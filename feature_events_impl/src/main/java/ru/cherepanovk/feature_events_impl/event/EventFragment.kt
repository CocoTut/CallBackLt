package ru.cherepanovk.feature_events_impl.event

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.android.synthetic.main.fragment_event.tvDate
import kotlinx.android.synthetic.main.toolbar_back_title.*
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.platform.BaseFragment
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.viewModel
import ru.cherepanovk.feature_events_impl.ARG_EVENT_ID
import ru.cherepanovk.feature_events_impl.R
import ru.cherepanovk.feature_events_impl.event.di.DaggerEventComponent
import javax.inject.Inject

class EventFragment : BaseFragment(R.layout.fragment_event),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

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
        model = viewModel(viewModelFactory)
        model.loadReminder(arguments?.getString(ARG_EVENT_ID))
    }

    override fun bindListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnSaveEvent.setOnClickListener {
            saveReminder()
        }

        tvDate.setOnClickListener {
            model.onDateClick(tvDate.text.toString())
        }

        tvTime.setOnClickListener {
            model.onTimeClick(tvTime.text.toString())
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
            observe(showDatePickerEvent, ::showDatePickerDialog)
            observe(eventDate, ::setDate)
            observe(eventTime, ::setTime)
            observe(showTimePickerEvent, ::showTimePickerDialog)
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        model.onDateSet(
            DateForPicker(year, month, dayOfMonth)
        )
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        model.onTimeSet(
            TimeForPicker(hourOfDay, minute)
        )
    }

    private fun setDate(eventDate: String) {
        tvDate.text = eventDate
    }

    private fun setTime(eventTime: String) {
        tvTime.text = eventTime
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

    private fun showDatePickerDialog(dateForPicker: DateForPicker) {
        DatePickerDialog(
            context!!,
            this,
            dateForPicker.year,
            dateForPicker.month,
            dateForPicker.day
        ).apply { show() }

    }

    private fun showTimePickerDialog(timeForPicker: TimeForPicker) {
        TimePickerDialog(
            context!!,
            this,
            timeForPicker.hours,
            timeForPicker.minutes,
            true
        ).apply { show() }
    }
}