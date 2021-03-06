package cn.senseless.barrage

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

class EmptyEntry(
    maxBounds: RectF,
    paint: Paint
) : BaseEntry(maxBounds, paint) {

    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(this, paint)
    }
}