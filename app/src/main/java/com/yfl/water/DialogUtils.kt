package com.yfl.water

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_input_number.*
import java.util.*

/**
 * @Author : YFL  is Creating a porject in My Application
 * @Package com.yfl.water
 * @Email : yufeilong92@163.com
 * @Time :2020/12/24 9:56
 * @Purpose :
 */
object DialogUtils {

    fun showInputNumberUserDialog(mContext: Activity, onSure: (number: Int) -> Unit) {
        var number = "0"
        val dialog = object : GmDialog(mContext, R.layout.dialog_input_number, Gravity.CENTER) {
            override fun initViewData() {
                showSoftInputFromWindow(mContext, et_dialog_input_number)
                btn_dialog_sure.setOnClickListener {
                    number = et_dialog_input_number.text.toString()
                    if (TextUtils.isEmpty(number)) {
                        Toast.makeText(mContext, "输入数量为空", Toast.LENGTH_SHORT).show();
                        return@setOnClickListener
                    }
                    if (number.toInt()>300){
                        Toast.makeText(mContext, "数量超出计算范围", Toast.LENGTH_SHORT).show();
                        return@setOnClickListener
                    }
                    onSure(number.toInt())
                    dismiss()
                }
            }
        }
        dialog.show()

    }

    /**
     * EditText获取焦点并显示软键盘
     */
    fun showSoftInputFromWindow(activity: Activity, editText: EditText) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val inputManager = activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.showSoftInput(editText, 0)
            }
        }, 300)
    }

    /***
     * @param mContext
     * @param lat 经度
     * @param lng 维度
     * @param selectClick 数据
     * @return
     */
    fun showChangerOrderInfom(
        mContext: Context,
        canable: Boolean,
        y: String,c:String, isNew: Boolean,
        sure: (y: String, w: String, isNew: Boolean) -> Unit
    ) {
        val dialogBuilde = AlertDialog.Builder(mContext)
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_showchanger_infom, null)

        val etc = view.findViewById<EditText>(R.id.et_show_changer_input_c)
        val etY = view.findViewById<EditText>(R.id.et_show_changer_input_y)
        val tvSure = view.findViewById<TextView>(R.id.tv_show_changer_infom_sure)

        etc.setText(c)
        etY.setText(y)
        dialogBuilde.setView(view)
        dialogBuilde.setCancelable(canable)
        dialogBuilde.create()
        val dialog = dialogBuilde.show()
        tvSure.setOnClickListener {
            var w = etc.text.toString()
            if (TextUtils.isEmpty(w)) {
                w = "0"
            }
            var y = etY.text.toString()
            if (TextUtils.isEmpty(y))
                y = "0"
            dialog.dismiss()
            sure(y, w, isNew)
        }
    }
}