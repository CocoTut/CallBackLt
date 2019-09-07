package ru.cherepanovk.feature_events_impl.events

import android.os.Bundle
import android.widget.PopupMenu
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.toolbar_burger_months.*
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.platform.BaseFragment
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.viewModel
import ru.cherepanovk.feature_events_impl.R
import ru.cherepanovk.feature_events_impl.events.di.EventsComponent
import javax.inject.Inject

class EventsFragment : BaseFragment(R.layout.fragment_events) {

    private lateinit var model: EventsViewModel

    @Inject
    lateinit var errorHandler: ErrorHandler

    private lateinit var popupMenu: PopupMenu

    override fun inject(componentManager: ComponentManager) {
           componentManager.getOrThrow<EventsComponent>()
               .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(componentManager)

        model = viewModel(viewModelFactory)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        popupMenu = PopupMenu(context, tvToolbarMonths)
        popupMenu.inflate(R.menu.menu_months)

        super.onActivityCreated(savedInstanceState)
    }

    override fun bindViewModel() {
       with(model){
           observe(currentMonth, ::setCurrentMonth)
       }
    }

    override fun bindListeners() {
        tvToolbarMonths.setOnClickListener {  popupMenu.show() }

        btnAddEvent.setOnClickListener {
            findNavController().navigate(R.id.action_eventsFragment_to_eventFragment)
        }

        popupMenu.setOnMenuItemClickListener { item ->

            model.onMonthClick(popupMenu.menu.children.indexOf(item))
            return@setOnMenuItemClickListener true
        }
    }

    private fun setCurrentMonth(month: Int){
        popupMenu.menu.getItem(month).apply {
            isChecked = true
            tvToolbarMonths.text = title
        }
    }


}
