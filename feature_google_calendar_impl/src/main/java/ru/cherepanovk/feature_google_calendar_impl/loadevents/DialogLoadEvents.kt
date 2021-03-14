package ru.cherepanovk.feature_google_calendar_impl.loadevents

import androidx.fragment.app.viewModels
import ru.cherepanovk.core.di.DI
import ru.cherepanovk.core.exception.Failure
import ru.cherepanovk.core.platform.BaseDialogFragment
import ru.cherepanovk.core.platform.viewBinding
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.observeFailure
import ru.cherepanovk.feature_google_calendar_api.di.CoreGoogleCalendarApi
import ru.cherepanovk.feature_google_calendar_impl.R
import ru.cherepanovk.feature_google_calendar_impl.databinding.DialogLoadEventsBinding
import ru.cherepanovk.feature_google_calendar_impl.di.GoogleCalendarApiComponent
import ru.cherepanovk.imgurtest.utils.extensions.showOrGone

class DialogLoadEvents : BaseDialogFragment(R.layout.dialog_load_events) {
    private val model: LoadEventsViewModel by viewModels { viewModelFactory }
    private val binding: DialogLoadEventsBinding by viewBinding(DialogLoadEventsBinding::bind)

    override fun inject() {
        DI.getComponent(CoreGoogleCalendarApi::class.java, GoogleCalendarApiComponent::class.java )
            .getLoadEventsComponent()
            .inject(this)
    }

    override fun bindListeners() {
        binding.btnNo.setOnClickListener {
            this.dismiss()
        }

        binding.btnYes.setOnClickListener {
            model.loadEvents()
        }
    }

    override fun bindViewModel() {
        with(model) {
            observe(isLoading, ::loading)
            observeFailure(failure, ::showFailure)
        }
    }

    private fun showFailure(failure: Failure?) {
        view?.let {
            errorHandler.onHandleFailure(it, failure)
        }
    }

    private fun loading(loading: Boolean) {
        if (!loading)
            dismiss()
        binding.pbDialog.showOrGone(loading)
    }
}