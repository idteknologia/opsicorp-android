package com.opsigo.travelaja.module.accomodation.dialog.accomodation_preferance


import android.content.Context
import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_adapter_filter_accomodation_selected.view.*


class AccomodationPreferanceAdapter (context: Context, private var items: ArrayList<AccomodationPreferanceModel>): androidx.recyclerview.widget.RecyclerView.Adapter<AccomodationPreferanceAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    val context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_adapter_filter_accomodation_selected, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

//        holder.itemView.checkbox.isChecked = getDataLogin.checked

        if(data.checked){
            val mainHandler = Handler(context.getMainLooper())
            mainHandler.post(Runnable { holder.itemView.checkbox.isChecked = true })
        }
        else{
            val mainHandler = Handler(context.getMainLooper())
            mainHandler.post(Runnable { holder.itemView.checkbox.isChecked = false })
        }

//        holder.itemView.checkbox.setOnCheckedChangeListener(null)

        if (data.time.isNotEmpty()){
            holder.itemView.tv_time.text = data.time
            holder.itemView.tv_time.visibility = View.VISIBLE
        }
        else{
            holder.itemView.tv_time.visibility = View.GONE
        }

        holder.itemView.tv_name_time.text = data.name

        holder.itemView.setOnClickListener {
            if (data.name=="Select All"){
                if (data.checked){
                    onclick.onClick(-3,position)
                }
                else{
                    onclick.onClick(-2,position)
                }
            }else{
                onclick.onClick(-5,position)
            }
        }

        holder.itemView.checkbox.setOnClickListener {
            if (data.name=="Select All"){
                if (data.checked){
                    onclick.onClick(-3,position)

                }
                else{
                    onclick.onClick(-2,position)
                }
            }else{
                onclick.onClick(-5,position)
            }
        }

    }

    fun setData(data: java.util.ArrayList<AccomodationPreferanceModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}