package cn.senseless.playground.barrage

import java.util.*

class Row(val rowId: Int) : LinkedList<BaseEntry>() {
    val right: Float
        get() = if (isEmpty()) 0f else last.bounds.right
}