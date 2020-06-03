package ru.cherepanovk.feature_events_impl.event.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.di.viewmodel.ViewModelFactory
import ru.cherepanovk.core.exception.Failure
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.observeFailure
import ru.cherepanovk.feature_events_impl.R
import javax.inject.Inject
import android.view.Window
import androidx.fragment.app.viewModels
import ru.cherepanovk.core.platform.viewBinding
import ru.cherepanovk.feature_events_impl.databinding.DialogDeleteReminderBinding
import ru.cherepanovk.feature_events_impl.event.dialog.di.DaggerDialogDeleteComponent


class DialogDeleteReminderFragment : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val reminderId: String?
        get() = DialogDeleteParams.fromBundle(arguments)?.reminderId

    private val binding: DialogDeleteReminderBinding by viewBinding(DialogDeleteReminderBinding::bind)

    private val model: DeleteReminderViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(ComponentManager)
    }

    private fun inject(componentManager: ComponentManager) {
        DaggerDialogDeleteComponent.builder()
            .contextProvider(componentManager.getOrThrow())
            .coreDbApi(componentManager.getOrThrow())
            .coreDomainApi(componentManager.getOrThrow())
            .coreGoogleCalendarApi(componentManager.getOrThrow())
            .corePreferencesApi(componentManager.getOrThrow())
            .build()
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
        }
    }


    private fun bindListeners() {
        binding.btnYes.setOnClickListener {
            model.deleteReminder(reminderId)
        }

        binding.btnNo.setOnClickListener {
           dismiss()
        }

    }


    private fun openEventsScreen(open: Boolean) {
        dismiss()
        findNavController().navigate(R.id.action_dialogDeleteReminder_to_eventsFragment)
    }

    private fun handleError(failure: Failure?) {

    }

    companion object {
        private const val ARG_EVENT_ID = "ARG_EVENT_ID"
        fun newInstance(id: String) = DialogDeleteReminderFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_EVENT_ID, id)
            }
        }
    }
}