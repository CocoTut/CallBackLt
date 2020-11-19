package ru.cherepanovk.feature_events_impl.dialog.reschedule

import android.content.res.Resources
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.xwray.groupie.viewbinding.BindableItem
import ru.cherepanovk.feature_events_impl.R
import ru.cherepanovk.feature_events_impl.databinding.ItemDialogRescheduleBinding

class RescheduleItem(
    val id: Int,
    val unit: RescheduleUnitType,
    val number: Int,
    private val onMenuDeleteClick: (Int) -> Unit
) : BindableItem<ItemDialogRescheduleBinding>() {

    override fun getLayout(): Int  =
        R.layout.item_dialog_reschedule

    override fun initializeViewBinding(view: View): ItemDialogRescheduleBinding {
        return ItemDialogRescheduleBinding.bind(view)
    }

    override fun bind(viewBinding: ItemDialogRescheduleBinding, position: Int) {
        viewBinding.tvUnitReschedule.text = getUnitText(viewBinding.root.resources)
        viewBinding.ivReschedule.setOnClickListener { showPopupMenu(viewBinding.ivReschedule) }
    }

    private fun showPopupMenu(view: View) {
        PopupMenu(view.context, view).apply {
            inflate(R.menu.popup_menu_reschedule)

            setOnMenuItemClickListener {
                if (it.itemId == R.id.deleteReschedule) {
                    onMenuDeleteClick.invoke(id)
                }
                false
            }

            show()
        }
    }

    private fun getUnitText(resources: Resources): CharSequence {
        return when(unit) {
            RescheduleUnitType.MINUTES -> resources.getQuantityString(
                R.plurals.plurals_minute,
                number,
                number
            )
            RescheduleUnitType.HOURS -> resources.getQuantityString(R.plurals.plurals_hour, number, number)
            RescheduleUnitType.DAYS -> resources.getQuantityString(R.plurals.plurals_day, number, number)
        }
    }
}