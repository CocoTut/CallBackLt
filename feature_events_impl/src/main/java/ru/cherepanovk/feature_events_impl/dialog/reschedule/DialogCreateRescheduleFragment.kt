package ru.cherepanovk.feature_events_impl.dialog.reschedule

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.platform.BaseDialogFragment
import ru.cherepanovk.core.platform.viewBinding
import ru.cherepanovk.feature_events_impl.R
import ru.cherepanovk.feature_events_impl.databinding.DialogCreateRescheduleBinding

class DialogCreateRescheduleFragment : BaseDialogFragment(R.layout.dialog_create_reschedule) {

    private val binding: DialogCreateRescheduleBinding by viewBinding(DialogCreateRescheduleBinding::bind)

//    private val model: DialogRescheduleViewModel by viewModels { viewModelFactory }

    override fun inject(componentManager: ComponentManager) {
//        DaggerRescheduleComponent.builder()
//            .contextProvider(componentManager.getOrThrow())
//            .coreDbApi(componentManager.getOrThrow())
//            .featureAlarmApi(componentManager.getOrThrow())
//            .coreGoogleCalendarApi(componentManager.getOrThrow())
//            .corePreferencesApi(componentManager.getOrThrow())
//            .rootViewProvider(componentManager.getOrThrow())
//            .build()
//            .inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {
    }

    override fun bindListeners() {
        binding.btnCancel.setOnClickListener { dismiss() }
    }

    override fun bindViewModel() {
//        with(model) {
//            observe(openMainScreen) { openEventsScreen() }
//            observeFailure(failure, errorHandler::onHandleFailure)
//        }
    }

    private fun openEventsScreen() {
        dismiss()
        findNavController().navigate(R.id.action_dialogRescheduleFragment_to_eventsFragment)
    }

}