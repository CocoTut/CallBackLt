package ru.cherepanovk.feature_events_impl.events

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.cherepanovk.core.di.DI
import ru.cherepanovk.core.di.dependencies.FeatureNavigator
import ru.cherepanovk.core.di.viewmodel.ViewModelFactory
import ru.cherepanovk.core.exception.Failure
import ru.cherepanovk.core.platform.BaseFragment
import ru.cherepanovk.core.platform.viewBinding
import ru.cherepanovk.core.utils.extentions.*
import ru.cherepanovk.core.utils.getDialIntent
import ru.cherepanovk.feature_events_api.EventsFeatureApi
import ru.cherepanovk.feature_events_impl.R
import ru.cherepanovk.feature_events_impl.databinding.FragmentEventsBinding
import ru.cherepanovk.feature_events_impl.dialog.delete.DialogDeleteParams
import ru.cherepanovk.feature_events_impl.event.EventOpenParams
import ru.cherepanovk.feature_events_impl.events.di.EventsComponent
import ru.cherepanovk.feature_google_calendar_api.data.GoogleAccountFeatureStarter
import javax.inject.Inject

const val PERMISSIONS_REQUEST_CODE = 302

class EventsFragment : BaseFragment(R.layout.fragment_events), EventsSwipeController.SwipeListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var googleAccountFeatureStarter: GoogleAccountFeatureStarter

    @Inject
    lateinit var featureNavigator: FeatureNavigator

    private val model by viewModels<EventsViewModel> { viewModelFactory }
    private val binding: FragmentEventsBinding by viewBinding(FragmentEventsBinding::bind)

    private val eventsSwipeController: EventsSwipeController = EventsSwipeController(this)

    private var swipedPosition = 0


    private val remindersAdapter = GroupAdapter<GroupieViewHolder>().apply {
        setOnItemClickListener { item, _ ->
            openEventScreen(id = (item as ItemReminder).id)
        }
    }


    private lateinit var popupMenu: PopupMenu
    private val yearsAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeRequestPermissions(
            PERMISSIONS_REQUEST_CODE,
            false,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_PHONE_STATE
        )

    }

    override fun onDestroy() {
        eventsSwipeController.removeSwipeListener()
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        model.checkGoogleAccount()
    }

    override fun inject() {
        DI.getComponent(EventsFeatureApi::class.java, EventsComponent::class.java)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popupMenu = PopupMenu(context, binding.toolbarMonths.tvToolbarMonths)
        popupMenu.inflate(R.menu.menu_months)
        initList()
        initYears()
    }

    private fun initYears() {
        binding.toolbarMonths.tvYears.apply {
            background = null
            isFocusable = false
            isClickable = true
            setOnClickListener {
                this.showDropDown()
            }
            setOnItemClickListener { _, _, position, _ ->
                model.yearSelected(yearsAdapter.getItem(position)?.toInt())
            }
        }

    }

    private fun initList() {
        binding.rvEventsFragment.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = remindersAdapter
            setHasFixedSize(true)
        }

        ItemTouchHelper(eventsSwipeController).run {
            attachToRecyclerView(binding.rvEventsFragment)
        }

    }

    override fun bindViewModel() {
        with(model) {
            observe(failure, ::showFailure)
            observe(currentMonth, ::setCurrentMonth)
            observe(emptyListVisibility, ::setEmptyListVisibility)
            observe(years, ::setYears)
            observe(currentYear, ::setCurrentYear)
            observe(itemsReminder, ::setItems)
            observe(askGoogleCalendarAccount, ::showAddGoogleAccountDialog)
            observe(isLoading, ::loading)
            observe(sortByDescending, ::setSorting)
            observeEvent(createNewReminder, ::openEventScreenWithLastPhoneNumber)
            observeFailure(failure, ::showFailure)
        }
        getNavigationResult<Boolean>()?.let {
            observe(it, ::deletingCanceled)
        }
    }

    private fun setSorting(sortedByDescending: Boolean) {
        binding.toolbarMonths.ivToolbarSort.isSelected = sortedByDescending
        binding.toolbarMonths.ivToolbarSort.contentDescription =
            getString(
                if (sortedByDescending) R.string.events_content_description_button_sort_by_ascending
                else  R.string.events_content_description_button_sort_by_descending
            )
    }

    private fun showFailure(failure: Failure) {
        view?.let {
            errorHandler.onHandleFailure(it, failure)
        }
    }

    private fun deletingCanceled(event: Boolean) {
        remindersAdapter.notifyItemChanged(swipedPosition)
    }

    private fun openEventScreenWithLastPhoneNumber(phoneNumber: String) {
        openEventScreen(phoneNumber = if (phoneNumber.isEmpty()) null else phoneNumber)
    }

    private fun loading(loading: Boolean) {
        binding.srlEventList.isRefreshing = loading
    }

    private fun showAddGoogleAccountDialog(showDialog: Boolean) {
        if (showDialog)
            featureNavigator.navigateToFeature(
                navController = findNavController(),
                featureNavGraph =
                googleAccountFeatureStarter.getAddGoogleAccountGraph(findNavController().navInflater)
            )
    }


    private fun setYears(years: List<String>) {
        yearsAdapter.clear()
        yearsAdapter.addAll(years)
        binding.toolbarMonths.tvYears.setAdapter(yearsAdapter)
    }

    override fun bindListeners() {
        binding.toolbarMonths.tvToolbarMonths.setOnClickListener { popupMenu.show() }

        binding.btnAddEvent.setOnClickListener {
            model.onBtnAddNewReminderClick()
        }

        popupMenu.setOnMenuItemClickListener { item ->

            model.onMonthClick(popupMenu.menu.children.indexOf(item))
            return@setOnMenuItemClickListener true
        }

        binding.srlEventList.setOnRefreshListener {
            model.updateReminders()
        }

        binding.toolbarMonths.ivToolbarSort.setOnClickListener {
            it.contentDescription = null
            model.onSortClick()
        }

    }

    private fun openEventScreen(id: String? = null, phoneNumber: String? = null) {
        val arguments = EventOpenParams(
            reminderId = id,
            phoneNumber = phoneNumber
        ).toBundle()
        val extras = FragmentNavigatorExtras(
            binding.btnAddEvent to getString(R.string.btn_transition_name)
        )

        findNavController().navigate(
            R.id.action_eventsFragment_to_eventFragment,
            arguments,
            null,
            extras
        )
    }

    private fun setItems(items: List<ItemReminder>) {
        remindersAdapter.update(items)
    }

    private fun setEmptyListVisibility(visible: Boolean) {
        binding.emptyList.root.visibility = if (visible) View.VISIBLE else View.GONE

    }

    private fun setCurrentMonth(month: Int) {
        popupMenu.menu.getItem(month).apply {
            isChecked = true
            binding.toolbarMonths.tvToolbarMonths.text = title
            binding.toolbarMonths.tvToolbarMonths.contentDescription =
                getString(R.string.content_descriprion_month_menu, title)
        }
    }

    private fun setCurrentYear(year: Int) {
        val yearText = year.toString()
        binding.toolbarMonths.tvYears.contentDescription =
            getString(R.string.content_description_years_menu, yearText)
        binding.toolbarMonths.tvYears.setText(yearText, false)

    }

    override fun swipeLeft(viewHolder: GroupieViewHolder) {
        doSwipe(viewHolder, SwipeDirection.ACTION_DELETE)
    }

    override fun swipeRight(viewHolder: GroupieViewHolder) {
        doSwipe(viewHolder, SwipeDirection.ACTION_CALL)
    }

    private fun doSwipe(viewHolder: GroupieViewHolder, swipeAction: SwipeDirection) {
        remindersAdapter.getItem(viewHolder).let {
            val itemReminder = (remindersAdapter.getItem(viewHolder) as ItemReminder)
            swipedPosition = remindersAdapter.getAdapterPosition(it)
            when (swipeAction) {
                SwipeDirection.ACTION_CALL -> {
                    startActivity(getDialIntent(itemReminder.phoneNumber))
                    remindersAdapter.notifyItemChanged(swipedPosition)
                }
                SwipeDirection.ACTION_DELETE -> showDeleteDialog(itemReminder.id)
            }
        }
    }

    private fun showDeleteDialog(id: String) {
        findNavController().navigate(
            R.id.action_eventsFragment_to_dialogDeleteReminder,
            DialogDeleteParams(id).toBundle()
        )
    }

    private enum class SwipeDirection {
        ACTION_CALL, ACTION_DELETE
    }

}
