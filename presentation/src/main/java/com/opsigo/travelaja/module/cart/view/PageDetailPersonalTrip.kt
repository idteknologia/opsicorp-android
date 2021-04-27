package com.opsigo.travelaja.module.cart.view

import android.view.View
import com.opsigo.travelaja.R
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.widget.LinearLayout
import com.opsigo.travelaja.module.cart.model.CartModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import com.opsigo.travelaja.module.cart.adapter.CartAdapterNew
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.empty_cart_view.view.*
import kotlinx.android.synthetic.main.page_detail_cart_bisnis_trip.view.*
import opsigo.com.datalayer.mapper.Serializer

class PageDetailPersonalTrip : LinearLayout,OnclickListenerRecyclerView{

    lateinit var callback : Callback

    fun callbackOnclickButton(onclickButtonListener: Callback){
        callback = onclickButtonListener
    }

    val data = ArrayList<CartModel>()
    val adapter by lazy { CartAdapterNew(context) }

    constructor(context: Context) : super(context) {
        init()
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : super(context, attrs, defStyle) {

        val a = context.obtainStyledAttributes(attrs, R.styleable.ButtonDefaultOpsicorp, defStyle, 0)
        a.recycle()

        init()
    }

    private fun init() {
        setOrientation(VERTICAL)
        View.inflate(context, R.layout.page_detail_cart_bisnis_trip, this)

        initRecyclerView()
        initCheckEmpty()
    }

    private fun initCheckEmpty() {
        if (data.isEmpty()){
            line_empty.visibility = View.VISIBLE
            line_empty_item.visibility = VISIBLE
        }
        else {
            line_empty.visibility = View.GONE
            line_empty_item.visibility = GONE
        }
    }

    override fun onClick(views: Int, position: Int) {
        when(views){
            Constants.PROGRESS_FLIGHT_CALLBACK -> {
                callback.updateViewReservedPersonalTrip()
            }
            Constants.PROGRESS_FLIGHT_SAVED -> {
                callback.updateViewSavedPersonalTrip()
            }
            Constants.PROGRESS_TRAIN_CALLBACK -> {
                callback.updateViewReservedPersonalTrip()
            }
            Constants.PROGRESS_TRAIN_SAVED ->{
                callback.updateViewSavedPersonalTrip()
            }
            Constants.ONCLICK_CHANGE_SEAT_MAP_TRAIN -> {
                val intent = Intent(context, Class.forName(Constants.BASE_PACKAGE_TRAIN+"seat.SeatActivityTrain"))
                intent.putExtra("data", Serializer.serialize(data[position]))
                if (!data[position].dataCardTrain.status.toLowerCase().contains("expired")&&!data[position].dataCardTrain.status.toLowerCase().contains("saved")){
//                    (context as Activity).startActivityForResult(intent,Constants.GET_SEAT_MAP)
                    Globals.gotoActivityForResultModule(context,intent, Constants.GET_SEAT_MAP)
                }
            }
            Constants.PROGRESS_HOTEL_CALLBACK -> {
                callback.updateViewReservedPersonalTrip()
            }
            Constants.ONCLIK_OPTION_REMOVE_TRAIN_CART ->{

            }
            Constants.ONCLICK_DETAIL_TRAIN -> {
                val intent = Intent(context,Class.forName(Constants.BASE_PACKAGE_TRAIN+"detail.CartTrainDetailActivity"))
                intent.putExtra(Constants.FROM_CART, Constants.FROM_CART)
                intent.putExtra(Constants.DATA_DETAIL_TRAIN, Serializer.serialize(data[position]))
                Globals.gotoActivityModule(context,intent)
            }
            Constants.ONCLICK_DETAIL_HOTEL -> {
                val intent = Intent(context,Class.forName(Constants.BASE_PACKAGE_HOTEL+"detail_hotel.DetailSummaryHotelActivity"))
                intent.putExtra(Constants.FROM_CART, Constants.FROM_CART)
                intent.putExtra(Constants.DATA_DETAIL_HOTEL, Serializer.serialize(data[position]))
                Globals.gotoActivityModule(context,intent)
            }
            Constants.ONCLICK_DETAIL_FLIGHT -> {
                val intent = Intent(context,Class.forName(Constants.BASE_PACKAGE_FLIGHT+"detail_cart.DetailCartFlightActivity"))
                intent.putExtra(Constants.FROM_CART, Constants.FROM_CART)
                intent.putExtra(Constants.DATA_DETAIL_FLIGHT, Serializer.serialize(data[position]))
                Globals.setLog("Test Detail", Serializer.serialize(data))
                Globals.gotoActivityModule(context,intent)
            }
        }
    }

    interface Callback {
        fun updateViewReservedPersonalTrip()
        fun updateViewSavedPersonalTrip()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        list_item_personal.layoutManager = layoutManager
        list_item_personal.itemAnimator = DefaultItemAnimator()
        list_item_personal.adapter = adapter
        adapter.setOnclickListener(this)
    }

    fun setData(mData:ArrayList<CartModel>){
        data.clear()
        data.addAll(mData)
        adapter.setData(data)
        initCheckEmpty()
    }

}