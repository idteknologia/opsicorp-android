package com.mobile.travelaja.module.cart.adapter

import opsigo.com.datalayer.request_model.accomodation.flight.sync.SyncFlightRequest
import opsigo.com.datalayer.request_model.accomodation.train.sync.SyncTrainRequest
import opsigo.com.datalayer.request_model.accomodation.hotel.sync.SyncHotelRequest
import kotlinx.android.synthetic.main.item_detail_flight_list_card_new.view.*
import kotlinx.android.synthetic.main.item_detail_train_list_card_new.view.*
import opsigo.com.domainlayer.model.accomodation.flight.ProgressFlightModel
import opsigo.com.domainlayer.model.accomodation.train.ProgressTrainModel
import kotlinx.android.synthetic.main.item_detail_header_list_card.view.*
import kotlinx.android.synthetic.main.item_hotel_summary_card_new.view.*
import com.mobile.travelaja.utility.Globals.getDateNowNewFormat
import opsigo.com.domainlayer.callback.CallbackArrayListString
import opsigo.com.domainlayer.callback.CallbackProgressFlight
import opsigo.com.domainlayer.callback.CallbackProgressTrain
import opsigo.com.datalayer.datanetwork.GetDataAccomodation
import com.mobile.travelaja.utility.Constants.TYPE_FLIGHT
import com.mobile.travelaja.utility.Constants.TYPE_HEADER
import com.mobile.travelaja.utility.Constants.TYPE_TRAIN
import com.mobile.travelaja.utility.Constants.TYPE_HOTEL
import com.mobile.travelaja.utility.Globals.getBaseUrl
import com.mobile.travelaja.utility.Globals.getToken
import com.mobile.travelaja.utility.Globals.setLog
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.module.cart.model.*
import opsigo.com.datalayer.mapper.Serializer
import androidx.core.content.ContextCompat
import com.mobile.travelaja.utility.*
import kotlin.collections.ArrayList
import com.squareup.picasso.Picasso
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.ImageView
import android.widget.TextView
import android.content.Context
import android.view.ViewGroup
import com.mobile.travelaja.R
import android.graphics.Color
import java.lang.Exception
import android.view.View
import android.util.Log
import java.util.*

class CartAdapterNew(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    var items = ArrayList<CartModel>()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {

        return when (viewType){

            TYPE_HEADER -> HeaderListCart(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_detail_header_list_card, parent, false))

            TYPE_TRAIN -> TrainHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_detail_train_list_card_new, parent, false))

            TYPE_FLIGHT -> FlightHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_detail_flight_list_card_new, parent, false))

            else        -> HotelHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_hotel_summary_card_new, parent, false))
        }
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }


    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {

        when (items.get(position).typeCard) {

            TYPE_HEADER -> {
                (holder as HeaderListCart).bind(items[position].dataHeader,position)
            }
            TYPE_TRAIN ->{
                (holder as TrainHolder).bind(items[position].dataCardTrain,position)
            }
            TYPE_FLIGHT-> {
                (holder as FlightHolder).bind(items[position].dataCardFlight,position)
            }
            TYPE_HOTEL -> {
                (holder as HotelHolder).bind(items[position].dataCardHotel,position)
            }
        }
    }

    open inner class ViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)

    fun setData(data: ArrayList<CartModel>) {
        items = data
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items.get(position).typeCard){
            TYPE_HEADER -> TYPE_HEADER
            TYPE_TRAIN  -> TYPE_TRAIN
            TYPE_FLIGHT -> TYPE_FLIGHT
            else        -> TYPE_HOTEL
        }
    }

    inner class HeaderListCart internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(data: CartHeaderModel, position: Int) {
            when (data.typeHeader){
                TYPE_TRAIN  -> {
                    itemView.line_image_accomodation.background = context.resources.getDrawable(R.drawable.rounded_cart_header_train)
                    itemView.ic_image_accomodation.setImageDrawable(context.resources.getDrawable(R.drawable.ic_train_cart))

                }
                TYPE_FLIGHT -> {
                    itemView.line_image_accomodation.background = context.resources.getDrawable(R.drawable.rounded_cart_header_flight)
                    itemView.ic_image_accomodation.setImageDrawable(context.resources.getDrawable(R.drawable.ic_flight_cart))
                }
                else        -> {
                    itemView.line_image_accomodation.background = context.resources.getDrawable(R.drawable.rounded_cart_header_train)
                    itemView.ic_image_accomodation.setImageDrawable(context.resources.getDrawable(R.drawable.ic_hotel_cart))
                }
            }

            itemView.title_header.text = data.titleHeader
            itemView.tv_type_trip.text = data.typeTrip

            itemView.btnOption.setOnClickListener {
                showPopUpRemove(itemView.btn_setting,position,data.typeHeader)
            }

        }

    }

    private fun showPopUpRemove(option: ImageView, position: Int, typeHeader: Int) {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = layoutInflater.inflate(R.layout.menu_popup_list_my_booking, null)
        val btnDetail = layout.findViewById(R.id.tv_view_detail) as TextView
        val btnRemove = layout.findViewById(R.id.tv_remove_list_data) as TextView
        val line_center = layout.findViewById(R.id.line_center) as LinearLayout

        btnDetail.visibility = View.GONE
        line_center.visibility = View.GONE

        val dialog = Globals.showPopup(option,layout)

        btnRemove.setOnClickListener {
            when (typeHeader){
                TYPE_TRAIN  -> {
                    onclick.onClick(Constants.ONCLIK_OPTION_REMOVE_TRAIN_CART,position)
                    dialog.dismiss()
                }
                TYPE_FLIGHT -> {
                    onclick.onClick(Constants.ONCLIK_OPTION_REMOVE_FLIGHT_CART,position)
                    dialog.dismiss()
                }
                else        -> {
                    onclick.onClick(Constants.ONCLIK_OPTION_REMOVE_HOTEL_CART,position)
                    dialog.dismiss()
                }
            }
        }
    }



    inner class TrainHolder internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(data: ItemCardTrainModel, position: Int) {

            if (position==(items.size-1)){
                itemView.line_item_vertical_train.visibility = View.INVISIBLE
            }
            else {
                itemView.line_item_vertical_train.visibility = View.VISIBLE
            }

            itemView.tv_status_booking_train.text   = data.status
            if("Expired".equals(data.status)){
                itemView.tv_status_booking_train.setTextColor(Color.parseColor("#BB000E"))
            }

            itemView.tv_pnr_train_cart.text         = if (data.pnrCode.isEmpty()) "-" else data.pnrCode

            itemView.tv_name_train_cart.text        = data.titleTrain
            itemView.tv_class_train_cart.text       = data.classTrain

            itemView.tv_carrier_number.text         = data.carrierNumber
            itemView.tv_seat_number.text            = data.seatText
            itemView.tv_progress_train.text         = "${itemView.context.getString(R.string.seat_on_progress)} ${data.progressTrain}%"

            itemView.tv_departure_date.text         = DateConverter().setDateFormat3(data.dateDeparture)

            if (position==items.size-1){
                itemView.line_bottom.visibility = View.VISIBLE
            }
            else{
                itemView.line_bottom.visibility = View.GONE
            }

            //departure
            itemView.tv_departure_train_cart.text       = data.origin
            itemView.tv_station_departure_cart.text     = data.stationDeparture+" ${itemView.context.getString(R.string.station)}"
            itemView.tv_date_departure_train_cart.text  = DateConverter().setDateFormat4(data.dateDeparture)

            //arrival
            itemView.tv_arrival_train_cart.text         = data.destination
            itemView.tv_station_arrival_cart.text       = data.stationArrival +" ${itemView.context.getString(R.string.station)}"
            itemView.tv_date_arrival_train_cart.text    = DateConverter().setDateFormat4(data.dateArrival)

            try {
                itemView.progress_train.progress = data.progressTrain.split(".")[0].toInt()
            }catch (e:Exception){
                setLog("Error parsing progress")
            }

            itemView.img_arrow.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_arrow_one_way))

            /*if (data.typeTrip==0){
                itemView.img_arrow.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_arrow_one_way))
            }
            else {
                itemView.img_arrow.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_arrow_one_way))
                if (data.isBackTrain){
                    itemView.img_arrow.rotation = 180F
                }
                else {
                    itemView.img_arrow.rotation = 0f
                }
            }*/

            itemView.tv_price_train_cart.text       = data.price

            setLog("--------- id = "+data.idTrain)
            if (data.progressTrain!="100.00"){
                itemView.tv_title_code.text = itemView.context.getString(R.string.booking_id)
                itemView.tv_progress_train.visibility =  View.VISIBLE
                itemView.tv_seat_number.visibility    = View.GONE
                setLog(Serializer.serialize(getDataSyncTrain(data)))
                getSyncTrain(data,position)
            }
            else{
                itemView.tv_title_code.text = itemView.context.getString(R.string.txt_booking_code_pnr)
                itemView.tv_progress_train.visibility =  View.GONE
                itemView.tv_seat_number.visibility    = View.VISIBLE
            }

            itemView.btn_change_seat.setOnClickListener {
                if (data.status=="Reserved"){
                    onclick.onClick(Constants.ONCLICK_CHANGE_SEAT_MAP_TRAIN,position)
                }
            }

            itemView.btn_detail_train.setOnClickListener {
                if (data.status=="Reserved"){
                    onclick.onClick(Constants.ONCLICK_DETAIL_TRAIN,position)
                }
            }

        }

    }

    private fun getSyncTrain(mData: ItemCardTrainModel,position:Int){
        GetDataAccomodation(getBaseUrl(context)).getSyncTrain(getToken(),getDataSyncTrain(mData),object: CallbackArrayListString{
            override fun successLoad(data: ArrayList<String>) {
                if (!data[0].equals("100")){
                    items[position].dataCardTrain.progressTrain = data[0]
                    notifyItemChanged(position)
                    getDataProgressTrain(mData,position)
                    setLog("----------- 0 ")
                }
                else{
                    items[position].dataCardTrain.progressTrain = "100.00"
                    items[position].dataCardTrain.status        = data[1]
                    notifyItemChanged(position)
                    if (!data[1].equals("Reserved")){
                        getSyncTrain(mData,position)
                        onclick.onClick(Constants.PROGRESS_TRAIN_SAVED,position)
                    }
                    else{
                        onclick.onClick(Constants.PROGRESS_TRAIN_CALLBACK,position)
                        setLog("----------- 100 ")
                    }
                }
            }

            override fun failedLoad(message: String) {

            }
        })
    }

    private fun getDataSyncTrain(data: ItemCardTrainModel): HashMap<Any, Any> {
        val mData = SyncTrainRequest()
        mData.pnrId = data.pnrID
        mData.trainId = data.idTrain
        mData.tripPlanId = data.tripId
        mData.travelAgent = Globals.getConfigCompany(context).defaultTravelAgent
        return Globals.classToHashMap(mData,SyncTrainRequest::class.java)
    }


    private fun getDataProgressTrain(data: ItemCardTrainModel, position: Int) {
        GetDataAccomodation(getBaseUrl(context)).getProgressTrain(getToken(),data.idTrain,object :CallbackProgressTrain{
            override fun success(progressData: ProgressTrainModel) {
                items[position].dataCardTrain.progressTrain = progressData.progress.toString()
                notifyItemChanged(position)
                if (progressData.progress!=100.0){
                    getProgressTrainAgain(data,position)
                }
                else{
                    onclick.onClick(Constants.PROGRESS_TRAIN_CALLBACK,position)
                }
            }

            override fun failed(message: String) {

            }
        })
    }

    private fun getProgressTrainAgain(data: ItemCardTrainModel, position: Int) {
        Globals.delay(5000,object :Globals.DelayCallback{
            override fun done() {
                getDataProgressTrain(data,position)
            }
        })
    }

    inner class FlightHolder internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(data: ItemCardFlightModel, position: Int) {
            if (position==(items.size-1)&&items.size>1){
                itemView.line_item_vertical_flight.visibility = View.INVISIBLE
            }
            else {
                itemView.line_item_vertical_flight.visibility = View.VISIBLE
            }

            if(data.imageFlight.isNotEmpty()){
                Picasso.get()
                        .load(data.imageFlight)
                        .fit()
                        .centerCrop()
                        .into(itemView.img_airline)
            }

            itemView.tvDetailsCart.setOnClickListener {
                if (data.status=="Reserved"){
                    onclick.onClick(Constants.ONCLICK_DETAIL_FLIGHT,position)
                }
            }

            itemView.tv_status_flight_cart.text   = data.status
            if("Expired".equals(data.status)){
                itemView.tv_status_flight_cart.setTextColor(Color.parseColor("#BB000E"))
            }else{
                itemView.tv_status_flight_cart.setTextColor(Color.parseColor("#2b9a8e"))
            }

            itemView.tv_date_departure_flight_cart_top.text = getDateNowNewFormat()
            itemView.tv_airline_name.text                  = data.titleFlight
            itemView.tv_pnr_flight_cart.text               = data.pnrCode + "x-pos " + position
            itemView.tv_progress_flight.text               = "on progress ${data.progressFlight}%"

            try {
                itemView.progress_flight.progress = data.progressFlight.split(".")[0].toInt()
            }catch (e:Exception){
                setLog("Error parsing progress")
            }

            try {
                itemView.progress_flight.progress = data.progressFlight.split(".")[0].toInt()
            }catch (e:Exception){
                setLog("Error parsing progress")
            }

            //departure
            itemView.tv_origin_flight_cart.text             = data.flightSegmentItem[0].cityCodeDeparture
            itemView.tv_airport_departure_cart.text         = data.flightSegmentItem[0].airportDeparture
            itemView.tv_date_departure_flight_cart.text     = DateConverter().setDateFormat4(data.flightSegmentItem[0].dateDeparture)

            //arrival
            itemView.tv_destination_flight_cart.text        = data.flightSegmentItem[0].cityCodeArrival
            itemView.tv_airport_arrival_cart.text           = data.flightSegmentItem[0].airportArrival
            itemView.tv_date_arrival_flight_cart.text       = DateConverter().setDateFormat4(data.flightSegmentItem[0].dateArrival)

            itemView.tv_class_flight_cart.text              = data.classFlight
            itemView.tv_subclass_cart.text                  = data.subClass
            itemView.tv_flight_number.text                  = data.flightNumber

            if (data.typeFlight.equals(0)){
                itemView.arrowOneWay.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_arrow_one_way))
            } else {
                if (data.flightSegmentItem.size > 1){
                    if (data.flightSegmentItem[0].pnrCode == data.flightSegmentItem[1].pnrCode){
                        itemView.arrowOneWay.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_arrow_roundtrip))
                    } else {
                        itemView.arrowOneWay.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_arrow_one_way))
                    }
                } else {
                    itemView.arrowOneWay.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_arrow_one_way))
                }
            }

            if (data.numberSheet.isEmpty()) {
                itemView.tv_number_seat_flight_cart.visibility = View.GONE
            }else{
                itemView.tv_number_seat_flight_cart.text    = data.numberSheet
            }

            itemView.tv_price_flight_cart.text = "IDR ${Globals.formatAmount(data.price.split(".")[0])}"

//
            if(data.isComply) {
                itemView.tv_not_comply.visibility = View.GONE
            } else {
                itemView.tv_not_comply.visibility = View.VISIBLE
            }

            if("Expired".equals(data.status)){
                itemView.tv_status_flight_cart.setTextColor(Color.parseColor("#BB000E"))
            }

            itemView.tv_status_flight_cart.text            = data.status
            itemView.tv_pnr_flight_cart.text               = if (data.pnrCode.isEmpty()) "-" else data.pnrCode
            itemView.tv_number_seat_flight_cart.text       = data.numberSheet
            itemView.tv_class_flight_cart.text             = data.classFlight

            val iprog = (data.progressFlight.toDouble()).toInt()
            if (iprog != 100){
                itemView.tv_title_code_fl.text = itemView.context.getString(R.string.booking_id)
                itemView.tv_progress_flight.visibility =  View.VISIBLE
                getDataSyncFlight(data,position)
            }else{
                itemView.tv_title_code_fl.text = itemView.context.getString(R.string.txt_booking_code_pnr)
                itemView.tv_progress_flight.visibility =  View.GONE
            }

        }
    }

    private fun getDataSyncFlight(dataFlight: ItemCardFlightModel, position: Int) {
        GetDataAccomodation(getBaseUrl(context)).getSyncronizeFlight(getToken(),dataSyncFlight(dataFlight),object :CallbackArrayListString{
            override fun successLoad(data: ArrayList<String>) {
                if (!data[0].equals("100")){
                    items[position].dataCardFlight.progressFlight = data[0]
                    notifyItemChanged(position)
                    getDataProgressFlight(dataFlight,position)
                }
                else{
                    items[position].dataCardFlight.progressFlight = "100.00"
                    items[position].dataCardFlight.status        = data[1]
                    notifyItemChanged(position)
                    if (!data[1].equals("Reserved")){
                        getDataSyncFlight(dataFlight,position)
                        onclick.onClick(Constants.PROGRESS_FLIGHT_SAVED,position)
                    }
                    else{
                        onclick.onClick(Constants.PROGRESS_FLIGHT_CALLBACK,position)
                        setLog("----------- 100 ")
                    }
                }
            }

            override fun failedLoad(message: String) {

            }
        })
    }

    private fun dataSyncFlight(data: ItemCardFlightModel): HashMap<Any, Any> {
        val mData = SyncFlightRequest()
        mData.flightId = data.idFlight
        mData.pnrId    = data.pnrId
        mData.travelAgent = Globals.getConfigCompany(context).defaultTravelAgent
        mData.tripPlanId  = data.tripId
        return Globals.classToHashMap(mData,SyncFlightRequest::class.java)
    }

    private fun getDataProgressFlight(data: ItemCardFlightModel, position: Int) {
        GetDataAccomodation(getBaseUrl(context)).getProgressFlight(getToken(),data.idFlight,object : CallbackProgressFlight {
            override fun success(progressData: ProgressFlightModel) {
                Log.d("xixxx","idflit : " + progressData.idFlight)
                Log.d("xixxx","pnr : " + progressData.pnrID)
                Log.d("xixxx","progress : " + progressData.progress)

                items[position].dataCardFlight.progressFlight = progressData.progress.toString()
                notifyItemChanged(position)
                if (progressData.progress!=100.00){
                    getProgressFlightAgain(data,position)
                }
                else{
                    Log.d("xixxx","progress 100!!!: " + progressData.progress)
                    onclick.onClick(Constants.PROGRESS_FLIGHT_CALLBACK,position)
                }

            }

            override fun failed(message: String) {

            }
        })
    }

    private fun getProgressFlightAgain(data: ItemCardFlightModel, position: Int) {
        Globals.delay(5000,object :Globals.DelayCallback{
            override fun done() {
                getDataProgressFlight(data,position)
            }
        })
    }

    inner class HotelHolder internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(data: ItemCardHotelModel, position: Int) {

            if (position==(items.size-1)){
                itemView.line_item_vertical_hotel.visibility = View.INVISIBLE
            }
            else {
                itemView.line_item_vertical_hotel.visibility = View.VISIBLE
            }

            if("Expired".equals(data.status)){
                itemView.tv_status_hotel_cart.setTextColor(Color.parseColor("#BB000E"))
            }
            else if("Saved".equals(data.status)){
                getSyncHotel(data,position)
            }
            else if ("Pending".equals(data.status)){
//                onclick.onClick(Constants.PROGRESS_HOTEL_CALLBACK,position)
            }

            if (position==items.size-1){
                itemView.line_bottom_hotel.visibility = View.VISIBLE
            }
            else{
                itemView.line_bottom_hotel.visibility = View.GONE
            }

            itemView.tv_status_hotel_cart.text       = data.status
            if (data.pnrCode.isEmpty()||data.pnrCode=="null"){
                itemView.tv_pnr_hotel_cart.text      = "-"
            }
            else {
                itemView.tv_pnr_hotel_cart.text      = data.pnrCode
            }
            itemView.tv_type_hotel_cart.text         = data.typeHotel
            itemView.tv_description_hotel_cart.text  = data.descreption
            itemView.tv_date_booking_hotel_cart.text = DateConverter().getDate(data.dateBooking.split(" ")[0],"yyyy-MM-dd","EEEE, yyyy-MMM-dd")
            itemView.tv_name_hotel_cart.text         = data.nameHotel
            itemView.tv_prize_hotel_cart.text        = Globals.formatAmount(data.price.split(".")[0])

            if (data.image.isNotEmpty()){
                Picasso.get()
                        .load(data.image)
                        .fit()
                        .into(itemView.tv_image_hotel_cart)
            }

            itemView.btn_detail_hotel.setOnClickListener {
                onclick.onClick(Constants.ONCLICK_DETAIL_HOTEL,position)
            }
        }
    }

    private fun getSyncHotel(mData: ItemCardHotelModel,position:Int){
        GetDataAccomodation(getBaseUrl(context)).getSyncHotel(getToken(),getDataSyncHotel(mData),object: CallbackArrayListString{
            override fun successLoad(data: ArrayList<String>) {
                onclick.onClick(Constants.PROGRESS_HOTEL_CALLBACK,position)
            }

            override fun failedLoad(message: String) {

            }
        })
    }

    private fun getDataSyncHotel(mData: ItemCardHotelModel): HashMap<Any, Any> {
        val data = SyncHotelRequest()
        data.hotelId  = mData.tripItemId
        data.maxRetry = 0
        data.pnrId    = mData.pnrId
        data.travelAgent = Globals.getConfigCompany(context).defaultTravelAgent
        return Globals.classToHashMap(data,SyncHotelRequest::class.java)
    }

}