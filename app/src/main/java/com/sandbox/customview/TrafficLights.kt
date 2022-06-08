package com.sandbox.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.sandbox.R

class TrafficLights(context: Context?, attrs: AttributeSet): View(context, attrs) {

    private val paint: Paint = Paint()
    private var colorBackground: Int = Color.WHITE
    private var colorRect: Int = Color.GRAY
    private var colorStroke: Int = Color.BLACK
    private var colorGreenLight: Int = Color.BLACK
    private var colorRedLight: Int = Color.RED


    init {
        context?.theme!!.obtainStyledAttributes(
            attrs,
            R.styleable.TrafficLights,
            0,
            0
        ).apply {
            try {
                colorBackground = getColor(R.styleable.TrafficLights_backgroundColor, Color.WHITE)
                when(getInt(R.styleable.TrafficLights_firstActivated, 0)) {
                    0 -> setState(LightState.RED)
                    1 -> setState(LightState.GREEN)
                }
            } finally {
                recycle()
            }
        }
    }


    enum class LightState{
        GREEN, RED
    }

    fun setState(state: LightState){
        when(state){
            LightState.GREEN -> {
                colorGreenLight = Color.GREEN
                colorRedLight = Color.BLACK
            }
            LightState.RED -> {
                colorGreenLight = Color.BLACK
                colorRedLight = Color.RED
            }
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //background
        paint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = colorBackground
        }
        canvas?.drawPaint(paint)

        //Traffic light box
        paint.apply {
            color = colorRect
            style = Paint.Style.FILL
        }
        val rectMargin = 0.2f
        canvas?.drawRect(
            rectMargin*width,
            rectMargin*height,
            (1-rectMargin)*width,
            (1-rectMargin)*height,
            paint
        )

        //Red light
        paint.apply { color = colorRedLight }
        canvas?.drawCircle(
            width/2f,
            1.75f*rectMargin*height,
            0.5f*rectMargin*height,
            paint
        )

        //Green light
        paint.apply { color = colorGreenLight }
        canvas?.drawCircle(
            width/2f,
            (1-1.75f*rectMargin)*height,
            0.5f*rectMargin*height,
            paint
        )

        canvas?.save()
        canvas?.restore()
    }
}