package ru.cherepanovk.feature_events_impl.events

import android.graphics.drawable.Animatable
import android.os.Handler
import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.cherepanovk.feature_events_impl.R
import ru.cherepanovk.feature_events_impl.databinding.ItemReminderBinding
import ru.cherepanovk.imgurtest.utils.extensions.showOrGone

class ItemReminder(
    val id: String,
    val phoneNumber: String,
    private val contactName: String,
    private val description: String,
    private val date: String,
    private val time: String,
    private val dateContentDescription: String
) : BindableItem<ItemReminderBinding>() {

    private val handler = Handler()
    private var expandDescriptionRequestLayoutCallback: Runnable? = null
    private var descriptionMaxLines = 1

    override fun getLayout() = R.layout.item_reminder

    override fun initializeViewBinding(view: View): ItemReminderBinding {
        return ItemReminderBinding.bind(view)
    }

    override fun bind(viewBinding: ItemReminderBinding, position: Int) {
        viewBinding.btnExpandDescription.setImageResource(R.drawable.ic_expand_more_accent_dark_24dp)
        expandDescriptionRequestLayoutCallback?.let {
            handler.removeCallbacks(it)
        }
        expandDescriptionRequestLayoutCallback =
            Runnable { viewBinding.btnExpandDescription.requestLayout() }
        viewBinding.tvDescription.apply {
            maxLines = descriptionMaxLines
            text = description
            showOrGone(description.isNotBlank())
            if (description.isNotBlank())
                contentDescription =
                    context.getString(
                        R.string.item_reminder_content_description_description,
                        description
                    )
        }
        viewBinding.tvDescription.measure(0, 0)
        viewBinding.rootItemReminder.setDescriptionEllipsizedListener {
            viewBinding.rootItemReminder.setDescriptionEllipsizedListener(null)
            viewBinding.btnExpandDescription.showOrGone(it)
            expandDescriptionRequestLayoutCallback?.let { runnable ->
                handler.post(runnable)
            }
        }

        viewBinding.root.requestLayout()
        viewBinding.tvContactName.apply {
            text = contactName
            contentDescription =
                context.getString(R.string.item_reminder_content_contact_name, contactName)
        }
        viewBinding.tvPhoneNumber.apply {
            text = phoneNumber
            if (phoneNumber.isNotBlank()) {
                contentDescription = context.getString(
                    R.string.item_reminder_content_descrption_phone_number,
                    phoneNumber
                )
            }
        }
        viewBinding.tvDate.apply {
            text = date
            contentDescription = context.getString(R.string.item_reminder_content_description_date, dateContentDescription)
        }
        viewBinding.tvTime.apply {
            text = time
            contentDescription = context.getString(R.string.item_reminder_content_description_time, time)
        }
        viewBinding.btnExpandDescription.apply {
            setImageResource(if (descriptionMaxLines > 1) R.drawable.collapse_animated else R.drawable.expand_animated)
            setOnClickListener {
                descriptionMaxLines = if (viewBinding.tvDescription.maxLines > 1) 1 else 3
                viewBinding.tvDescription.maxLines = descriptionMaxLines
                bindIcon(viewBinding)
            }
        }
    }

    private fun bindIcon(viewBinding: ItemReminderBinding) {
        viewBinding.btnExpandDescription.apply {
            setImageResource(
                if (descriptionMaxLines > 1)
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