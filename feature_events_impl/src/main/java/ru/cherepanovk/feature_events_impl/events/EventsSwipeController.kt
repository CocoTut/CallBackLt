package ru.cherepanovk.feature_events_impl.events

import android.graphics.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import ru.cherepanovk.feature_events_impl.R

class EventsSwipeController(private var swipeListener: SwipeListener? = null) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when(direction) {
            ItemTouchHelper.LEFT -> swipeListener?.swipeLeft(viewHolder as ViewHolder)
            ItemTouchHelper.RIGHT -> swipeListener?.swipeRight(viewHolder as ViewHolder)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val icon: Bitmap
        val p = Paint()
        val context = recyclerView.context

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView
            val height = itemView.bottom.toFloat() - itemView.top.toFloat()
            val width = height / 3
            if (dX > 0) {
                p.setColor(Color.parseColor("#388E3C"))
                val background = RectF(
                    itemView.left.toFloat(),
                    itemView.top.toFloat(), dX, itemView.bottom.toFloat()
                )
                c.drawRect(background, p)
                icon = BitmapFactory.decodeResource(
                    context.getResources(),
                    R.drawable.baseline_call_white_24
                )
                val icon_dest = RectF(
                    itemView.left.toFloat() + width, itemView.top.toFloat() + width, itemView.left
                        .toFloat() + 2 * width, itemView.bottom.toFloat() - width
                )
                c.drawBitmap(icon, null, icon_dest, p)
            } else {
                p.setColor(Color.parseColor("#D32F2F"))
                val background = RectF(
                    itemView.right.toFloat() + dX,
                    itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat()
                )
                c.drawRect(background, p)
                icon = BitmapFactory.decodeResource(
                    context.getResources(),
                    R.drawable.baseline_delete_white_24
                )
                val icon_dest = RectF(
                    itemView.right.toFloat() - 2 * width,
                    itemView.top.toFloat() + width,
                    itemView.right
                        .toFloat() - width,
                    itemView.bottom.toFloat() - width
                )
                c.drawBitmap(icon, null, icon_dest, p)
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    fun removeSwipeListener() {
        swipeListener = null
    }

    interface SwipeListener {
        fun swipeLeft(viewHolder: ViewHolder)

        fun swipeRight(viewHolder: ViewHolder)
    }
}