package com.opsigo.travelaja.module.accomodation.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.*
import com.opsigo.travelaja.utility.Globals.setLog
import com.squareup.picasso.Picasso
import opsigo.com.domainlayer.model.accomodation.AccomodationResultModel
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel
import opsigo.com.domainlayer.model.accomodation.train.ResultListTrainModel
import kotlinx.android.synthetic.main.layout_filter_result_hotel.view.*
import java.util.ArrayList

/**
 * Created by khoiron on 04/09/18.
 */

class ResultAccomodationAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    var dataList = ArrayList<AccomodationResultModel>()
    lateinit var onclick: OnclickListenerRecyclerView
    lateinit var context: Context
    lateinit var onclickAnimation : OnclickListenerRecyclerViewAnimation

    fun setDataList(dataList: ArrayList<AccomodationResultModel>, context: Context) {
        this.dataList = dataList
        notifyDataSetChanged()
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {

        return when (viewType){

            VIEW_TYPE_TRAIN -> TrainHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_filter_result_train, parent, false))

            VIEW_TYPE_FLIGT -> FlightHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_filter_result_flight, parent, false))

            VIEW_TYPE_HOTEL -> HotelHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_filter_result_hotel, parent, false))

            VIEW_TYPE_LOADING -> FLightLoading(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_filter_result_flight_loading, parent, false))

            VIEW_TYPE_LOADING_HOTEL -> HotelLoading(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_filter_result_hotel_loading, parent, false))

            VIEW_TYPE_HEADER_NOT_COMPLY -> NotComplyHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_header_not_comply, parent, false))

            VIEW_TYPE_HEADER_SOLD_OUT -> SoldOutHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_header_sold_out, parent, false))

            else -> BusHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_filter_result_flight, parent, false))
        }

    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_TRAIN -> (holder as TrainHolder).bind(dataList[position].listTrainModel,position)
            VIEW_TYPE_FLIGT -> (holder as FlightHolder).bind(dataList[position].listFlightModel,position)
            VIEW_TYPE_HOTEL -> (holder as HotelHolder).bind(dataList[position],position)
            VIEW_TYPE_LOADING -> (holder as FLightLoading).bind(dataList[position],position)
            VIEW_TYPE_BUS   -> (holder as BusHolder).bind(dataList[position],position)
            VIEW_TYPE_LOADING_HOTEL     -> (holder as HotelLoading).bind(dataList[position],position)
            VIEW_TYPE_HEADER_NOT_COMPLY -> (holder as NotComplyHolder).bind(dataList[position],position)
            VIEW_TYPE_HEADER_SOLD_OUT   -> (holder as SoldOutHolder).bind(dataList[position],position)
        }
    }

    open inner class ViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position].typeLayout){
            Constants.TYPE_TRAIN    -> VIEW_TYPE_TRAIN
            Constants.TYPE_FLIGHT   -> VIEW_TYPE_FLIGT
            Constants.TYPE_HOTEL    -> VIEW_TYPE_HOTEL
            Constants.TYPE_LOADING  -> VIEW_TYPE_LOADING
            Constants.TYPE_LOADING_HOTEL     -> VIEW_TYPE_LOADING_HOTEL
            Constants.TYPE_HEADER_NOT_COMPLY -> VIEW_TYPE_HEADER_NOT_COMPLY
            Constants.TYPE_HEADER_SOLD_OUT   -> VIEW_TYPE_HEADER_SOLD_OUT
            else -> VIEW_TYPE_BUS
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    inner class TrainHolder internal constructor(itemView: View) : ViewHolder(itemView) {
        val tv_title_train     = itemView.findViewById(R.id.tv_title_train) as TextView
//        val tv_destination     = itemView.findViewById(R.id.tv_destination) as TextView
        val tv_type_class      = itemView.findViewById(R.id.tv_type_class) as TextView
        val tv_number_sheet    = itemView.findViewById(R.id.tv_number_sheet) as TextView
        val tv_price           = itemView.findViewById(R.id.tv_price) as TextView
        val tv_duration        = itemView.findViewById(R.id.tv_duration) as TextView
        val tv_time_departure  = itemView.findViewById(R.id.tv_time_departure) as TextView
        val tv_title_pax       = itemView.findViewById(R.id.tv_title_pax) as TextView
        val imageLineTrain    = itemView.findViewById(R.id.line_image_train) as ImageView

        fun bind(data: ResultListTrainModel, position: Int) {

            var Titiletrain = data.titleTrain
            if(Titiletrain.split(" ").size > 1)
                Titiletrain = Titiletrain.split(" ")[0] + " "+ Titiletrain.split(" ")[1]

            tv_title_train.text     = Titiletrain
            Log.d("xreus","here05 " + Titiletrain)
//            tv_destination.text     = data.nameStation
            tv_type_class.text      = data.className + " (${data.subClass.toUpperCase()})"
            if(data.totalSeat == "0"){
                tv_number_sheet.text    = data.totalSeat+" seat(s)"
            }else{
                tv_number_sheet.text    = data.totalSeat+" seat(s) left"
            }
//            tv_number_sheet.text    = data.totalSeat+" seat(s) left"
            tv_price.text           = Globals.formatAmount(data.price)
            tv_duration.text        = data.duration
            tv_time_departure.text  = data.timeDeparture

            itemView.setOnClickListener {
                if (!data.totalSeat.equals("0")){
                    onclick.onClick(-2,position)
                }
            }

            if(data.notComply&&data.totalSeat.toInt()>0){
                tv_price.setTextColor(context.resources.getColor(R.color.colorRedUndo))
                imageLineTrain.setImageDrawable(context.resources.getDrawable(R.drawable.line_image_train))
                tv_title_train.setTextColor(context.resources.getColor(R.color.colorBlueTitle))
                tv_time_departure.setTextColor(context.resources.getColor(R.color.colorBlueTitle))
            }
            else if (data.totalSeat.equals("0")){
                tv_price.setTextColor(context.resources.getColor(R.color.trainColorSoldOut))
                tv_title_train.setTextColor(context.resources.getColor(R.color.trainColorSoldOut))
                tv_time_departure.setTextColor(context.resources.getColor(R.color.trainColorSoldOut))
                tv_duration.setTextColor(context.resources.getColor(R.color.trainColorSoldOut2))
                tv_title_pax.setTextColor(context.resources.getColor(R.color.trainColorSoldOut2))
                tv_type_class.setTextColor(context.resources.getColor(R.color.trainColorSoldOut2))
                imageLineTrain.setImageDrawable(context.resources.getDrawable(R.drawable.line_image_train_grey))
            }
            else{
                tv_title_train.setTextColor(context.resources.getColor(R.color.colorBlueTitle))
                tv_time_departure.setTextColor(context.resources.getColor(R.color.colorBlueTitle))
                tv_price.setTextColor(context.resources.getColor(R.color.colorBlueTitle))
                imageLineTrain.setImageDrawable(context.resources.getDrawable(R.drawable.line_image_train))
            }


        }
    }

    inner class FlightHolder internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {



        val img_flight_icon    = itemView.findViewById(R.id.img_flight_icon) as ImageView
        val title_airline       = itemView.findViewById(R.id.title_airline) as TextView
        val tv_number           = itemView.findViewById(R.id.tv_number) as TextView
        val tv_duration         = itemView.findViewById(R.id.tv_duration) as TextView
        val tv_type_class       = itemView.findViewById(R.id.tv_type_class) as TextView
        val tv_destination      = itemView.findViewById(R.id.tv_destination) as TextView
        val tv_time_departure   = itemView.findViewById(R.id.tv_time_departure) as TextView
        val tv_price            = itemView.findViewById(R.id.tv_price) as TextView
        val tv_transit          = itemView.findViewById(R.id.tv_transit) as TextView
        val flightComply        = itemView.findViewById(R.id.tv_comply) as TextView


        fun bind(data: ResultListFlightModel, position:Int) {
            Picasso.get()
                    .load(data.imgAirline)
                    .fit()
                    .centerInside()
                    .into(img_flight_icon)

            title_airline.text     = data.titleAirline
            tv_number.text         = data.flightNumber
            tv_duration.text       = data.durationView
            tv_type_class.text     = data.nameClass + " (" + data.code + ")"
            tv_destination.text    = data.origin + " - " + data.destination
            tv_time_departure.text = data.departTime + " - " + data.arriveTime
//            tv_price.text          = Globals.formatAmount(data.price)
            tv_price.text          = StringUtils().setCurrency("", data.price , false)

            var transit = data.totalTransit.toString()
            if(!data.isConnecting){
                transit = "Direct"
                tv_transit.text        = transit
            } else {
                tv_transit.text        = "${transit} Transit"
            }

            if (data.isComply.equals("true")){
                flightComply.visible()
                flightComply.text      = "Not Comply"
            } else {
                flightComply.invisible()
            }


            itemView.setOnClickListener {
                onclick.onClick(-1,position)
            }
        }
    }

    inner class HotelHolder internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(data: AccomodationResultModel, position: Int) {
            Picasso.get()
                    .load(data.listHotelModel.imageHotel)
                    .fit()
                    .centerCrop()
                    .into(itemView.img_hotel)

            itemView.tv_name_hotel.text         = data.listHotelModel.nameHotel
            itemView.tv_type_hotel.text         = data.listHotelModel.typeHotel
            itemView.tv_city.text               = data.listHotelModel.city
            itemView.tv_available.text          = data.listHotelModel.totalAvailable +" Room(s) available"
            itemView.tv_prize.text              = "IDR ${Globals.formatAmount(data.listHotelModel.price)} /room/night"

            setLog(data.listHotelModel.nameHotel)
            setLog(data.listHotelModel.starRating)

            when(data.listHotelModel.starRating){
                "0.0" -> {
                    itemView.star_1.visibility = View.GONE
                    itemView.star_2.visibility = View.GONE
                    itemView.star_3.visibility = View.GONE
                    itemView.star_4.visibility = View.GONE
                    itemView.star_5.visibility = View.GONE
                }
                "1.0" ->{
                    itemView.star_1.visibility = View.VISIBLE
                    itemView.star_2.visibility = View.GONE
                    itemView.star_3.visibility = View.GONE
                    itemView.star_4.visibility = View.GONE
                    itemView.star_5.visibility = View.GONE
                }
                "2.0" ->{
                    itemView.star_1.visibility = View.VISIBLE
                    itemView.star_2.visibility = View.VISIBLE
                    itemView.star_3.visibility = View.GONE
                    itemView.star_4.visibility = View.GONE
                    itemView.star_5.visibility = View.GONE
                }
                "3.0" ->{
                    itemView.star_1.visibility = View.VISIBLE
                    itemView.star_2.visibility = View.VISIBLE
                    itemView.star_3.visibility = View.VISIBLE
                    itemView.star_4.visibility = View.GONE
                    itemView.star_5.visibility = View.GONE
                }
                "4.0" ->{
                    itemView.star_1.visibility = View.VISIBLE
                    itemView.star_2.visibility = View.VISIBLE
                    itemView.star_3.visibility = View.VISIBLE
                    itemView.star_4.visibility = View.VISIBLE
                    itemView.star_5.visibility = View.GONE
                }
                "5.0" ->{
                    itemView.star_1.visibility = View.VISIBLE
                    itemView.star_2.visibility = View.VISIBLE
                    itemView.star_3.visibility = View.VISIBLE
                    itemView.star_4.visibility = View.VISIBLE
                    itemView.star_5.visibility = View.VISIBLE
                }
            }

            itemView.setOnClickListener {
                onclickAnimation.onClick(-3,position,itemView.img_hotel)
            }
        }

    }

    inner class BusHolder internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(data: AccomodationResultModel, position: Int) {

        }

    }

    inner class FLightLoading internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(data: AccomodationResultModel, position: Int) {

        }

    }

    inner class HotelLoading internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(data: AccomodationResultModel, position: Int) {

        }

    }

    inner class NotComplyHolder internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(data: AccomodationResultModel, position: Int) {

        }

    }

    inner class SoldOutHolder internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(data: AccomodationResultModel, position: Int) {

        }

    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    fun setOnclickListenerWithAnimation(onclickListenerRecyclerView: OnclickListenerRecyclerViewAnimation){
        this.onclickAnimation = onclickListenerRecyclerView
    }

    companion object {
        val VIEW_TYPE_TRAIN   = 1
        val VIEW_TYPE_FLIGT   = 2
        val VIEW_TYPE_BUS     = 3
        val VIEW_TYPE_HOTEL   = 4
        val VIEW_TYPE_LOADING = 5
        val VIEW_TYPE_HEADER_NOT_COMPLY = 6
        val VIEW_TYPE_HEADER_SOLD_OUT   = 7
        val VIEW_TYPE_LOADING_HOTEL = 8
    }
}
