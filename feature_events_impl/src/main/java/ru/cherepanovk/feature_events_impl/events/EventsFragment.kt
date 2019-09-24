package ru.cherepanovk.feature_events_impl.events

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.toolbar_months.*
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.di.getOrThrow
import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.platform.BaseFragment
import ru.cherepanovk.core.utils.extentions.observe
import ru.cherepanovk.core.utils.extentions.viewModel
import ru.cherepanovk.feature_events_impl.ARG_EVENT_ID
import ru.cherepanovk.feature_events_impl.R
import ru.cherepanovk.feature_events_impl.events.di.EventsComponent
import javax.inject.Inject

class EventsFragment : BaseFragment(R.layout.fragment_events) {

    private lateinit var model: EventsViewModel

    @Inject
    lateinit var errorHandler: ErrorHandler

    private val remindersAdapter = GroupAdapter<ViewHolder>().apply {
        setOnItemClickListener { item, _ ->
            val arguments = Bundle().apply {
                putString(ARG_EVENT_ID, (item as ItemReminder).id)
            }
            findNavController().navigate(R.id.action_eventsFragment_to_eventFragment, arguments)
        }
    }

    private lateinit var popupMenu: PopupMenu
    private val yearsAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line)
    }

    override fun inject(componentManager: ComponentManager) {
        componentManager.getOrThrow<EventsComponent>()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = viewModel(viewModelFactory)
        bindViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popupMenu = PopupMenu(context, tvToolbarMonths)
        popupMenu.inflate(R.menu.menu_months)
        initList()
        initYears()
        model.loadData()
    }

    private fun initYears() {
        tvYears.background = null
        tvYears.isFocusable = false
        tvYears.isClickable = true
        tvYears.setOnClickListener {
            tvYears.showDropDown()
        }
        tvYears.setOnItemClickListener { _, _, position, _ ->
            model.yearSelected(yearsAdapter.getItem(position)?.toInt())
        }
    }

    private fun initList() {
        rvEventsFragment.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = remindersAdapter
            setHasFixedSize(true)
        }

    }

    override fun bindViewModel() {
        with(model) {
            observe(currentMonth, ::setCurrentMonth)

            observe(emptyListVisibility, ::setEmptyListVisibility)
            observe(years, ::setYears)
            observe(currentYear, ::setCurrentYear)
        }
        model.itemsReminder.observe(this, Observer { setItems(it) })
    }

    private fun setYears(years: List<String>) {
        yearsAdapter.clear()
        yearsAdapter.addAll(years)
        tvYears.setAdapter(yearsAdapter)
    }

    override fun bindListeners() {
        tvToolbarMonths.setOnClickListener { popupMenu.show() }

        btnAddEvent.setOnClickListener {
            findNavController().navigate(R.id.action_eventsFragment_to_eventFragment)
        }

        popupMenu.setOnMenuItemClickListener { item ->

            model.onMonthClick(popupMenu.menu.children.indexOf(item))
            return@setOnMenuItemClickListener true
        }

    }

    private fun setItems(items: List<ItemReminder>) {
        remindersAdapter.clear()
        remindersAdapter.addAll(items)
    }

    private fun setEmptyListVisibility(visible: Boolean) {
        emptyList.visibility = if (visible) View.VISIBLE else View.GONE

    }

    private fun setCurrentMonth(month: Int) {
        popupMenu.menu.getItem(month).apply {
            isChecked = true
            tvToolbarMonths.text = title
        }
    }

    private fun setCurrentYear(year: Int) {
        tvYears.setText(year.toString(), false)
    }


}
