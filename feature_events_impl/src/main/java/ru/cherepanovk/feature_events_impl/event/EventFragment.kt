package ru.cherepanovk.feature_events_impl.event

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
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
import ru.cherepanovk.feature_events_impl.event.dialog.DialogDeleteReminderFragment
import ru.cherepanovk.imgurtest.utils.extensions.afterTextChanged
import ru.cherepanovk.imgurtest.utils.extensions.hideKeyboard
import javax.inject.Inject

class EventFragment : BaseFragment(R.layout.fragment_event),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var model: EventViewModel
    private val openParams
        get() = arguments?.let { EventOpenParams.fromBundle(it) }

    @Inject
    lateinit var errorHandler: ErrorHandler

    override fun inject(componentManager: ComponentManager) {
        DaggerEventComponent.builder()
            .contextProvider(componentManager.getOrThrow())
            .coreDbApi(componentManager.getOrThrow())
            .coreDomainApi(componentManager.getOrThrow())
            .build()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move)
            .apply { interpolator = AccelerateDecelerateInterpolator() }

        if (firstTimeCreated(savedInstanceState))
            model.loadReminder(openParams?.reminderId)
    }

    override fun onDestroyView() {
        view?.hideKeyboard()
        super.onDestroyView()
    }


    override fun bindViewModel() {
        model = viewModel(viewModelFactory) {
            observe(reminderView, ::setReminder)
            observe(toolbarTitleNewReminder, ::setTitleNewReminder)
            observe(success, ::handleSuccess)
            observe(showDatePickerEvent, ::showDatePickerDialog)
            observe(eventDate, ::setDate)
            observe(eventTime, ::setTime)
            observe(showTimePickerEvent, ::showTimePickerDialog)
            observe(buttonsVisibility, ::setButtonsVisibility)
            observe(hintTimeIsLessThanCurrent, ::showTimeHint)
            observe(hintDateIsLessThanCurrent, ::showDateHint)

        }
    }


    override fun bindListeners() {
        ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        btnSaveEvent.setOnClickListener {
            if (isContactNameNotEmpty())
                saveReminder()
            else
                tilContactName.error = getString(R.string.ContactNameIsEmpty)
        }

        btnDeleteEvent.setOnClickListener {
            arguments?.getString(ARG_EVENT_ID)?.let { showDeleteDialog(it) }
        }

        tvDate.setOnClickListener {
            model.onDateClick(tvDate.text.toString())
        }

        tvTime.setOnClickListener {
            model.onTimeClick(
                tvTime.text.toString(),
                tvDate.text.toString()
            )
        }

        etContactNameEvent.afterTextChanged {
            if (isContactNameNotEmpty())
                tilContactName.error = null
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

    private fun showTimeHint(visible: Boolean) {
        tvTimeHint.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun showDateHint(visible: Boolean) {
        tvDateHint.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun showDeleteDialog(id: String) {
        val dialogFragment = DialogDeleteReminderFragment.newInstance(id)
        val transaction = childFragmentManager
            .beginTransaction()
            .add(dialogFragment, dialogFragment::class.java.canonicalName)
        transaction.commit()
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

    private fun isContactNameNotEmpty(): Boolean {
        return etContactNameEvent.text.isNotBlank()
    }

    private fun setDate(eventDate: String) {
        tvDate.text = eventDate
    }

    private fun setTime(eventTime: String) {
        tvTime.text = eventTime
    }

    private fun setButtonsVisibility(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.INVISIBLE
        btnCall.visibility = visibility
        btnDeleteEvent.visibility = visibility
    }

    private fun handleSuccess(success: Boolean) {
        requireActivity().onBackPressed()
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