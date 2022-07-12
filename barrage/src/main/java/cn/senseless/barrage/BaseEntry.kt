package cn.senseless.barrage

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF


abstract class BaseEntry(
    private val maxBounds: RectF,
    private val paint: Paint,
    val bounds: RectF,
) {
    fun offset(dx: Float, dy: Float) {
        bounds.offset(dx, dy)
    }

    fun needRemove(): Boolean {
        return bounds.right < maxBounds.left
    }

    fun draw(canvas: Canvas) {
        if (bounds.left < maxBounds.right && bounds.right > maxBounds.left) {
            canvas.drawRect(bounds, paint)
        }
    }
}