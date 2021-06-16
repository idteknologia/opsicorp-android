package com.mobile.travelaja.module.item_custom.toolbar_view

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.mobile.travelaja.R
import kotlinx.android.synthetic.main.toolbar_view.view.*


class ToolbarOpsicorp : LinearLayout, View.OnClickListener {

    lateinit var onclick : OnclickButtonListener

    var START = 0
    var CENTER = 1
    var END   = 2

    var mHidenCart = false
    var mHidenTitle = false
    var mHidenBack  = false

    companion object{
        val DEFAULT_HIDEN_CART  = false
        val DEFAULT_HIDEN_TITEL = false
        val DEFAULT_HIDEN_BACK  = false
    }

    fun callbackOnclickToolbar(onclickButtonListener: OnclickButtonListener){
        onclick = onclickButtonListener
    }

    constructor(context: Context) : super(context) {
        init()
    }


    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : super(context, attrs, defStyle) {

        val a = context.obtainStyledAttributes(attrs, R.styleable.ToolbarOpsicorp, defStyle, 0)

        try {
            mHidenCart  = a.getBoolean(R.styleable.ToolbarOpsicorp_toolbar_hidden_cart, DEFAULT_HIDEN_CART)
            mHidenTitle = a.getBoolean(R.styleable.ToolbarOpsicorp_toolbar_hidden_title, DEFAULT_HIDEN_TITEL)
            mHidenBack  = a.getBoolean(R.styleable.ToolbarOpsicorp_toolbar_hidden_back, DEFAULT_HIDEN_BACK)
        }catch (e:Exception){
            mHidenCart  = DEFAULT_HIDEN_CART
            mHidenTitle = DEFAULT_HIDEN_TITEL
            mHidenBack  = DEFAULT_HIDEN_BACK
        }

        a.recycle()

        init()
    }

    fun showTitleRoundtripLeft(departure: String,arrival: String){
        logo_center.visibility = View.GONE
        line_title_round_trip_left.visibility = View.VISIBLE
        tv_title_round_trip_left_departure.text = departure
        tv_title_round_trip_left_arrival.text = arrival
    }

    private fun init() {
        setOrientation(LinearLayout.VERTICAL);
        View.inflate(context, R.layout.toolbar_view, this)

        if (mHidenBack){
            hidenBtnBack()
        }

        if (mHidenCart){
            hidenBtnCart()
        }

        if (mHidenTitle){
            hidenLogoCenter()
        }

        btn_back.setOnClickListener(this)
        line_back.setOnClickListener(this)
        btn_card.setOnClickListener(this)
        logo_center.setOnClickListener(this)
        btn_reset.setOnClickListener(this)
        line_btn_add_more_item.setOnClickListener(this)
    }

    fun showtitlePurchase(){
        line_text_double.visibility = View.GONE
        logo_center.visibility = View.GONE
        line_title_purchase.visibility = View.VISIBLE
    }

    fun setTitlePurchase(departureAirport: String
                         ,titleDepartureAirport: String,
                         arrivalAirport: String,
                         titleArrivalAirport: String){
        tv_departure_airport.text = departureAirport
        tv_title_departure_airport.text = titleDepartureAirport
        tv_arrival_airport.text = arrivalAirport
        tv_title_arrival_airport.text = titleArrivalAirport
    }


    interface OnclickButtonListener{
        fun btnBack()
        fun logoCenter()
        fun btnCard()
    }

    fun showAddMoreItem(){
        line_btn_add_more_item.visibility = View.VISIBLE
        btn_card.visibility = View.GONE
    }

    fun hideAddMoreItem(){
        line_btn_add_more_item.visibility = View.GONE
    }


    override fun onClick(v: View?) {

        if(v==btn_back){
            onclick.btnBack()
        }
        else if(v==logo_center){
            onclick.logoCenter()
        }
        else if (v==btn_card){
            onclick.btnCard()
        }
        else if(v==btn_reset){
            onclick.btnCard()
        }
        else if(v==line_back){
            onclick.btnBack()
        }
        else if (v==line_btn_filter){
            onclick.btnCard()
        }
        else if (v==line_btn_add_more_item){
            onclick.btnCard()
        }
    }

    fun hidenBtnBack(){
        btn_back.visibility = View.GONE
    }

    fun showBtnFilter(){
        line_btn_filter.visibility = View.VISIBLE
        hidenBtnCart()
    }

    fun showBtnReset(){
        btn_card.visibility  = View.GONE
        btn_reset.visibility = View.VISIBLE
    }

    fun hidenBtnCart(){
        btn_card.visibility = View.GONE
    }
    fun hidenLogoCenter(){
        logo_center.visibility = View.GONE
    }

    fun changeImageBtnBack(drawable :Int){
        btn_back.setImageDrawable(resources.getDrawable(drawable))

    }

    fun changeImageCard(drawable :Int){
        btn_card.setImageDrawable(resources.getDrawable(drawable))
    }

    fun setToolbarColorView(color: Int){
        line_toolbar.setBackgroundColor(resources.getColor(color))
    }

    fun setTitleBar(string: String){
        logo_center.text = string
    }

    fun setTitleBarColor(color: Int){
        logo_center.setTextColor(resources.getColor(color))
    }

    fun setDoubleTitle(title1: String,title2: String){
        logo_center.visibility      = View.INVISIBLE
        line_text_double.visibility = View.VISIBLE
        title_double1.text          = title1
        title_double2.text          = title2
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun doubleTitleGravity(position:Int){
        when(position){
            START -> {
                title_double1.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                title_double2.textAlignment  = View.TEXT_ALIGNMENT_TEXT_START
            }
            CENTER -> {
                title_double1.textAlignment = View.TEXT_ALIGNMENT_CENTER
                title_double2.textAlignment  = View.TEXT_ALIGNMENT_CENTER
            }
            END -> {
                title_double1.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                title_double2.textAlignment  = View.TEXT_ALIGNMENT_TEXT_END
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun singgleTitleGravity(position:Int){
        when(position){
            START -> {
                logo_center.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
            CENTER -> {
                logo_center.textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
            END -> {
                logo_center.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
            }
        }

    }

    fun getImageCart():ImageView{
        return btn_card
    }

    fun getImageBack():ImageView{
        return btn_back
    }

}