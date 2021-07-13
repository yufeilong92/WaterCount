package com.yfl.water

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView

class CustomScrollView :ScrollView{
    constructor(context:Context):super(context){}
    constructor(context:Context,attr:AttributeSet):super(context,attr){}
    constructor(context:Context,attr: AttributeSet,def:Int):super(context,attr,def){}

    var downx: Float = 0.0f
    var downy: Float = 0.0f
    var lastx = 0.0f
    var lasty = 0.0f
    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        downx = e!!.getRawX()
        downy = e.getRawY()
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                lastx = downx;
                lasty = downy;
                return super.onInterceptTouchEvent(e)
            }
            MotionEvent.ACTION_MOVE -> {
                val x = downx - lastx
                val y = downy - lasty
                if (Math.abs(x) > Math.abs(y) || Math.abs(y) < 10) {
                    return super.onInterceptTouchEvent(e)
                } else {
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(e)
    }
}