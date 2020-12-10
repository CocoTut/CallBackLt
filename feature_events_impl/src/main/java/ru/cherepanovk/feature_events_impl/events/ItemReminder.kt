package ru.cherepanovk.feature_events_impl.events

import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Animatable
import android.os.Handler
import android.view.View
import android.widget.TextView
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
    private val time: String
) : BindableItem<ItemReminderBinding>() {

    private val paint = Paint()
    private var bounds = Rect()
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
        expandDescriptionRequestLayoutCallback = Runnable { viewBinding.btnExpandDescription.requestLayout() }
        viewBinding.tvDescription.apply {
            maxLines = descriptionMaxLines
            text = description
            showOrGone(description.isNotBlank())
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
        viewBinding.tvContactName.text = contactName
        viewBinding.tvPhoneNumber.text = phoneNumber
        viewBinding.tvDate.text = date
        viewBinding.tvTime.text = time
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