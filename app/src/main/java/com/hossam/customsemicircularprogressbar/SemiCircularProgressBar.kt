package com.hossam.customsemicircularprogressbar

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View

class SemiCircularProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {



    private val backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var maxProgress: Double = 100.0
    private var currentProgress: Double = 0.0
    private var strokeWidthDefault = 20f

    init {
        // Set default paint properties for background
        backgroundPaint.style = Paint.Style.STROKE
        backgroundPaint.strokeCap = Paint.Cap.ROUND
        backgroundPaint.color = Color.LTGRAY
        backgroundPaint.strokeWidth = strokeWidthDefault

        // Set default paint properties for progress
        progressPaint.style = Paint.Style.STROKE
        progressPaint.strokeCap = Paint.Cap.ROUND
        progressPaint.strokeWidth = strokeWidthDefault
        progressPaint.color = Color.parseColor("#194374")
    }

    fun setMax(max: Double) {
        if (max > 0) {
            maxProgress = max
            invalidate()
        }
    }

    fun setProgress(progress: Double) {
        if (progress in 0.0..maxProgress) {
            currentProgress = progress
            invalidate()
        }
    }


    fun setProgressWidth(width: Float) {
        backgroundPaint.strokeWidth = width
        progressPaint.strokeWidth = width
        invalidate()
    }


    fun setProgressWithAnimation(progress: Double, duration: Long = 500) {
        if (progress in 0.0..maxProgress) {
            val animator = ValueAnimator.ofFloat(currentProgress.toFloat(), progress.toFloat())
            animator.duration = duration
            animator.addUpdateListener { animation ->
                currentProgress = animation.animatedValue.toString().toDouble()
                invalidate()
            }
            animator.start()
        } else { currentProgress = maxProgress }


    }

    fun setStrokeWidth(width: Float) {
        backgroundPaint.strokeWidth = width
        progressPaint.strokeWidth = width
        strokeWidthDefault = width
        invalidate()
    }

    fun setProgressColor(color: Int) {
        progressPaint.color = color
        invalidate()
    }

    fun setProgressGradient(startColor: Int, endColor: Int) {
        val shader = LinearGradient(
            0f, 0f, width.toFloat(), (height * 2).toFloat(),
            startColor, endColor, Shader.TileMode.CLAMP
        )
        progressPaint.shader = shader
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val strokeWidth = progressPaint.strokeWidth
        val halfStrokeWidth = strokeWidth / 2f

        val rect = RectF(
            halfStrokeWidth,
            halfStrokeWidth,
            width - halfStrokeWidth,
            height * 2 - halfStrokeWidth
        )

        // Draw the complete background semicircle
        canvas.drawArc(rect, 180f, 180f, false, backgroundPaint)

        // Calculate the angle based on the progress
        val angle = 180f * (currentProgress / maxProgress.toFloat())

        // Draw the progress on top of the background
        canvas.drawArc(rect, 180f, angle.toFloat(), false, progressPaint)
    }
}