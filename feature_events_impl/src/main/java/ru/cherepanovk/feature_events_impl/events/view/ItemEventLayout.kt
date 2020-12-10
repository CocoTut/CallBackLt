package ru.cherepanovk.feature_events_impl.events.view

import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import ru.cherepanovk.feature_events_impl.R
import ru.cherepanovk.imgurtest.utils.extensions.showOrGone

class ItemEventLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val paint = Paint()
    var bounds = Rect()
    private var onLayoutListener: ((Boolean) -> Unit)? = null

    private lateinit var descriptionTextView: TextView
    private lateinit var iconExpand: ImageView
    private lateinit var descriptionLayoutParams: LayoutParams


    fun setDescriptionEllipsizedListener(listener: ((Boolean) -> Unit)?){
        onLayoutListener = listener
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        descriptionTextView = findViewById(R.id.tvDescription)
        iconExpand = findViewById(R.id.btnExpandDescription)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

//        iconExpand.visibility = if (isDescriptionEllipsized()) VISIBLE else GONE
//        Log.d("ellipsized", "${iconExpand.visibility}")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        onLayoutListener?.invoke(isDescriptionEllipsized())
    }


    private fun isDescriptionEllipsized(): Boolean {
        if (descriptionTextView.visibility == GONE) return false

        paint.textSize = descriptionTextView.textSize;
        paint.typeface = descriptionTextView.typeface
        paint.getTextBounds(
            descriptionTextView.text.toString(),
            0,
            descriptionTextView.text.toString().length,
            bounds
        );
        val ellipsized = bounds.width() > descriptionTextView.measuredWidth
        Log.d("ellipsized", "$ellipsized ${descriptionTextView.text}")
        return ellipsized
    }
}