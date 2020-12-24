package com.yfl.water

import android.app.Application

/**
 * @Author : YFL  is Creating a porject in My Application
 * @Package com.yfl.water
 * @Email : yufeilong92@163.com
 * @Time :2020/12/24 13:30
 * @Purpose :
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SaveNumberUtils.initSharedPreference(this)
    }
}