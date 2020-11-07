package ru.cherepanovk.feature_events_impl.dialog.reschedule

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.xwray.groupie.viewbinding.BindableItem
import ru.cherepanovk.feature_events_impl.R
import ru.cherepanovk.feature_events_impl.databinding.ItemDialogRescheduleCreateBinding
import ru.cherepanovk.imgurtest.utils.extensions.afterTextChanged
import ru.cherepanovk.imgurtest.utils.extensions.gone
import ru.cherepanovk.imgurtest.utils.extensions.show


class RescheduleCreateItem(
    private val onAddClickListener: (RescheduleUnitType, Int) -> Unit,
    private val onCreateClickListener: () -> Unit
) : BindableItem<ItemDialogRescheduleCreateBinding>() {

    private lateinit var context: Context
    private val unitsAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line)
    }
    private var unitPosition = 0

    private val itemsMap = mapOf(
        0 to RescheduleUnitType.MINUTES,
        1 to RescheduleUnitType.HOURS,
        2 to RescheduleUnitType.DAYS
    )

    override fun getLayout(): Int =
        R.layout.item_dialog_reschedule_create

    override fun initializeViewBinding(view: View): ItemDialogRescheduleCreateBinding {
        return ItemDialogRescheduleCreateBinding.bind(view)
    }

    override fun bind(viewBinding: ItemDialogRescheduleCreateBinding, position: Int) {
        context = viewBinding.root.context
        initUnits(viewBinding.tvUnitReschedule)

        viewBinding.btnCreateReschedule.setOnClickListener {
            showCreation(viewBinding)
            onCreateClickListener.invoke()
        }

        viewBinding.btnCancel.setOnClickListener {
           hideCreation(viewBinding)
        }

        viewBinding.btnAddReschedule.setOnClickListener {
                if (viewBinding.etNumberUnit.text.isEmpty()) {
                    viewBinding.tilNumberUnit.error =
                        context.getString(R.string.error_add_reschedule_empty_number)
                } else {
                    onAddClickListener.invoke(
                        itemsMap.getValue(unitPosition),
                        viewBinding.etNumberUnit.text.toString().toInt()
                    )
                    hideCreation(viewBinding)
                }

            }
        viewBinding.etNumberUnit.afterTextChanged {
            viewBinding.tilNumberUnit.error = null
        }

    }

    private fun showCreation(viewBinding: ItemDialogRescheduleCreateBinding) {
        viewBinding.btnCreateReschedule.gone()
        viewBinding.clCreateReschedule.show()
    }

    private fun initUnits(tvUnitReschedule: AutoCompleteTextView) {
        val units = context.resources.getStringArray(R.array.units_array).toList()
        if (tvUnitReschedule.text.isEmpty())
            tvUnitReschedule.setText(units[0])
        unitsAdapter.clear()
        unitsAdapter.addAll(units)
        tvUnitReschedule.setAdapter(unitsAdapter)

        tvUnitReschedule.apply {
            isFocusable = false
            isClickable = true
            setOnClickListener {
                this.showDropDown()
            }
            setOnItemClickListener { _, _, position, _ ->
                unitPosition = position
            }
        }
    }

    private fun hideCreation(viewBinding: ItemDialogRescheduleCreateBinding) {
        viewBinding.clCreateReschedule.gone()
        viewBinding.btnCreateReschedule.show()
    }

}