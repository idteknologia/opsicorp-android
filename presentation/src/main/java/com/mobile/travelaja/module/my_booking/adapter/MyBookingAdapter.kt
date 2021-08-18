package com.mobile.travelaja.module.my_booking.adapter

import android.view.View
import android.content.Intent
import android.view.ViewGroup
import com.mobile.travelaja.R
import android.content.Context
import android.widget.TextView
import android.widget.ImageView
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.DateConverter
import opsigo.com.domainlayer.model.my_booking.MyBookingModel
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_my_booking_body.view.*
import com.mobile.travelaja.module.my_booking.purchase_detail.PurchaseDetailActivity

class MyBookingAdapter (var context: Context, private var items: ArrayList<MyBookingModel>): androidx.recyclerview.widget.RecyclerView.Adapter<MyBookingAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_my_booking_body, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)

        if (items.size>1){
            if (position!=0){
                if (DateConverter().getDate(data.date,"yyyy-MM-dd HH:mm:ss","yyyy-MM").equals(DateConverter().getDate(items[position-1].date,"yyyy-MM-dd HH:mm:ss","yyyy-MM"))){
                    holder.itemView.tv_date_my_booking.visibility = View.GONE
                }
                else {
                    holder.itemView.tv_date_my_booking.visibility = View.VISIBLE
                }
            }
            else {
                holder.itemView.tv_date_my_booking.visibility = View.VISIBLE
            }
        }
        else {
            holder.itemView.tv_date_my_booking.visibility = View.VISIBLE
        }

        holder.itemView.tv_date_my_booking.text     = DateConverter().getDate(data.date,"yyyy-MM-dd HH:mm:ss","MMMM yyyy")
        holder.itemView.tv_id_booking.text     = "Booking ID: ${data.idBooking.substring(0,11)}"
        holder.itemView.tv_departure_city.text = data.cityDeparture
        holder.itemView.tv_arrival_city.text   = data.cityArrival
        holder.itemView.status_payment.text    = if (data.statusPayment.toLowerCase().equals("paid")) "Purchase Successful" else data.statusPayment
        holder.itemView.tv_total_prize.text    = "IDR ${Globals.formatAmount(data.prize.split(".")[0])}"

        when(data.typeAccomdation){
            Constants.TripType.Airline ->{
                holder.itemView.line_transportation.visibility = View.VISIBLE
                holder.itemView.tv_name_hotel.visibility = View.GONE
                setIconAccomodation(R.drawable.ic_flight_up_selected,holder.itemView.ic_icon_transport)
            }

            Constants.TripType.Hotel ->{
                holder.itemView.line_transportation.visibility = View.GONE
                holder.itemView.tv_name_hotel.visibility = View.VISIBLE
                holder.itemView.tv_name_hotel.text = data.nameAccomodation
                setIconAccomodation(R.drawable.ic_hotel_selected,holder.itemView.ic_icon_transport)
            }

            Constants.TripType.KAI -> {
                holder.itemView.line_transportation.visibility = View.VISIBLE
                holder.itemView.tv_name_hotel.visibility = View.GONE
                setIconAccomodation(R.drawable.ic_train_selected,holder.itemView.ic_icon_transport)
            }
        }

        if (data.isRoundtrip){
            holder.itemView.ic_arrow.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_arrow_roundtrip))
        }
        else {
            holder.itemView.ic_arrow.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_arrow_one_way))
        }

        holder.itemView.btn_menu_lis_my_booking.setOnClickListener {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = layoutInflater.inflate(R.layout.menu_popup_list_my_booking, null)
            val btnDetail = layout.findViewById(R.id.tv_view_detail) as TextView
            val btnRemove = layout.findViewById(R.id.tv_remove_list_data) as TextView

            btnDetail.setOnClickListener {
                if (items.get(position).typeAccomdation== Constants.TripType.Airline){
                    Globals.typeAccomodation = "Flight"
                }
                else if(items.get(position).typeAccomdation== Constants.TripType.KAI){
                    Globals.typeAccomodation = "Train"
                }
                else {
                    Globals.typeAccomodation = "Hotel"
                }
                context.startActivity(Intent(context, PurchaseDetailActivity::class.java))
            }

            btnRemove.setOnClickListener {
                items.removeAt(position)
                notifyDataSetChanged()
            }

            Globals.showPopup(holder.itemView.btn_menu_lis_my_booking,layout)
            onclick.onClick(-10,position)
        }

        holder.itemView.setOnClickListener {
            onclick.onClick(-1,position)
            if (items.get(position).typeAccomdation== Constants.TripType.Airline){
                Globals.typeAccomodation = "Flight"
            }
            else if(items.get(position).typeAccomdation== Constants.TripType.KAI){
                Globals.typeAccomodation = "Train"
            }
            else {
                Globals.typeAccomodation = "Hotel"
            }
            context.startActivity(Intent(context, PurchaseDetailActivity::class.java))
        }

        if (position==items.size-1){
            holder.itemView.line_bottom.visibility = View.VISIBLE
        }
        else{
            holder.itemView.line_bottom.visibility = View.GONE
        }

    }

    fun setData(data: ArrayList<MyBookingModel>) {
        items = data
        notifyDataSetChanged()
    }

    private fun setIconAccomodation(imageDrawable: Int,itemView: ImageView) {
        itemView.setImageDrawable(context.resources.getDrawable(imageDrawable))
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row)
}