package com.mobile.travelaja.module.approval.summary

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView

import com.mobile.travelaja.R
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.DateConverter
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import com.squareup.picasso.Picasso
import opsigo.com.domainlayer.model.summary.ItemFlightModel
import opsigo.com.domainlayer.model.summary.ItemHotelModel
import opsigo.com.domainlayer.model.summary.ItemTrainModel
import opsigo.com.domainlayer.model.summary.SummaryModelItems

import java.util.ArrayList

class SummaryAdapter (val context: Context): androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    var items = ArrayList<SummaryModelItems>()
    val TYPE_TRAIN  = "TRAIN"
    val TYPE_FLIGHT = "FLIGHT"
    val TYPE_HOTEL  = "HOTEL"
    val TYPE_HEADER = "HEADER"

    var optionMenuFlight = false
    var optionMenuTrain  = false
    var optionMenuHotel  = false

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {

        return when (viewType){

            VIEW_TYPE_HEADER -> HeaderListCart(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_header_list_summary, parent, false))

            VIEW_TYPE_TRAIN -> TrainHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_train_summary, parent, false))

            VIEW_TYPE_FLIGT -> FlightHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_flight_summary, parent, false))

            else            -> HotelHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_hotel_summary, parent, false))
        }

    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }


    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {

        Log.e("TAG == ${position}? ",items.get(position).typeCard)

        when (items.get(position).typeCard) {

            TYPE_HEADER -> {
                (holder as HeaderListCart).bind(items[position],position)
            }
            TYPE_TRAIN ->{
                (holder as TrainHolder).bind(items[position].dataItemTrain,position)
            }
            TYPE_FLIGHT-> {
                (holder as FlightHolder).bind(items[position].dataItemFlight,position)
            }
            TYPE_HOTEL -> {
                (holder as HotelHolder).bind(items[position].dataItemHotel,position)
            }
        }
    }

    open inner class ViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)

    fun setData(data: ArrayList<SummaryModelItems>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items.get(position).typeCard){
            TYPE_HEADER -> VIEW_TYPE_HEADER
            TYPE_TRAIN  -> VIEW_TYPE_TRAIN
            TYPE_FLIGHT -> VIEW_TYPE_FLIGT
            else        -> VIEW_TYPE_HOTEL
        }
    }

    inner class HeaderListCart internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        var tv_title_parent      :TextView = itemView.findViewById(R.id.tv_title_parent)
        var reason               :TextView = itemView.findViewById(R.id.tv_title_reason)
        fun bind(data: SummaryModelItems, position: Int) {
            tv_title_parent.text          = data.title
            reason.text                   = data.reason

            if (data.reason.isNotEmpty()){
                reason.visibility = View.VISIBLE
            }
            else{
                reason.visibility = View.GONE
            }

            if (data.reason=="Approved"){
                reason.setTextColor(context.resources.getColor(R.color.colorGreenApproval))
            }
        }

    }

    inner class TrainHolder internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        var tv_train        :TextView = itemView.findViewById(R.id.tv_train)
        var tv_destination  :TextView = itemView.findViewById(R.id.tv_destination)
        var tv_pnr_code     :TextView = itemView.findViewById(R.id.tv_pnr_code)
        var tv_status       :TextView = itemView.findViewById(R.id.tv_status)
        var tv_number_sheet :TextView = itemView.findViewById(R.id.tv_number)
        var tv_date_arrival :TextView = itemView.findViewById(R.id.tv_date_arrival)
        var time_departure  :TextView = itemView.findViewById(R.id.time_departure)
        var time_arrival    :TextView = itemView.findViewById(R.id.time_arrival)
        var tv_price        :TextView = itemView.findViewById(R.id.tv_price)
        var img_train       :ImageView= itemView.findViewById(R.id.img_train)
        var btnOption       :ImageView= itemView.findViewById(R.id.btn_option_train)

        fun bind(data: ItemTrainModel, position: Int) {
            tv_train.text            = data.titleTrain
            tv_destination.text      = data.oriDest
            tv_pnr_code.text         = data.pnrCode
            tv_status.text           = data.status
            tv_number_sheet.text     = data.carrierNumber
            tv_date_arrival.text     = DateConverter().getDate(data.dateDeparture,"yyyy-mm-dd","EEE, dd MMMM yyyy")
            time_departure.text      = data.timeDeparture+" "
            time_arrival.text        = data.timeArrival
            tv_price.text            = "IDR "+Globals.formatAmount(data.price.split(".")[0])


            btnOption.setOnClickListener {
                showDialogOption(position,btnOption)
            }

            if (optionMenuTrain){
                btnOption.visibility = View.VISIBLE
            }
            else{
                btnOption.visibility = View.GONE
            }
        }
    }

    inner class FlightHolder internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        var tv_dep_return_tittle :TextView = itemView.findViewById(R.id.tv_depart_return_title)
        var tv_airline      :TextView = itemView.findViewById(R.id.tv_airline)
        var tv_destination  :TextView = itemView.findViewById(R.id.tv_destination)
        var tv_pnr          :TextView = itemView.findViewById(R.id.tv_pnr_train_cart)
        var tv_status       :TextView = itemView.findViewById(R.id.tv_status)
        var tv_number       :TextView = itemView.findViewById(R.id.tv_number)
        var tv_date_arrival :TextView = itemView.findViewById(R.id.tv_date_arrival)
        var time_departure  :TextView = itemView.findViewById(R.id.time_departure)
        var time_arrival    :TextView = itemView.findViewById(R.id.time_arrival)
        var tv_price        :TextView = itemView.findViewById(R.id.tv_price)
        var img_flight      :ImageView= itemView.findViewById(R.id.img_flight)
        var btnOptionFlight :ImageView= itemView.findViewById(R.id.btn_option_flight)


        fun bind(data: ItemFlightModel, position: Int) {
            if(data.num == 0 && data.seq == 0){
                tv_dep_return_tittle.visibility = View.VISIBLE
                tv_dep_return_tittle.text   = "DEPARTURE FLIGHT"
            }else if(data.num == 1 && data.seq == 0){
                tv_dep_return_tittle.visibility = View.VISIBLE
                tv_dep_return_tittle.text   = "RETURN FLIGHT"
            }else{
                tv_dep_return_tittle.visibility = View.GONE
            }

            tv_airline.text          = data.airlineName
            tv_destination.text      = data.airportDeparture
            tv_pnr.text              = data.pnrCode
            tv_status.text           = data.status
            tv_number.text           = data.flightNumber
            tv_date_arrival.text     = DateConverter().getDate(data.dateArrival,"yyyy-mm-dd","EEE, dd MMMM yyyy")
            time_departure.text      = data.timeDeparture
            time_arrival.text        = data.timeArrival
            tv_price.text            = "IDR "+Globals.formatAmount(data.price.split(".")[0])

            if(data.imageFlight.isNotEmpty()){
                Picasso.get()
                        .load(data.imageFlight)
                        .fit()
                        .centerCrop()
                        .into(img_flight)
            }

            btnOptionFlight.setOnClickListener {
                showDialogOption(position,btnOptionFlight)
            }

            if (optionMenuFlight){
                btnOptionFlight.visibility = View.VISIBLE
            }
            else{
                btnOptionFlight.visibility = View.GONE
            }

        }

    }

    inner class HotelHolder internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        var img_hotel             :ImageView= itemView.findViewById(R.id.img_hotel)
        var tv_status_hotel       :TextView = itemView.findViewById(R.id.tv_status_hotel)
        var tv_name_hotel         :TextView = itemView.findViewById(R.id.tv_name_hotel)
        var tv_address_hotel      :TextView = itemView.findViewById(R.id.tv_address_hotel)
        var tv_date_booking_hotel :TextView = itemView.findViewById(R.id.tv_date_booking_hotel)
        var tv_price_hotel        :TextView = itemView.findViewById(R.id.tv_price_hotel)
        var line_spacing          :LinearLayout = itemView.findViewById(R.id.line_spacing)
        var btnOptionHotel        :ImageView = itemView.findViewById(R.id.btn_option_hotel)
        var star1                 :ImageView = itemView.findViewById(R.id.str1)
        var star2                 :ImageView = itemView.findViewById(R.id.str2)
        var star3                 :ImageView = itemView.findViewById(R.id.str3)
        var star4                 :ImageView = itemView.findViewById(R.id.str4)
        var star5                 :ImageView = itemView.findViewById(R.id.str5)

        fun bind(data: ItemHotelModel, position: Int) {
            tv_status_hotel.text        = data.status
            tv_name_hotel.text          = data.nameHotel
            tv_address_hotel.text       = data.address
            tv_date_booking_hotel.text  = DateConverter().getDate(data.dateBooking,"yyyy-mm-dd","EEE, dd MMMM yyyy")
            tv_price_hotel.text         = "IDR "+Globals.formatAmount(data.price.split(".")[0])

            val stars = ArrayList<ImageView>()
            stars.clear()
            stars.add(star1)
            stars.add(star2)
            stars.add(star3)
            stars.add(star4)
            stars.add(star5)

            val totalStart = ArrayList<Int>()

            if (data.starRating.toInt()<6){
                for (i in 0 until data.starRating.toInt()){
                    totalStart.add(i)
                }
            }

            if (position==items.size-1){
                line_spacing.visibility = View.VISIBLE
            }

            if (data.image.isNotEmpty()){
                Picasso.get()
                        .load(data.image)
                        .fit()
                        .centerCrop()
                        .into(img_hotel)
            }

            btnOptionHotel.setOnClickListener {
                showDialogOption(position,btnOptionHotel)
            }

            if (optionMenuHotel){
                btnOptionHotel.visibility = View.VISIBLE
            }
            else{
                btnOptionHotel.visibility = View.GONE
            }

            Globals.setStartImage(stars,totalStart)
        }

    }

    private fun showDialogOption(position: Int,itemView: ImageView) {
        var popup: PopupWindow? = null

        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = layoutInflater.inflate(R.layout.menu_popup_list_my_booking, null)
        val btnDetail = layout.findViewById(R.id.tv_view_detail) as TextView
        val btnRemove = layout.findViewById(R.id.tv_remove_list_data) as TextView

        btnDetail.text = "Approve"
        btnRemove.text = "Reject"

        btnDetail.setOnClickListener {
            popup?.dismiss()
            if (items.get(position).typeCard=="FLIGHT"){
                onclick.onClick(Constants.OPTION_FLIGHT_APPROVE,position)
            }
            else if(items.get(position).typeCard=="TRAIN"){
                onclick.onClick(Constants.OPTION_TRAIN_APPROVE,position)
            }
            else {
                onclick.onClick(Constants.OPTION_HOTEL_APPROVE,position)
            }
        }

        btnRemove.setOnClickListener {
            popup?.dismiss()
            if (items.get(position).typeCard=="FLIGHT"){
                onclick.onClick(Constants.OPTION_FLIGHT_REJECT,position)
            }
            else if(items.get(position).typeCard=="TRAIN"){
                onclick.onClick(Constants.OPTION_TRAIN_REJECT,position)
            }
            else {
                onclick.onClick(Constants.OPTION_HOTEL_REJECT,position)
            }
        }

        popup = Globals.showPopup(itemView,layout)
    }

    companion object {

        val VIEW_TYPE_TRAIN  = 1
        val VIEW_TYPE_FLIGT  = 2
        val VIEW_TYPE_HOTEL  = 3
        val VIEW_TYPE_HEADER = 0
    }

}