package cn.senseless.barrage

import android.content.Context
import android.graphics.BlendMode
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.os.HandlerThread
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class BarrageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : SurfaceView(context, attrs), SurfaceHolder.Callback {
    private var mIsRunning = false
    private var mIsPause = false
    private val mPaint = Paint()
    private val mBounds = RectF()
    private var mRowCount = 3 //几行
    private var mHorizontalSpace = 5f //行间距
    private var mVerticalSpace = 5f //列间距
    private var mRowHeight = 50f
    private var mSpeed = 0f
    private var mDuration = 8000
    private val mRows = ArrayList<Row>()
    private var mThread: HandlerThread? = null
    private var mHandler: Handler? = null

    init {
        holder.addCallback(this)
        mPaint.isAntiAlias = true
        mPaint.color = Color.GREEN
        mPaint.strokeWidth = 1f
        repeat(mRowCount) {
            mRows.add(Row(it))
        }
    }

    private fun needRefresh(): Boolean {
        mRows.forEach {
            if (!it.isEmpty()) {
                return true
            }
        }
        return false
    }

    fun addBarrage(width: Int, height: Int) {
        if (mBounds.isEmpty) return
        val entry = EmptyEntry(mBounds, mPaint, RectF())
        entry.bounds.right = width.toFloat()
        entry.bounds.bottom = height.toFloat()
        addBarrage(entry)
    }

    fun addBarrage(entry: BaseEntry) {
        mHandler?.post {
            val shortRow = getShortRow()
            var nl = mBounds.right + mHorizontalSpace
            var nt =
                mBounds.bottom - ((mRowCount - shortRow.rowId) * mRowHeight) - (mRowCount - shortRow.rowId - 1) * mVerticalSpace
            if (shortRow.isNotEmpty()) {
                val lr = shortRow.last.bounds.right
                nl = if (lr >= mBounds.right) {
                    lr + mHorizontalSpace
                } else {
                    mBounds.right + mHorizontalSpace
                }
            }
            entry.bounds.offsetTo(nl, nt)
            shortRow.addLast(entry)
            if (!mIsRunning) mHandler?.post(drawTask)
        }
    }

    private fun getShortRow(): Row {
        var index = 0
        var right = 0f
        for (i in mRows.lastIndex downTo 0) {
            val row = mRows[i]
            if (row.isEmpty()) {
                return row
            } else {
                if (row.last.bounds.right < mBounds.right) {
                    return row
                } else {
                    if (right == 0f) {
                        right = row.last.bounds.right
                    } else {
                        if (row.last.bounds.right < right) {
                            right = row.last.bounds.right
                            index = i
                        }
                    }
                }
            }
        }
        return mRows[index]
    }

    fun pause() {
        mIsPause = true
    }

    fun resume() {
        mIsPause = false
        if (needRefresh()) invalidate()
    }

    private val drawTask = object : Runnable {

        override fun run() {
            if (mIsPause || !needRefresh()) {
                mIsRunning = false
                return
            }
            val canvas = holder.lockCanvas() ?: return
            canvas.drawColor(Color.TRANSPARENT, BlendMode.CLEAR)
            for (index in mRows.lastIndex downTo 0) {
                val row = mRows[index]
                val iterator = row.iterator()
                while (iterator.hasNext()) {
                    val item = iterator.next()
                    item.offset(-mSpeed, 0f)
                    if (item.needRemove()) {
                        iterator.remove()
                        continue
                    }
                    item.draw(canvas)
                }
            }
            holder.unlockCanvasAndPost(canvas)
            if (!needRefresh()) {
                mIsRunning = false
            } else {
                mIsRunning = true
                mHandler?.post(this)
            }
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        mThread = HandlerThread(TAG)
        mThread!!.start()
        mHandler = Handler(mThread!!.looper)
        mIsRunning = true
        mHandler!!.post(drawTask)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if (mHandler != null) {
            mHandler!!.post {
                mBounds.set(0f, 0f, width.toFloat(), height.toFloat())
                mSpeed = 5f
            }
        } else {
            mBounds.set(0f, 0f, width.toFloat(), height.toFloat())
            mSpeed = 1f
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        mThread?.quitSafely()
        mThread = null
        mHandler = null
        mIsRunning = false
    }

    companion object {
        private const val TAG = "BarrageView"
    }
}