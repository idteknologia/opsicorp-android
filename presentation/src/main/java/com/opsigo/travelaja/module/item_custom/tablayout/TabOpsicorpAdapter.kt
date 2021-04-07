package com.opsigo.travelaja.module.item_custom.tablayout

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.tab_item.view.*


class TabOpsicorpAdapter (context: Context, private var items: ArrayList<String>): androidx.recyclerview.widget.RecyclerView.Adapter<TabOpsicorpAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    val context = context

    var inActive  = 0
    var sizeText  = 0
    var colorText = 0
    var colorLineSelected = 0
    var colorLineDefault = 0
    var sizeLineSelected  = 0
    var sizeLineDefault   = 0

    fun setSize(mSize:Int){
        sizeText = mSize
    }

    fun setSizeLinesSeledted(mSize:Int){
        sizeLineSelected = mSize
    }

    fun setSizeLinesDefault(mSize:Int){
        sizeLineDefault = mSize
    }

    fun setColor(mColor:Int){
        colorText = mColor
    }

    fun setColorLinesSelected(mColor:Int){
        colorLineSelected = mColor
    }

    fun setColorLinesDefault(mColor:Int){
        colorLineDefault = mColor
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.tab_item, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = items.get(position)
        holder.itemView.tv_tab.text = data

        if (sizeText!=0) holder.itemView.tv_tab.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.resources.getDimension(sizeText))
        if (colorText!=0) holder.itemView.tv_tab.setTextColor(context.resources.getColor(colorText))

        if (inActive==position){
            if (sizeLineSelected!=0){
                holder.itemView.line_corner.setBackgroundColor(context.resources.getColor(colorLineSelected))
            }
            else{
                holder.itemView.line_corner.setBackgroundColor(context.resources.getColor(R.color.selected_tab))
            }

            val params = holder.itemView.line_corner.getLayoutParams() as LinearLayout.LayoutParams
            if(sizeLineSelected!=0) {
                params.height = context.resources.getDimensionPixelSize(sizeLineSelected)
            }else{
                params.height = context.resources.getDimensionPixelSize(R.dimen.line_selected)
            }
            params.width  = LinearLayout.LayoutParams.MATCH_PARENT
            holder.itemView.line_corner.layoutParams = params
        }else{
            if (sizeLineDefault!=0){
                holder.itemView.line_corner.setBackgroundColor(context.resources.getColor(colorLineDefault))
            }
            else{
                holder.itemView.line_corner.setBackgroundColor(context.resources.getColor(R.color.gray_line))
            }

            val params = holder.itemView.line_corner.getLayoutParams() as LinearLayout.LayoutParams
            if(sizeLineDefault!=0) {
                params.height = context.resources.getDimensionPixelSize(sizeLineDefault)
            }else{
                params.height = context.resources.getDimensionPixelSize(R.dimen.line_not_selected)
            }
            params.width  = LinearLayout.LayoutParams.MATCH_PARENT
            holder.itemView.line_corner.layoutParams = params
        }

        holder.itemView.setOnClickListener {
            inActive = position
            onclick.onClick(-1,position)
        }

    }

    fun setData(data: java.util.ArrayList<String>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}