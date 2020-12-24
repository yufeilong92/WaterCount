package com.yfl.water

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mArray: MutableList<MainVo>? = null

    private var mAdapter: MainAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clearData()
        initEvent()
    }

    private fun initEvent() {
        val instance = SaveNumberUtils.getInstance()
        val number = instance?.getSaveTokenTag()
        if (TextUtils.isEmpty(number)) {
            showDialog()
        } else {
            initData(number = number!!.toInt())
        }
        //重新输入户
        btn_main_input.setOnClickListener {
            showDialog()
        }
        //计算
        btn_main_count.setOnClickListener {
            countData()
        }
        setAllWater(0.0)

    }


    private fun setAllWater(number: Double) {
        val html =
            Html.fromHtml("实际使用<font color='#32CD32'><normal>" + number + "</normal></font>吨")
        tv_main_dun_all.text = html
    }

    private fun countData() {

        val zallWater = et_main_z_water.text.toString()
        if (TextUtils.isEmpty(zallWater)) {
            Toast.makeText(this, "请输入总顿数", Toast.LENGTH_SHORT).show();
            return
        }
        val allMoney = et_main_z_money.text.toString()
        if (TextUtils.isEmpty(allMoney)) {
            Toast.makeText(this, "请输入总金额", Toast.LENGTH_SHORT).show();
        }

        if (mArray.isNullOrEmpty()) {
            Toast.makeText(this, "请点击输入楼户数", Toast.LENGTH_SHORT).show();
            return
        }
        //计算平均价格 水场每吨价格
        avgValue()
        //总水量
        var allWater = 0.0
        mArray?.forEach {
            if (it.new == 0 || it.last == 0) {
            } else {
                val sub = JavaArithUtil.sub(it.new.toDouble(), it.last.toDouble(), 2)
                allWater += sub
            }
        }
        allWater = JavaArithUtil.toDouble(allWater, 2)

        setAllWater(allWater)
        //实际 每吨价格
        val avg = factAvg(allWater)
        //计算每个用户的吨位，和价钱
        mArray?.forEach {
            if (it.new == 0 || it.last == 0) {
            } else {
                val sub = JavaArithUtil.sub(it.new.toDouble(), it.last.toDouble(), 2)
                if (sub < 0) {
                } else {
                    val value = JavaArithUtil.mul(sub, avg, 2)
                    it.value = value
                    it.difference = sub
                }
            }
        }
        //实际交付总价价格(平均下的吨位，乘 单价)
        var realityAllValue = 0.0
        mArray?.forEach {
            realityAllValue += it.value
        }
        realityAllValue = JavaArithUtil.toDouble(realityAllValue, 2)
        tv_main_sf.text = "$realityAllValue"

        //与水场差价
        val money = et_main_z_money.text.toString()
        if (TextUtils.isEmpty(money) || money == "0") {
        } else {
            val cj = JavaArithUtil.sub(money.toDouble(), realityAllValue, 2)
            tv_main_cj.text = "$cj"
        }

        mAdapter?.notifyDataSetChanged()


    }

    private fun clearViewData() {
        setAllWater(0.0)
        tv_main_avg.text = "0"
        tv_main_loss.text = "0"
        tv_main_sf.text = "0"
        tv_main_cj.text = "0"
    }

    //实际
    private fun factAvg(allWater: Double): Double {
        val money = et_main_z_money.text.toString()
        if (TextUtils.isEmpty(money) || money == "0") return 0.0
        val avg = JavaArithUtil.div(money.toDouble(), allWater.toDouble(), 2)
        tv_main_loss.text = "$avg"
        return avg
    }

    private fun showDialog() {
        if (Utils.handleOnDoubleClick_ONE(1000)) {
            return
        }
        val instance = SaveNumberUtils.getInstance()
        DialogUtils.showInputNumberUserDialog(this) {
            instance?.putTokenTag("$it")
            initData(it)
            clearViewData()
        }
    }

    private fun initData(number: Int) {
        if (number == 0) {
            Toast.makeText(this, "数量不能为0", Toast.LENGTH_SHORT).show();
            return
        }
        val list = mutableListOf<MainVo>()
        for (index in 1..number) {
            list.add(MainVo(0, 0, 0.0, 0.0))
        }
        clearData()
        addData(list)
        mAdapter = MainAdapter(this, mArray!!)
        val gm = GridLayoutManager(this, 1)
        rlv_main_content.layoutManager = gm
        rlv_main_content.adapter = mAdapter!!
        mAdapter?.setRecyclerListener(object : MainAdapter.RecyclerItemListener {
            override fun itemlossClickListener(position: Int, number: Int) {
                showChangerDialog(false, number, position)
            }

            override fun itemNewClickListener(position: Int, number: Int) {
                showChangerDialog(true, number, position)
            }

        })
        if (!mArray.isNullOrEmpty()) {
            avgValue()
        }

    }

    private fun showChangerDialog(isNew: Boolean, number: Int, postion: Int) {
        DialogUtils.showChangerOrderInfom(this, true, "$number", isNew)
        { data: String, isNew: Boolean ->
            val numberInt = data.toInt()
            if (isNew) {
                val last = mArray!![postion].last
                if (last != 0 && numberInt < last) {
                    Toast.makeText(this, "输入有误", Toast.LENGTH_SHORT).show();
                    return@showChangerOrderInfom
                }
                mArray!![postion].new = numberInt
            } else {
                val new = mArray!![postion].new
                if (new != 0 && numberInt > new) {
                    Toast.makeText(this, "输入有误", Toast.LENGTH_SHORT).show();
                    return@showChangerOrderInfom
                }
                mArray!![postion].last = numberInt
            }

            mAdapter?.notifyItemChanged(postion)
        }

    }

    private fun avgValue() {
        val money = et_main_z_money.text.toString()
        if (TextUtils.isEmpty(money) || money == "0") return
        val water = et_main_z_water.text.toString()
        if (TextUtils.isEmpty(water) || water == "0") return
        val avgValue = JavaArithUtil.div(money.toDouble(), water.toDouble(), 2)
        tv_main_avg.text = "$avgValue"
    }

    private fun clearData() {
        if (mArray == null) {
            mArray = ArrayList()
        } else {
            mArray!!.clear()
        }
    }

    private fun addData(list: MutableList<MainVo>?) {
        if (list == null || list.isEmpty()) {
            return
        }
        if (mArray == null) {
            clearData()
        }
        mArray!!.addAll(list)
    }

}

