package ru.cherepanovk.feature_events_impl

import android.os.Bundle
import android.widget.PopupMenu
import androidx.core.view.children
import kotlinx.android.synthetic.main.toolbar_burger_months.*
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.platform.BaseFragment
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.viewModel
import ru.cherepanovk.core_db_impl.di.DaggerCoreDbComponent
import ru.cherepanovk.feature_events_impl.di.DaggerEventsComponent
import javax.inject.Inject

class EventsFragment : BaseFragment(R.layout.fragment_events) {

    private lateinit var model: EventsViewModel

    @Inject
    lateinit var errorHandler: ErrorHandler

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
