package com.cherepanovky.callbackit.features.events

import android.os.Bundle
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import com.cherepanovky.callbackit.R
import com.cherepanovky.callbackit.core.exception.ErrorHandler
import com.cherepanovky.callbackit.core.extention.viewModel
import com.cherepanovky.callbackit.core.platform.BaseFragment
import com.cherepanovky.callbackit.features.events.di.DaggerEventsComponent
import kotlinx.android.synthetic.main.toolbar_burger_months.*
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core_db_impl.di.DaggerCoreDbComponent
import javax.inject.Inject

class EventsFragment : BaseFragment(R.layout.fragment_events) {

    lateinit var model: EventsViewModel

    @Inject
    lateinit var errorHandler: ErrorHandler

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var popupMenu: PopupMenu

    override fun inject(componentManager: ComponentManager) {
            DaggerEventsComponent.builder()
                .contextProvider(componentManager.getOrThrow())
                .coreDbApi(
                    DaggerCoreDbComponent.builder()
                        .contextProvider(componentManager.getOrThrow())
                        .build()
                )
                .build()
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(componentManager)

        model = viewModel(viewModelFactory)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        popupMenu = PopupMenu(context, tvToolbarMonths)
        popupMenu.inflate(R.menu.menu_months)
        tvToolbarMonths.setOnClickListener { showMonthsMenu() }
    }

    private fun showMonthsMenu() {
        popupMenu.show()
    }


}
