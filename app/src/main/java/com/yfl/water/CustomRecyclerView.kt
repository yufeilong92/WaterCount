package com.yfl.water

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerView : RecyclerView {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attr: AttributeSet) : super(context, attr) {}
    constructor(context: Context, attr: AttributeSet, def: Int) : super(context, attr, def) {}

    var downx: Float = 0.0f
    var downy: Float = 0.0f
    var lastx = 0.0f
    var lasty = 0.0f
    var  isMove:Boolean=false
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        downy = ev!!.getRawY()
        downx = ev.getRawX()
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                lastx = downx
                lasty = downy
                isMove=false
                parent.requestDisallowInterceptTouchEvent(true)
                return super.dispatchTouchEvent(ev)
            }
            MotionEvent.ACTION_MOVE -> {
                val x = lastx - downx
                val y = lasty - downy
                if (Math.abs(x) > Math.abs(y) || Math.abs(y) < 10) {
                    isMove=false
                    parent.requestDisallowInterceptTouchEvent(true)
                    return super.dispatchTouchEvent(ev)
                } else {
                    parent.requestDisallowInterceptTouchEvent(false)
                    return true
                }

            }
        }
        return super.dispatchTouchEvent(ev)
    }
}