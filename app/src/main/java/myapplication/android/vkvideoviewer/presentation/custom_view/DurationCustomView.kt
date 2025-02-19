package myapplication.android.vkvideoviewer.presentation.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.withStyledAttributes
import myapplication.android.vkvideoviewer.R

class DurationCustomView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : View(context, attributeSet, defStyle, defTheme) {

    var size: Float = 14f
        set(value) {
            if (value != field){
                field = value
                requestLayout()
            }
        }

    var textColor: Int = context.getColor(R.color.white)
        set(value) {
            if (value != field){
                field = value
                requestLayout()
            }
        }

    var text: String = "00:00"
        set(value) {
            if (value != field) {
                field = value
                requestLayout()
            }
        }

    init {
        context.withStyledAttributes(attributeSet, R.styleable.TimerCustomView) {
            text = getString(R.styleable.TimerCustomView_android_text).toString()
            textColor = getInt(R.styleable.TimerCustomView_android_textColor, 0)
            size = getFloat(R.styleable.TimerCustomView_timerSize, 14f)
        }
        //onEmojiClicked()
    }

    private val textRect = Rect()

    private val textPaint = Paint().apply {
        color = textColor
        textSize = size.toSp()
    }

    private val textToDraw
        get() = text

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        textPaint.getTextBounds(textToDraw, 0, textToDraw.length, textRect)

        val actualWidth = resolveSize(
            paddingLeft + paddingRight + textRect.width(),
            widthMeasureSpec
        ) + 5

        val actualHeight = resolveSize(
            paddingTop + paddingBottom + textRect.height(),
            heightMeasureSpec
        )

        setMeasuredDimension(actualWidth, actualHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textPaint.color = textColor
        val topOffset = height / 2 - textRect.exactCenterY()

        canvas.drawText(textToDraw, paddingLeft.toFloat() , topOffset, textPaint)
    }

    private fun Float.toSp() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this, context.resources.displayMetrics
    )

//    private fun DurationCustomView.onEmojiClicked() {
//        setOnClickListener {
//            it.isSelected = !it.isSelected
//            changeText(it as EmojiView)
//        }
//    }

//    private fun changeText(it: EmojiView) {
//        it.textColor = if (it.isSelected) {
//            it.count++
//            context.getColor(R.color.text_selected)
//        } else {
//            it.count--
//            context.getColor(R.color.text_unselected)
//        }
//    }
}