package ru.cherepanovk.feature_events_impl.event.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.dialog_delete_reminder.*
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.di.viewmodel.ViewModelFactory
import ru.cherepanovk.core.exception.Failure
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.observeFailure
import ru.cherepanovk.core.utils.extentions.viewModel
import ru.cherepanovk.feature_events_impl.ARG_EVENT_ID
import ru.cherepanovk.feature_events_impl.R
import ru.cherepanovk.feature_events_impl.event.di.DaggerEventComponent
import javax.inject.Inject

class DialogDeleteReminderFragment : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var model: DeleteReminderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(ComponentManager)

        model = viewModel(viewModelFactory)
    }

    private fun inject(componentManager: ComponentManager) {
            DaggerEventComponent.builder()
                .contextProvider(componentManager.getOrThrow())
                .coreDbApi(componentManager.getOrThrow())
                .build()
                .injectDialog(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_delete_reminder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindListeners()
        bindViewModel()
    }

    private fun bindViewModel() {
        with(model) {
            observe(openMainScreen, ::openEventsScreen)
            observeFailure(failure, ::handleError)
        }
    }

    private fun openEventsScreen(open: Boolean?) {
        findNavController().popBackStack(R.id.action_dialogDeleteReminder_to_eventsFragment, true)
    }

    private fun handleError(failure: Failure?) {
        //TODO
        dismiss()
    }

    private fun bindListeners() {
        btnYes.setOnClickListener {
            model.deleteReminder(arguments?.getString(ARG_EVENT_ID))
        }

        btnNo.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}