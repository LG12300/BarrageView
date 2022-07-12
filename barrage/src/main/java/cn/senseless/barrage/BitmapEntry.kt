package cn.senseless.barrage

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

class BitmapEntry(
    private val bitmap: Bitmap,
    maxBounds: RectF,
    paint: Paint
) : BaseEntry(maxBounds, paint) {

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, null, this, paint)
    }
}