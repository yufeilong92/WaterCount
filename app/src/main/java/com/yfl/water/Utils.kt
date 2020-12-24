package com.yfl.water

/**
 * @Author : YFL  is Creating a porject in My Application
 * @Package com.yfl.water
 * @Email : yufeilong92@163.com
 * @Time :2020/12/24 14:10
 * @Purpose :
 */
object Utils {

    private var lastClickTime_Two: Long = 0
    /***
     * 处理多次点击问题 (true 多次 ，false 不是)
     * @return
     */
    fun handleOnDoubleClick_ONE(twn:Int): Boolean {
        val l = System.currentTimeMillis()
        if (l - lastClickTime_Two > twn) {
            lastClickTime_Two = l
            return false
        }
        return true
    }
}