package com.yfl.water

/**
 * @Author : YFL  is Creating a porject in My Application
 * @Package com.yfl.water
 * @Email : yufeilong92@163.com
 * @Time :2020/12/24 13:19
 * @Purpose :
 */
class MainVo(
    //上次
    var last: Int,
    //上次卫生间
    var lastY:Int,
    //上次厨房
    var lastc:Int,
    //本次
    var new: Int,
    //本次卫生间
    var newY:Int,
    //本次厨房
    var newc:Int,
    //差数据量
    var difference:Double,
    //差价
    var value:Double
) {
}