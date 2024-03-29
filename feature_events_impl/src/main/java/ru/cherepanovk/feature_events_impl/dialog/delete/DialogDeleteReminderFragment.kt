package ru.cherepanovk.feature_events_impl.dialog.delete

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.cherepanovk.core.di.DI
import ru.cherepanovk.core.di.viewmodel.ViewModelFactory
import ru.cherepanovk.core.exception.Failure
import ru.cherepanovk.core.platform.ErrorHandler
import ru.cherepanovk.core.platform.viewBinding
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.observeFailure
import ru.cherepanovk.core.utils.extentions.setNavigationResult
import ru.cherepanovk.feature_events_api.EventsFeatureApi
import ru.cherepanovk.feature_events_impl.R
import ru.cherepanovk.feature_events_impl.databinding.DialogDeleteReminderBinding
import ru.cherepanovk.feature_events_impl.events.di.EventsComponent
import ru.cherepanovk.imgurtest.utils.extensions.showOrGone
import javax.inject.Inject


class DialogDeleteReminderFragment : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var errorHandler: ErrorHandler

    private val reminderId: String?
        get() = DialogDeleteParams.fromBundle(arguments)?.reminderId

    private val binding: DialogDeleteReminderBinding by viewBinding(DialogDeleteReminderBinding::bind)

    private val model: DeleteReminderViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    private fun inject() {
        DI.getComponent(EventsFeatureApi::class.java, EventsComponent::class.java)
            .getDialogDeleteComponent()
            .injectDialog(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_delete_reminder, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindListeners()

    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    private fun bindViewModel() {
        with(model) {
            observe(openMainScreen, ::openEventsScreen)
            observeFailure(failure, ::handleError)
            observe(isLoading, binding.pbDeleteEvent::showOrGone)
        }
    }


    private fun bindListeners() {
        binding.btnYes.setOnClickListener {
            model.deleteReminder(reminderId)
        }

        binding.btnNo.setOnClickListener {
            setNavigationResult(true)
            dismiss()
        }
    }


    private fun openEventsScreen(open: Boolean) {
        dismiss()
        findNavController().navigate(R.id.action_dialogDeleteReminder_to_eventsFragment)
    }

    private fun handleError(failure: Failure) {
        view?.let {
            errorHandler.onHandleFailure(it, failure)
        }
        dismiss()
    }

    companion object {
        private const val ARG_EVENT_ID = "ARG_EVENT_ID"
        fun newInstance(id: String) =
            DialogDeleteReminderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_EVENT_ID, id)
                }
            }
    }
}