package ru.cherepanovk.feature_events_impl.event

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.transition.TransitionInflater
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.android.synthetic.main.toolbar_back_title.*
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.platform.ActivityStarter
import ru.cherepanovk.core.platform.BaseFragment
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.pickContacts
import ru.cherepanovk.core.utils.extentions.viewModel
import ru.cherepanovk.feature_events_impl.R
import ru.cherepanovk.feature_events_impl.event.di.DaggerEventComponent
import ru.cherepanovk.feature_events_impl.event.dialog.DialogDeleteReminderFragment
import ru.cherepanovk.imgurtest.utils.extensions.afterTextChanged
import ru.cherepanovk.imgurtest.utils.extensions.hideKeyboard
import javax.inject.Inject

private const val REQUEST_CONTACT_PICKER = 1006
private const val CURSOR_CONTACT_NAME = 0
private const val CURSOR_PHONE_NUMBER = 1

class EventFragment : BaseFragment(R.layout.fragment_event),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener,
    ActivityStarter {

    private lateinit var model: EventViewModel
    private val openParams
        get() = arguments?.let { EventOpenParams.fromBundle(it) }

    @Inject
    lateinit var errorHandler: ErrorHandler

    private val contactsData: Array<String> by lazy {
        arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
    }

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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
            openParams?.reminderId?.let { showDeleteDialog(it) }
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

        btnOpenContacts.setOnClickListener {
            pickContacts(REQUEST_CONTACT_PICKER)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK &&
            requestCode == REQUEST_CONTACT_PICKER
        ) {
            settContactFromUri(data)
        }
    }

    private fun settContactFromUri(data: Intent?) {
        data?.data?.let { dataUri ->
            val cursor = requireActivity().contentResolver.query(
                dataUri, contactsData, null, null, null
            )
            cursor?.moveToNext()
            cursor?.getString(CURSOR_CONTACT_NAME)?.let {
                etContactNameEvent.setText(it)
            }

            cursor?.getString(CURSOR_PHONE_NUMBER)?.let {
                etPhoneNumberEvent.setText(it)
            }
            cursor?.close()
        }
    }

    private fun showTimeHint(visible: Boolean) {
        tvTimeHint.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun showDateHint(visible: Boolean) {
        tvDateHint.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun showDeleteDialog(id: String) {
        val dialogFragment = DialogDeleteReminderFragment.newInstance(id)
        dialogFragment.show(childFragmentManager,  DialogDeleteReminderFragment::class.java.canonicalName)
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