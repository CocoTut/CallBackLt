package ru.cherepanovk.feature_events_impl.event

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
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
import ru.cherepanovk.core.utils.getDialIntent
import ru.cherepanovk.feature_events_impl.R
import ru.cherepanovk.feature_events_impl.event.di.DaggerEventComponent
import ru.cherepanovk.feature_events_impl.event.dialog.DialogDeleteReminderFragment
import ru.cherepanovk.imgurtest.utils.extensions.afterTextChanged
import ru.cherepanovk.imgurtest.utils.extensions.hideKeyboard
import javax.inject.Inject


private const val REQUEST_CONTACT_PICKER = 1006

class EventFragment : BaseFragment(R.layout.fragment_event),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener,
    ActivityStarter {

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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (firstTimeCreated(savedInstanceState)) {
            model.loadReminder(openParams?.reminderId)
            model.trySetPhoneNumber(openParams?.phoneNumber)
        }
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
            observe(contactName, ::setContactName)
            observe(phoneNumber, ::setPhoneNumber)

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

        btnOpenContacts.setOnClickListener {
            pickContacts(REQUEST_CONTACT_PICKER)
        }

        btnSendToWhatsApp.setOnClickListener {
            sendToWhatsApp()
        }

        btnCall.setOnClickListener {
            startActivity(getDialIntent(etPhoneNumberEvent.text.toString()))
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

        etPhoneNumberEvent.afterTextChanged {
            if (isPhoneNumberNameNotEmpty())
                tilPhoneNumber.error = null
        }


    }

    private fun sendToWhatsApp() {
        when {
            !isPhoneNumberNameNotEmpty() -> tilPhoneNumber.error = getString(R.string.error_empry_phone_number)
            iswhatsappInstalled("com.whatsapp") -> openWhatsApp()
            !iswhatsappInstalled("com.whatsapp") -> showWhatsAppError()
        }


    }

    private fun showWhatsAppError() {
        Toast.makeText(
            requireContext(), "WhatsApp not Installed",
            Toast.LENGTH_SHORT
        ).show()
        val uri = Uri.parse("market://details?id=com.whatsapp")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        startActivity(goToMarket)
    }

    private fun openWhatsApp() {
        val number = etPhoneNumberEvent.text.toString()
        val uri = Uri.parse("smsto:$number")
        val i = Intent(Intent.ACTION_SENDTO, uri)
        i.setPackage("com.whatsapp")
        startActivity(Intent.createChooser(i, ""))
    }

    private fun iswhatsappInstalled(uri: String): Boolean {
        val pm: PackageManager = requireActivity().packageManager
        var app_installed = false
        app_installed = try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return app_installed
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
            model.setContact(data)
        }
    }

    private fun setPhoneNumber(phoneNumber: String) {
        etPhoneNumberEvent.setText(phoneNumber)
    }

    private fun setContactName(contactName: String) {
        etContactNameEvent.setText(contactName)
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

    private fun isPhoneNumberNameNotEmpty(): Boolean {
        return etPhoneNumberEvent.text.isNotBlank()
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