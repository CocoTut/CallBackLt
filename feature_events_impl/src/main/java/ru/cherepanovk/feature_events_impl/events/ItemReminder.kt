package ru.cherepanovk.feature_events_impl.events

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_reminder.view.*
import ru.cherepanovk.feature_events_impl.R

class ItemReminder(
    val id: String,
    private val description: String,
    val phoneNumber: String,
    private val date: String,
    private val time: String
) : Item() {

    override fun getLayout() = R.layout.item_reminder

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val view = viewHolder.itemView
        view.tvDescription.text = description
        view.tvPhoneNumber.text = phoneNumber
        view.tvDate.text = date
        view.tvTime.text = time
    }

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        if (other !is ItemReminder) return false
        return id == other.id &&
                description == other.description &&
                phoneNumber == other.phoneNumber &&
                date == other.date &&
                time == other.time
    }
}