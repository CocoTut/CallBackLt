package ru.cherepanovk.feature_events_impl.events

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannedString
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import androidx.core.text.buildSpannedString
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_reminder.view.*
import ru.cherepanovk.feature_events_impl.R

class ItemReminder(
    val id: String,
    val phoneNumber: String,
    private val contactName: String,
    private val description: String,
    private val date: String,
    private val time: String
) : Item() {

    override fun getLayout() = R.layout.item_reminder

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val view = viewHolder.itemView
        view.tvDescription.text = getDescription(description, contactName)
        view.tvPhoneNumber.text = phoneNumber
        view.tvDate.text = date
        view.tvTime.text = time
    }

    override fun isSameAs(other: com.xwray.groupie.Item<*>): Boolean {
        if (other !is ItemReminder) return false
        return id == other.id &&
                description == other.description &&
                phoneNumber == other.phoneNumber &&
                date == other.date &&
                time == other.time
    }

    private fun getDescription(description: String, contactName: String): SpannedString {
        val spannableContactName = SpannableString(contactName).apply {
            setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                contactName.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return buildSpannedString {
            append(spannableContactName)
            if (description.isNotBlank()) {
                append(", ")
                append(description)
            }
        }
    }
}