package ru.cherepanovk.feature_events_impl.events

import android.graphics.Typeface
import android.graphics.drawable.Animatable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannedString
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.view.View
import android.widget.ImageView
import androidx.core.text.buildSpannedString
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.viewbinding.BindableItem
import kotlinx.android.synthetic.main.item_reminder.view.*
import ru.cherepanovk.feature_events_impl.R
import ru.cherepanovk.feature_events_impl.databinding.ItemDialogRescheduleBinding
import ru.cherepanovk.feature_events_impl.databinding.ItemReminderBinding
import ru.cherepanovk.imgurtest.utils.extensions.isEllipsized
import ru.cherepanovk.imgurtest.utils.extensions.showOrGone

class ItemReminder(
    val id: String,
    val phoneNumber: String,
    private val contactName: String,
    private val description: String,
    private val date: String,
    private val time: String
) : BindableItem<ItemReminderBinding>() {

    override fun getLayout() = R.layout.item_reminder

    override fun initializeViewBinding(view: View): ItemReminderBinding {
        return ItemReminderBinding.bind(view)
    }

    override fun bind(viewBinding: ItemReminderBinding, position: Int) {
        viewBinding.tvDescription.apply {
            text = description
            showOrGone(description.isNotBlank())
        }
        viewBinding.tvContactName.text = contactName
        viewBinding.tvPhoneNumber.text = phoneNumber
        viewBinding.tvDate.text = date
        viewBinding.tvTime.text = time
        viewBinding.btnExpandDescription.apply {
            showOrGone(viewBinding.tvDescription.isEllipsized())
            setOnClickListener {
                viewBinding.tvDescription.maxLines =
                    if (viewBinding.tvDescription.maxLines > 1) 1 else 3
                bindIcon(viewBinding)
            }
        }
    }

    private fun bindIcon(viewBinding: ItemReminderBinding) {
        viewBinding.btnExpandDescription.apply {
            setImageResource(
                if (viewBinding.tvDescription.maxLines > 1)
                    R.drawable.collapse_animated
                else
                    R.drawable.expand_animated
            )
            (drawable as Animatable).start()
        }

    }

    override fun isSameAs(other: com.xwray.groupie.Item<*>): Boolean {
        if (other !is ItemReminder) return false
        return id == other.id &&
                description == other.description &&
                phoneNumber == other.phoneNumber &&
                date == other.date &&
                time == other.time
    }
}