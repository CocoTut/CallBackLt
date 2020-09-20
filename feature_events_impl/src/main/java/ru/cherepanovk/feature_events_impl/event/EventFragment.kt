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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import pub.devrel.easypermissions.EasyPermissions
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.platform.ActivityStarter
import ru.cherepanovk.core.platform.BaseFragment
import ru.cherepanovk.core.platform.viewBinding
import ru.cherepanovk.core.utils.extentions.*
import ru.cherepanovk.core.utils.getDialIntent
import ru.cherepanovk.feature_events_impl.ContactsPermissionChecker
import ru.cherepanovk.feature_events_impl.R
import ru.cherepanovk.feature_events_impl.databinding.FragmentEventBinding
import ru.cherepanovk.feature_events_impl.event.di.DaggerEventComponent
import ru.cherepanovk.feature_events_impl.event.dialog.DialogDeleteParams
import ru.cherepanovk.imgurtest.utils.extensions.afterTextChanged
import ru.cherepanovk.imgurtest.utils.extensions.hideKeyboard
import ru.cherepanovk.imgurtest.utils.extensions.showOrHide
import javax.inject.Inject


private const val REQUEST_CONTACT_PICKER = 1006

class EventFragment : BaseFragment(R.layout.fragment_event),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener,
    ActivityStarter {

    private val model: EventViewModel by viewModels { viewModelFactory }
    private val binding: FragmentEventBinding by viewBinding(FragmentEventBinding::bind)
    private val openParams
        get() = arguments?.let { EventOpenParams.fromBundle(it) }

    @Inject
    lateinit var contactsPermissionChecker: ContactsPermissionChecker


    override fun inject(componentManager: ComponentManager) {
        DaggerEventComponent.builder()
            .contextProvider(componentManager.getOrThrow())
            .coreDbApi(componentManager.getOrThrow())
            .featureAlarmApi(componentManager.getOrThrow())
            .corePreferencesApi(componentManager.getOrThrow())
            .coreGoogleCalendarApi(componentManager.getOrThrow())
            .rootViewProvider(componentManager.getOrThrow())
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onDestroyView() {
        view?.hideKeyboard()
        super.onDestroyView()
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
            observe(buttonsVisibility, ::setButtonsVisibility)
            observe(hintTimeIsLessThanCurrent, ::showTimeHint)
            observe(hintDateIsLessThanCurrent, ::showDateHint)
            observe(contactName, ::setContactName)
            observe(phoneNumber, ::setPhoneNumber)
            observe(isLoading, ::showLoading)
            observeFailure(failure, errorHandler::onHandleFailure)

        }
    }

    private fun showLoading(loading: Boolean) {
        binding.pbEvent.showOrHide(loading)
    }

    override fun bindListeners() {
        binding.toolbar.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btnSaveEvent.setOnClickListener {
            if (isContactNameNotEmpty())
                saveReminder()
            else
                binding.tilContactName.error = getString(R.string.ContactNameIsEmpty)
        }

        binding.btnDeleteEvent.setOnClickListener {
            openParams?.reminderId?.let { showDeleteDialog(it) }
        }

        binding.btnOpenContacts.setOnClickListener {
            contactsPermissionChecker.checkContactPermission(this) {
                pickContacts(
                    REQUEST_CONTACT_PICKER
                )
            }


        }

        binding.btnSendToWhatsApp.setOnClickListener {
            sendToWhatsApp()
        }

        binding.btnCall.setOnClickListener {
            startActivity(getDialIntent(binding.etPhoneNumberEvent.text.toString()))
        }

        binding.tvDate.setOnClickListener {
            model.onDateClick(binding.tvDate.text.toString())
        }

        binding.tvTime.setOnClickListener {
            model.onTimeClick(
                binding.tvTime.text.toString(),
                binding.tvDate.text.toString()
            )
        }

        binding.etContactNameEvent.afterTextChanged {
            if (isContactNameNotEmpty())
                binding.tilContactName.error = null
        }

        binding.etPhoneNumberEvent.afterTextChanged {
            if (isPhoneNumberNameNotEmpty())
                binding.tilPhoneNumber.error = null
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private fun sendToWhatsApp() {
        when {
            !isPhoneNumberNameNotEmpty() -> binding.tilPhoneNumber.error =
                getString(R.string.error_empry_phone_number)
            isWhatsappInstalled("com.whatsapp") -> openWhatsApp()
            !isWhatsappInstalled("com.whatsapp") -> showWhatsAppError()
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
        val number = binding.etPhoneNumberEvent.text.toString()
        val uri = Uri.parse("smsto:$number")
        val i = Intent(Intent.ACTION_SENDTO, uri)
        i.setPackage("com.whatsapp")
        startActivity(Intent.createChooser(i, ""))
    }

    private fun isWhatsappInstalled(uri: String): Boolean {
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
        binding.etPhoneNumberEvent.setText(phoneNumber)
    }

    private fun setContactName(contactName: String) {
        binding.etContactNameEvent.setText(contactName)
    }

    private fun showTimeHint(visible: Boolean) {
        binding.tvTimeHint.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun showDateHint(visible: Boolean) {
        binding.tvDateHint.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun showDeleteDialog(id: String) {
        findNavController().navigate(
            R.id.action_eventFragment_to_dialogDeleteReminder,
            DialogDeleteParams(id).toBundle()
        )
    }


    private fun saveReminder() {
        val reminderView = ReminderView(
            phoneNumber = binding.etPhoneNumberEvent.text.toString(),
            description = binding.etDescriptionEvent.text.toString(),
            contactName = binding.etContactNameEvent.text.toString(),
            date = binding.tvDate.text.toString(),
            time = binding.tvTime.text.toString()
        )
        model.saveReminder(reminderView)
    }

    private fun isContactNameNotEmpty(): Boolean {
        return binding.etContactNameEvent.text.isNotBlank()
    }

    private fun isPhoneNumberNameNotEmpty(): Boolean {
        return binding.etPhoneNumberEvent.text.isNotBlank()
    }

    private fun setDate(eventDate: String) {
        binding.tvDate.text = eventDate
    }

    private fun setTime(eventTime: String) {
        binding.tvTime.text = eventTime
    }

    private fun setButtonsVisibility(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.INVISIBLE
        binding.btnCall.visibility = visibility
        binding.btnDeleteEvent.visibility = visibility
    }

    private fun handleSuccess(success: Boolean) {
        requireActivity().onBackPressed()
    }

    private fun setReminder(reminder: ReminderView) {
        binding.etPhoneNumberEvent.setText(reminder.phoneNumber)
        binding.etContactNameEvent.setText(reminder.contactName)
        binding.etDescriptionEvent.setText(reminder.description)
        binding.tvDate.text = reminder.date
        binding.tvTime.text = reminder.time
    }

    private fun setTitleNewReminder(newReminder: Boolean) {
        binding.toolbar.tvToolbarTitle.setText(
            if (newReminder) R.string.title_toolbar_new_reminder else R.string.title_toolbar_edit_reminder
        )
    }

    private fun showDatePickerDialog(dateForPicker: DateForPicker) {
        DatePickerDialog(
            requireContext(),
            this,
            dateForPicker.year,
            dateForPicker.month,
            dateForPicker.day
        ).apply { show() }

    }

    private fun showTimePickerDialog(timeForPicker: TimeForPicker) {
        TimePickerDialog(
            requireContext(),
            this,
            timeForPicker.hours,
            timeForPicker.minutes,
            true
        ).apply { show() }
    }


}


