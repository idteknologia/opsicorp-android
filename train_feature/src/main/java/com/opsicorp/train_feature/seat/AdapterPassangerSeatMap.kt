package com.opsicorp.train_feature.seat

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.train_feature.R
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.seatmap_pasanger_adapter_view.view.*

class AdapterPassangerSeatMap (var context: Context, var items: ArrayList<PassangerSeatMapModel>): androidx.recyclerview.widget.RecyclerView.Adapter<AdapterPassangerSeatMap.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    var positionPick = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.seatmap_pasanger_adapter_view, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)

        if(position==0){
            holder.itemView.line_first.visibility = View.VISIBLE
        }
        else{
            holder.itemView.line_first.visibility = View.GONE
        }

        if (position==(items.size-1)){
            holder.itemView.line_last.visibility = View.VISIBLE
        }
        else {
            holder.itemView.line_last.visibility = View.GONE
        }

        if (position==positionPick){
            setDrawColorPick(holder.itemView,
                    R.color.gray_50_subtitle,
                    R.color.white,
                    R.drawable.rounded_seatmap_adapter_passanger_selected,
                    R.drawable.rounded_seatmap_available)
        }
        else{
            setDrawColorPick(holder.itemView,
                    R.color.white,
                    R.color.gray_50_subtitle,
                    R.drawable.rounded_seatmap_adapter_passanger_pick,
                    R.drawable.rounded_seatmap_pick)
        }

        holder.itemView.title_passanger.text  = "Passanger ${position+1}"
        holder.itemView.name_passanger.text   = data.namePassanger
        holder.itemView.number_seat.text      = data.numberSeatSelected

        holder.itemView.setOnClickListener {
            onclick.onClick(-1,position)
            positionPick = position
            notifyDataSetChanged()
        }
    }

    private fun setDrawColorPick(itemView: View,colorText:Int,colorText2:Int,drawableLineSelected:Int,drawableLineNumberSelect:Int) {
        itemView.line_selected.background = context.resources.getDrawable(drawableLineSelected)
        itemView.circleNumber.background  = context.resources.getDrawable(drawableLineNumberSelect)

        itemView.title_passanger.setTextColor(context.resources.getColor(colorText2))
        itemView.name_passanger.setTextColor(context.resources.getColor(colorText2))
        itemView.number_seat.setTextColor(context.resources.getColor(colorText))
    }

    fun setData(data: ArrayList<PassangerSeatMapModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}