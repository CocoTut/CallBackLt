package com.cherepanovky.callbackit.features.events

import android.os.Bundle
import com.cherepanovky.callbackit.R
import com.cherepanovky.callbackit.core.di.ComponentManager
import com.cherepanovky.callbackit.core.exception.ErrorHandler
import com.cherepanovky.callbackit.core.extention.viewContainer
import com.cherepanovky.callbackit.core.extention.viewModel
import com.cherepanovky.callbackit.core.platform.BaseFragment
import com.cherepanovky.callbackit.core.platform.RootViewProvider
import javax.inject.Inject

class EventsFragment : BaseFragment(R.layout.fragment_events) {

    lateinit var model: EventsViewModel

    @Inject
    lateinit var errorHandler: ErrorHandler

    override fun inject(componentManager: ComponentManager) {
        componentManager.appComponent
            .createEventsComponent()
            .rootViewProvider(RootViewProvider { viewContainer })
            .build()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = viewModel(viewModelFactory)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


}
