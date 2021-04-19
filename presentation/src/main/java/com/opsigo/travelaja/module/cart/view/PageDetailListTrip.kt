package com.opsigo.travelaja.module.cart.view

import android.view.View
import com.opsigo.travelaja.R
import android.content.Intent
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.Constants
import opsigo.com.datalayer.mapper.Serializer
import com.opsigo.travelaja.utility.Globals.setLog
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.opsigo.travelaja.module.cart.model.CartModel
import com.opsigo.travelaja.module.home.activity.HomeActivity
import com.opsigo.travelaja.module.cart.adapter.CartAdapterNew
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.opsigo.travelaja.utility.Constants.ONCLICK_DETAIL_HOTEL
import com.opsigo.travelaja.utility.Constants.ONCLICK_DETAIL_TRAIN
import com.opsigo.travelaja.utility.Constants.PROGRESS_TRAIN_SAVED
import com.opsigo.travelaja.utility.Constants.ONCLICK_DETAIL_FLIGHT
import com.opsigo.travelaja.utility.Constants.PROGRESS_FLIGHT_SAVED
import com.opsigo.travelaja.utility.Constants.PROGRESS_HOTEL_CALLBACK
import com.opsigo.travelaja.utility.Constants.PROGRESS_TRAIN_CALLBACK
import kotlinx.android.synthetic.main.page_cart_list_detail_trip.view.*
import com.opsigo.travelaja.utility.Constants.PROGRESS_FLIGHT_CALLBACK
import com.opsigo.travelaja.utility.Constants.ONCLICK_CHANGE_SEAT_MAP_TRAIN
import com.opsigo.travelaja.utility.Constants.ONCLIK_OPTION_REMOVE_TRAIN_CART

class PageDetailListTrip : LinearLayout, View.OnClickListener,OnclickListenerRecyclerView{

    val data = ArrayList<CartModel>()
    val adapter by lazy { CartAdapterNew(context) }
    lateinit var callback : Callback
    lateinit var onclick: OnclickListenerRecyclerView


    fun callbackOnclickButton(onclickButtonListener: Callback){
        callback = onclickButtonListener
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

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
        View.inflate(context, R.layout.page_cart_list_detail_trip, this)
        initRecyclerView()
        line_btn_add_more_item.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_list_cart_item.layoutManager = layoutManager
        rv_list_cart_item.itemAnimator = DefaultItemAnimator()
        rv_list_cart_item.adapter = adapter
        adapter.setOnclickListener(this)
    }

    fun setData(mData:ArrayList<CartModel>){
        data.clear()
        data.addAll(mData)
        adapter.setData(data)
    }

    fun setTripCode(tripCode:String){
        tv_trip_code.text = tripCode
    }

    fun setPurpose(purpose:String){
        tv_purpose.text = purpose
    }

    fun setTimeLimit(timeLimit:String){
        tv_time_limit.text = "Expired in : " + timeLimit
    }

    interface Callback{
        fun updateViewReserved()
        fun updateViewSaved()
        fun onClicked(views: Int)
    }

    override fun onClick(v: View?) {
        when(v){
            line_btn_add_more_item -> {
                val intent = Intent(context, HomeActivity::class.java)
                intent.putExtra(Constants.FROM_CART,Constants.FROM_CART)
                intent.putExtra(Constants.TYPE_ACCOMODATION,Constants.KEY_ACCOMODATION)
                Globals.gotoActivityModule(context,intent)
            }
        }
    }

    override fun onClick(views: Int, position: Int) {
        when(views){
            PROGRESS_FLIGHT_CALLBACK -> {
                callback.updateViewReserved()
            }
            PROGRESS_FLIGHT_SAVED -> {
                callback.updateViewSaved()
            }
            PROGRESS_TRAIN_CALLBACK -> {
                callback.updateViewReserved()
            }
            PROGRESS_TRAIN_SAVED ->{
                callback.updateViewSaved()
            }
            ONCLICK_CHANGE_SEAT_MAP_TRAIN -> {
                val intent = Intent(context, Class.forName(Constants.BASE_PACKAGE_TRAIN+"seat.SeatActivityTrain"))
                intent.putExtra("data", Serializer.serialize(data[position]))
                if (!data[position].dataCardTrain.status.toLowerCase().contains("expired")&&!data[position].dataCardTrain.status.toLowerCase().contains("saved")){
//                    (context as Activity).startActivityForResult(intent,Constants.GET_SEAT_MAP)
                    Globals.gotoActivityForResultModule(context,intent,Constants.GET_SEAT_MAP)
                }
            }
            PROGRESS_HOTEL_CALLBACK -> {
                callback.updateViewReserved()
            }
            ONCLIK_OPTION_REMOVE_TRAIN_CART ->{

            }
            ONCLICK_DETAIL_TRAIN -> {
                val intent = Intent(context,Class.forName(Constants.BASE_PACKAGE_TRAIN+"detail.CartTrainDetailActivity"))
                intent.putExtra(Constants.FROM_CART,Constants.FROM_CART)
                intent.putExtra(Constants.DATA_DETAIL_TRAIN,Serializer.serialize(data[position]))
                Globals.gotoActivityModule(context,intent)
            }
            ONCLICK_DETAIL_HOTEL -> {
                val intent = Intent(context,Class.forName(Constants.BASE_PACKAGE_HOTEL+"detail_hotel.DetailSummaryHotelActivity"))
                intent.putExtra(Constants.FROM_CART,Constants.FROM_CART)
                intent.putExtra(Constants.DATA_DETAIL_HOTEL,Serializer.serialize(data[position]))
                Globals.gotoActivityModule(context,intent)
            }
            ONCLICK_DETAIL_FLIGHT -> {
                val intent = Intent(context,Class.forName(Constants.BASE_PACKAGE_FLIGHT+"detail_cart.DetailCartFlightActivity"))
                intent.putExtra(Constants.FROM_CART,Constants.FROM_CART)
                intent.putExtra(Constants.DATA_DETAIL_FLIGHT,Serializer.serialize(data[position]))
                setLog("Test Detail", Serializer.serialize(data))
                Globals.gotoActivityModule(context,intent)
            }
        }
    }

}