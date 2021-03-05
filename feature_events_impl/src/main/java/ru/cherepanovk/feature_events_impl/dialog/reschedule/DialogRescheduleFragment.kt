package ru.cherepanovk.feature_events_impl.dialog.reschedule

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.exception.Failure
import ru.cherepanovk.core.platform.BaseDialogFragment
import ru.cherepanovk.core.platform.viewBinding
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.observeFailure
import ru.cherepanovk.feature_events_impl.R
import ru.cherepanovk.feature_events_impl.databinding.DialogRescheduleBinding
import ru.cherepanovk.feature_events_impl.dialog.reschedule.di.DaggerRescheduleComponent
import ru.cherepanovk.imgurtest.utils.extensions.showOrGone

class DialogRescheduleFragment : BaseDialogFragment(R.layout.dialog_reschedule) {


    private val binding: DialogRescheduleBinding by viewBinding(DialogRescheduleBinding::bind)

    private val model: DialogRescheduleViewModel by viewModels { viewModelFactory }

    private val rescheduleAdapter = GroupAdapter<GroupieViewHolder>()

    private val reminderId: String?
        get() = DialogRescheduleParams.fromBundle(arguments)?.reminderId

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun inject(componentManager: ComponentManager) {
        DaggerRescheduleComponent.builder()
            .contextProvider(componentManager.getOrThrow())
            .coreDbApi(componentManager.getOrThrow())
            .featureAlarmApi(componentManager.getOrThrow())
            .coreGoogleCalendarApi(componentManager.getOrThrow())
            .corePreferencesApi(componentManager.getOrThrow())
            .build()
            .inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        model.loadRescheduleItems()
    }

    private fun initList() {
        binding.rvReschedule.apply {
            adapter = rescheduleAdapter
            layoutManager = LinearLayoutManager(requireContext()).also { linearLayoutManager = it }
            setHasFixedSize(true)
        }
    }

    override fun bindListeners() {
        rescheduleAdapter.setOnItemClickListener { item, _ ->
            when (item) {
                is RescheduleItem -> model.onItemClick(reminderId, item)
            }

        }
    }

    override fun bindViewModel() {
        with(model) {
            observe(rescheduleItems, ::setItems)
            observe(isLoading, binding.pbChangeTime::showOrGone)
            observe(openMainScreen) { openEventsScreen() }
            observe(createClick) { onCreateRescheduleClick() }
            observeFailure(failure,::showFailure)
        }
    }

    private fun showFailure(failure: Failure?) {
        view?.let {
            errorHandler.onHandleFailure(it, failure)
        }

    }

    private fun onCreateRescheduleClick() {
        linearLayoutManager.scrollToPositionWithOffset(rescheduleAdapter.itemCount - 1, 0)
    }

    private fun openEventsScreen() {
        dismiss()
        findNavController().navigate(R.id.action_dialogRescheduleFragment_to_eventsFragment)
    }

    private fun setItems(items: List<Group>) {
        rescheduleAdapter.update(items)
    }
}