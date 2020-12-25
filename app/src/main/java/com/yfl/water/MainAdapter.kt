package com.yfl.water

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author : YFL  is Creating a porject in My Application
 * @Package com.yfl.water
 * @Email : yufeilong92@163.com
 * @Time :2020/12/24 13:18
 * @Purpose :主界面适配器
 */
class MainAdapter(var mContext: Context, var data: MutableList<MainVo>) :
    RecyclerView.Adapter<MainAdapter.ViewHolde>() {
    private var mInfalter: LayoutInflater? = null

    init {
        mInfalter = LayoutInflater.from(mContext)
    }

    interface RecyclerItemListener {
        fun itemlossClickListener(position: Int)
        fun itemNewClickListener(position: Int)
    }

    private var listener: RecyclerItemListener? = null;

    fun setRecyclerListener(listener: RecyclerItemListener) {
        this.listener = listener
    }

    class ViewHolde(view: View) : RecyclerView.ViewHolder(view) {
        val last = view.findViewById<TextView>(R.id.tv_main_last)
        val new = view.findViewById<TextView>(R.id.tv_main_new)
        val number = view.findViewById<TextView>(R.id.tv_main_number)
        val money = view.findViewById<TextView>(R.id.tv_main_money)
        val lastBeface=view.findViewById<TextView>(R.id.tv_main_beface_zong)
        val newNow=view.findViewById<TextView>(R.id.tv_main_new_zong)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolde {
        return ViewHolde(mInfalter!!.inflate(R.layout.main_item, null))
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolde, position: Int) {
        val mainVo = data[position]
        holder.number.text = "${position + 1}"
        holder.new.text = "${mainVo.new}"
        holder.last.text = "${mainVo.last}"
        holder.money.text = "${mainVo.difference}吨--${mainVo.value}元"
        holder.lastBeface.text="${mainVo.lastY}(阳)+${mainVo.lastc}(卫)"
        holder.newNow.text="${mainVo.newY}(阳)+${mainVo.newc}(卫)"

        holder.new.setOnClickListener {
            listener?.itemNewClickListener(position)
        }
        holder.last.setOnClickListener {
            listener?.itemlossClickListener(position)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}