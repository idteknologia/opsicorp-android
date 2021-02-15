package com.opsicorp.train_feature.adapter


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsicorp.train_feature.R
import opsigo.com.domainlayer.model.accomodation.train.ConfirmationTrainModel
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_confirmation_order_train.view.*

class ConfirmationTrainAdapter (val context: Context, private var items: ArrayList<ConfirmationTrainModel>): RecyclerView.Adapter<ConfirmationTrainAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_confirmation_order_train, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        holder.itemView.tv_pnr_code.text = data.pnr_code
        holder.itemView.tv_status.text = data.status
        holder.itemView.tv_date_arrival_deaprture.text = data.date_arrival_depart

        holder.itemView.tv_title_train.text = if (data.title_train.split(" ").size>2) data.title_train.split(" ")[0]+" "+data.title_train.split(" ")[0] else data.title_train
        holder.itemView.tv_class_type.text = data.class_type
        holder.itemView.tv_number_sheet.text = ""//data.number_sheet
        holder.itemView.tv_time.text = data.timeDeparture
        holder.itemView.tv_date.text = data.dateDeparture
        holder.itemView.tv_name_city.text = data.name_departure
        holder.itemView.tv_name_station.text = data.name_station_departure
        holder.itemView.line_total_duration.text = data.line_total_duration

        holder.itemView.tv_time_arrival.text = data.time_arrival
        holder.itemView.tv_date_arrival.text = data.date_arrival
        holder.itemView.tv_name_arrival.text = data.name_arrival
        holder.itemView.tv_name_station_arrival.text = data.name_station_arrival
        holder.itemView.tv_total_passager.text = data.total_passager
        holder.itemView.tv_total_prize.text = data.total_prize

    }

    fun setData(data: ArrayList<ConfirmationTrainModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}