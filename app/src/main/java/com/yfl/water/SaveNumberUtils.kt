package com.yfl.water

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.yfl.water.KotlinSerializableUtil.obj2Str
import com.yfl.water.KotlinSerializableUtil.str2Obj
import java.io.IOException
import java.io.StreamCorruptedException

/**
 * @Author : YFL  is Creating a porject in My Application
 * @Package com.yfl.water
 * @Email : yufeilong92@163.com
 * @Time :2020/12/24 11:59
 * @Purpose :
 */
object SaveNumberUtils {
    // 用户名key
    private var msp: SharedPreferences? = null

        val KEY_NAME = "savetokenTAG"

    @SuppressLint("WrongConstant")
    fun SaveNumberUtils(context: Context): SaveNumberUtils? {
        msp = context.getSharedPreferences(
            "data",
            Context.MODE_PRIVATE or Context.MODE_APPEND
        )
        return this
    }
    private var sharedUserUtils: SaveNumberUtils? = null
    private var orderInfom: String? = null


    @Synchronized
    fun initSharedPreference(context: Context) {
        if (sharedUserUtils == null) {
            sharedUserUtils = SaveNumberUtils(context)
        }
    }

    @Synchronized
    fun getInstance(): SaveNumberUtils? {
        return sharedUserUtils
    }

    fun getSharedPref(): SharedPreferences? {
        return msp
    }

    @Synchronized
    fun putTokenTag(vo: String?) {
        val editor = msp!!.edit()
        var str = ""
        try {
            str = obj2Str(vo)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        editor.putString(KEY_NAME, str)
        editor.commit()
        orderInfom = vo
    }

    @Synchronized
    fun getSaveTokenTag(): String? {
        if (orderInfom == null) {
            orderInfom = String()
            //获取序列化的数据
            val str = msp!!.getString(SaveNumberUtils.KEY_NAME, "")
            try {
                val com = str2Obj(str!!)
                if (com != null) {
                    orderInfom = com as String
                }
            } catch (e: StreamCorruptedException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return orderInfom
    }

    @Synchronized
    fun delectTokenTag() {
        val editor = msp!!.edit()
        editor.putString(KEY_NAME, "")
        editor.commit()
        orderInfom = null
    }
}