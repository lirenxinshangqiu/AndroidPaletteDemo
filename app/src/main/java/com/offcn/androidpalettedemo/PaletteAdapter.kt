package com.offcn.androidpalettedemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_palette.view.*

/**
 * ==============================
 *  作者：lushan
 *  创建时间：2020-02-21
 *  描述:
 * ===============================
 */

class PaletteAdapter (private var context: Context,private var list: MutableList<SwatchBean> = mutableListOf()):
    RecyclerView.Adapter<PaletteAdapter.SwatchViewHolder>() {
    private val layoutInflater:LayoutInflater = LayoutInflater.from(context)

    fun setData(list: MutableList<SwatchBean>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class SwatchViewHolder(view:View):RecyclerView.ViewHolder(view){

        fun bindData(bean:SwatchBean){
            with(bean){
                itemView.rgbTv?.apply {
                    text = rgbStr
                    setTextColor(rgb)
                }

                itemView.titleTv?.apply {
                    text = titleTextColorStr
                    setTextColor(titleTextColor)
                }
                itemView.contentTv?.apply {
                    text = bodyTextColorStr
                    setTextColor(bodyTextColor)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwatchViewHolder {
        return SwatchViewHolder(layoutInflater.inflate(R.layout.item_palette,parent,false))

    }

    override fun getItemCount(): Int  = list.size

    override fun onBindViewHolder(holder: SwatchViewHolder, position: Int) {
        holder.bindData(list[position])
    }
}