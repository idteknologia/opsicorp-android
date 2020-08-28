package com.opsigo.travelaja.module.my_booking.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.my_booking.purchase_detail.PurchaseDetailActivity
import com.opsigo.travelaja.utility.DateConverter
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import opsigo.com.domainlayer.model.my_booking.MyBookingModel
import kotlinx.android.synthetic.main.item_my_booking_body.view.*
import kotlinx.android.synthetic.main.item_my_booking_header.view.*

class MyBookingAdapter (val context: Context, private var items: ArrayList<MyBookingModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {

            HEADER_MY_BOOKING -> HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_booking_header, parent, false))

            else -> BodyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_booking_body, parent, false))
        }

    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView) {
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            HEADER_MY_BOOKING -> (holder as HeaderHolder).bind(items[position], position)
            BODY_MY_BOOKING -> (holder as BodyHolder).bind(items[position], position)
        }
    }

    inner class HeaderHolder internal constructor(itemView: View) : ViewHolder(itemView) {

        fun bind(data: MyBookingModel, position: Int) {
            itemView.tv_date_my_booking.text =  formatingDate(data.date )

        }

    }

    fun formatingDate(string: String):String{
        val year = DateConverter().formatingDateDefault("dd-MM-yyyy",string).split("-")[0]
        val mount = DateConverter().formatingDateDefault("dd-MM-yyyy",string).split("-")[1].substring(0,3)
        val day = DateConverter().formatingDateDefault("dd-MM-yyyy",string).split("-")[2]

        return "${day} ${mount} ${year}"
    }


    inner class BodyHolder internal constructor(itemView: View) : ViewHolder(itemView) {


        fun bind(data: MyBookingModel, position: Int) {
            itemView.tv_id_booking.text     = data.idBooking
            itemView.tv_departure_city.text = data.cityDeparture
            itemView.tv_arrival_city.text   = data.cityArrival
            itemView.status_payment.text    = data.statusPayment
            itemView.tv_total_prize.text    = "IDR "+data.prize

            when(data.typeAccomdation){
                "Flight" ->{
                    setIconAccomodation(R.drawable.ic_flight_up_selected,itemView.ic_icon_transport)
                }

                "Hotel" ->{
                    setIconAccomodation(R.drawable.ic_hotel_selected,itemView.ic_icon_transport)
                }

                "Train" -> {
                    setIconAccomodation(R.drawable.ic_train_selected,itemView.ic_icon_transport)
                }
            }

            itemView.btn_menu_lis_my_booking.setOnClickListener {
                val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val layout = layoutInflater.inflate(R.layout.menu_popup_list_my_booking, null)
                val btnDetail = layout.findViewById(R.id.tv_view_detail) as TextView
                val btnRemove = layout.findViewById(R.id.tv_remove_list_data) as TextView

                btnDetail.setOnClickListener {
                    if (items.get(position).typeAccomdation=="Flight"){
                        Globals.typeAccomodation = "Flight"
                    }
                    else if(items.get(position).typeAccomdation=="Train"){
                        Globals.typeAccomodation = "Train"
                    }
                    else {
                        Globals.typeAccomodation = "Hotel"
                    }
                    context.startActivity(Intent(context,PurchaseDetailActivity::class.java))
                }

                btnRemove.setOnClickListener {
                    items.removeAt(position)
                    notifyDataSetChanged()
                }

                Globals.showPopup(itemView.btn_menu_lis_my_booking,layout)
                onclick.onClick(-10,position)
            }

            itemView.setOnClickListener {
                onclick.onClick(-1,position)
                if (items.get(position).typeAccomdation=="Flight"){
                    Globals.typeAccomodation = "Flight"
                }
                else if(items.get(position).typeAccomdation=="Train"){
                    Globals.typeAccomodation = "Train"
                }
                else {
                    Globals.typeAccomodation = "Hotel"
                }
                context.startActivity(Intent(context,PurchaseDetailActivity::class.java))
            }

            if (position==items.size-1){
                itemView.line_bottom.visibility = View.VISIBLE
            }
            else{
                itemView.line_bottom.visibility = View.GONE
            }
        }

    }

    private fun setIconAccomodation(imageDrawable: Int,itemView: ImageView) {
        itemView.setImageDrawable(context.resources.getDrawable(imageDrawable))
    }

    fun setData(data: ArrayList<MyBookingModel>) {
        items = data
        notifyDataSetChanged()
    }

    companion object {
        val HEADER_MY_BOOKING = 1
        val BODY_MY_BOOKING = 2
    }

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemViewType(position: Int): Int {
        return when (items.get(position).type) {
            "Header" -> HEADER_MY_BOOKING
            else -> BODY_MY_BOOKING
        }
    }
}