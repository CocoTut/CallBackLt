package ru.cherepanovk.feature_events_impl.event

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.android.synthetic.main.fragment_event.tvDate
import kotlinx.android.synthetic.main.toolbar_back_title.*
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.platform.BaseFragment
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.observeFailure
import ru.cherepanovk.core.utils.extentions.viewModel
import ru.cherepanovk.feature_events_impl.ARG_EVENT_ID
import ru.cherepanovk.feature_events_impl.R
import ru.cherepanovk.feature_events_impl.event.di.DaggerEventComponent
import javax.inject.Inject

class EventFragment : BaseFragment(R.layout.fragment_event) {

    private lateinit var model: EventViewModel

    @Inject
    lateinit var errorHandler: ErrorHandler

    override fun inject(componentManager: ComponentManager) {
        DaggerEventComponent.builder()
            .contextProvider(componentManager.getOrThrow())
            .coreDbApi(componentManager.getOrThrow())
            .build()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = viewModel(viewModelFactory) {

        }
        model.loadReminder(arguments?.getString(ARG_EVENT_ID))

        bindViewModel()

    }

    override fun bindListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnSaveEvent.setOnClickListener {
            saveReminder()
        }
    }

    private fun saveReminder() {
        val reminderView = ReminderView(
            phoneNumber = etPhoneNumberEvent.text.toString(),
            description = etDescriptionEvent.text.toString(),
            contactName = etContactNameEvent.text.toString(),
            date = tvDate.text.toString(),
            time = tvTime.text.toString()

        )
        model.saveReminder(reminderView)
    }

    override fun bindViewModel() {
        with(model) {
            observe(reminderView, ::setReminder)
            observe(toolbarTitleNewReminder, ::setTitleNewReminder)
            observe(success, ::handleSuccess)
        }
    }
    private fun handleSuccess(success: Boolean) {
        findNavController().popBackStack()
    }

    private fun setReminder(reminder: ReminderView) {
        etPhoneNumberEvent.setText(reminder.phoneNumber)
        etContactNameEvent.setText(reminder.contactName)
        etDescriptionEvent.setText(reminder.description)
        tvDate.text = reminder.date
        tvTime.text = reminder.time
    }

    private fun setTitleNewReminder(newReminder: Boolean) {
        tvToolbarTitle.setText(
            if (newReminder) R.string.title_toolbar_new_reminder else R.string.title_toolbar_edit_reminder
        )
    }
}