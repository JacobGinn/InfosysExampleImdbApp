package com.example.infosysdemoapplication.ui

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View
import com.example.infosysdemoapplication.R

class MattView(context : Context, attrs : AttributeSet) : View(context, attrs) {

    private var mShowText : Boolean? = null
    private var textPos : Int? = null
    private var textHeight : Float = 0.0f

    /**
     * Creating the paints are a major optimization that is made because the view is constantly being remade
     * so not having to recreate the Paints saves some time.
     */
    private val textPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        if (textHeight == 0.0f){
            textHeight = textSize
        }else{
            textSize = textHeight
        }
    }

    private val mattPaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textSize = textHeight
    }

    private val shadowPaint = Paint(0).apply {
        color = 0x101010
        maskFilter = BlurMaskFilter(8f, BlurMaskFilter.Blur.NORMAL)
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MattView,
            0, 0
        ).apply {
            try {
                mShowText = getBoolean(R.styleable.MattView_showText,false)
                textPos = getInteger(R.styleable.MattView_labelPosition, 0)
            }finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    fun isShowText():Boolean{
        return mShowText ?: false
    }

    fun setShowText(showText : Boolean){
        mShowText = showText
        invalidate()
        requestLayout()
    }

}