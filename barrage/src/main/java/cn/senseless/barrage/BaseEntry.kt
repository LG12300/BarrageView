package cn.senseless.barrage

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF


abstract class BaseEntry(
    private val maxBounds: RectF,
    protected val paint: Paint,
) : RectF() {

    open fun needRemove(): Boolean {
        return right < maxBounds.left
    }

    open fun draw(canvas: Canvas) {
        if (left < maxBounds.right && right > maxBounds.left) {
            onDraw(canvas)
        }
    }

    abstract fun onDraw(canvas: Canvas)
}