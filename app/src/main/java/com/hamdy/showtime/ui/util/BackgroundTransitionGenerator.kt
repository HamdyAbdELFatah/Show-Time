package com.hamdy.showtime.ui.util

import android.graphics.RectF
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import com.flaviofaria.kenburnsview.Transition
import com.flaviofaria.kenburnsview.TransitionGenerator
import kotlin.math.abs


class BackgroundTransitionGenerator : TransitionGenerator {
    private val DEFAULT_TRANSITION_DURATION = 9000
    private val DEFAULT_TRANSITION_INTERPOLATOR: Interpolator = LinearInterpolator()
    private var transitionDuration: Long = 0
    private var transitionInterpolator: Interpolator? = null
    private var lastTransition: Transition? = null
    private var lastDrawableBounds: RectF? = null
    private var forward = false
    private var MIN_RECT_FACTOR = 1.0f

    fun SetMinRectFactor(f: Float) {
        MIN_RECT_FACTOR = f
    }

    fun BackgroundTransitionGenerator() {
        transitionDuration = DEFAULT_TRANSITION_DURATION.toLong()
        transitionInterpolator = DEFAULT_TRANSITION_INTERPOLATOR
    }

    fun setTransitionGenerator(duration: Long, interpolator: Interpolator) {
        transitionDuration = DEFAULT_TRANSITION_DURATION.toLong()
        transitionInterpolator = DEFAULT_TRANSITION_INTERPOLATOR
    }

    override fun generateNextTransition(drawableBounds: RectF, viewport: RectF): Transition? {
        val drawableRatio = getRectRatio(drawableBounds)
        val viewportRectRatio = getRectRatio(viewport)
        val startRect: RectF
        val endRect: RectF
        if (drawableRatio >= viewportRectRatio) {
            val w = drawableBounds.height() * viewportRectRatio
            val h = drawableBounds.height()
            startRect = RectF(0F, 0F, w, h)
            endRect = generateRandomRect(
                drawableBounds,
                viewport
            ) //new RectF((drawableBounds.width() - w), drawableBounds.height(),drawableBounds.width(), h);
        } else {
            val w = drawableBounds.width()
            val h = drawableBounds.width() / viewportRectRatio
            startRect = RectF(0F, 0F, w, h)
            endRect = generateRandomRect(
                drawableBounds,
                viewport
            ) //new RectF(0, drawableBounds.height() - h, w, drawableBounds.height());
        }
        if (drawableBounds != lastDrawableBounds || !haveSameAspectRatio(
                lastTransition!!.destinyRect,
                viewport
            )
        ) {
            forward = false
        }
        forward = !forward
        lastTransition = if (forward) {
            Transition(startRect, endRect, transitionDuration, transitionInterpolator)
        } else {
            Transition(endRect, startRect, transitionDuration, transitionInterpolator)
        }
        lastDrawableBounds = RectF(drawableBounds)
        return lastTransition
    }

    private fun haveSameAspectRatio(r1: RectF, r2: RectF): Boolean {
        return abs(getRectRatio(r1) - getRectRatio(r2)) <= 0.01f
    }

    private fun generateRandomRect(drawableBounds: RectF, viewportRect: RectF): RectF {
        val drawableRatio = getRectRatio(drawableBounds)
        val viewportRectRatio = getRectRatio(viewportRect)
        val maxCrop: RectF = if (drawableRatio > viewportRectRatio) {
            val r = drawableBounds.height() / viewportRect.height() * viewportRect.width()
            val b = drawableBounds.height()
            RectF(0F, 0F, r, b)
        } else {
            val r = drawableBounds.width()
            val b = drawableBounds.width() / viewportRect.width() * viewportRect.height()
            RectF(0F, 0F, r, b)
        }
        val factor = MIN_RECT_FACTOR
        val width = factor * maxCrop.width()
        val height = factor * maxCrop.height()
        val widthDiff = (drawableBounds.width() - width).toInt()
        val heightDiff = (drawableBounds.height() - height).toInt()
        return RectF(
            widthDiff.toFloat(),
            heightDiff.toFloat(),
            widthDiff + width,
            heightDiff + height
        )
    }

    private fun getRectRatio(rect: RectF): Float {
        return rect.width() / rect.height()
    }

}