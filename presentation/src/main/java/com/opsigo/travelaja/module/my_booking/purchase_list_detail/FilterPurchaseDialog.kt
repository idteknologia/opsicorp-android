package com.opsigo.travelaja.module.my_booking.purchase_list_detail

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.expandview.ExpandableLinearLayout
import com.opsigo.travelaja.module.my_booking.adapter.FilterPurchaceAdapter
import com.opsigo.travelaja.module.my_booking.model.FilterPurchaseModel
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlin.collections.ArrayList


class FilterPurchaseDialog(var context: Context) {

    lateinit var rv_product_type_filter : RecyclerView
    lateinit var rv_payment_method_filter : RecyclerView
    lateinit var scrollView : NestedScrollView
    lateinit var btnShowProduct : ImageView
    lateinit var btnShowPayment : ImageView
    lateinit var btnShowPurchaseDate :ImageView
    lateinit var expandDablePaymentMethod : ExpandableLinearLayout
    lateinit var expandableProductType    : ExpandableLinearLayout
    lateinit var expandablePurchaseDate   : ExpandableLinearLayout
    lateinit var views : View
    val data by lazy { ArrayList<FilterPurchaseModel>() }
    val dataPayment by lazy { ArrayList<FilterPurchaseModel>() }
    val adapterProductType by lazy { FilterPurchaceAdapter(context, data) }
    val adapterPaymentMethod by lazy { FilterPurchaceAdapter(context, dataPayment) }

    var alertDialog: AlertDialog? = null

    fun create(){
        val adb = AlertDialog.Builder(context)
        views = LayoutInflater.from(context).inflate(R.layout.filter_purchase_list,null)
        btnShowProduct = views.findViewById(R.id.btn_show_product)
        btnShowPayment = views.findViewById(R.id.btn_show_payment_methode)
        btnShowPurchaseDate = views.findViewById(R.id.btn_show_purchase_date)
        scrollView     = views.findViewById(R.id.scroll)

        expandableProductType = views.findViewById(R.id.expand_product_type)
        expandablePurchaseDate = views.findViewById(R.id.expand_purchase_date)
        expandDablePaymentMethod =  views.findViewById(R.id.expand_payment_method)

        btnShowProduct.setOnClickListener {
            changeImage(btnShowProduct,0)
        }

        btnShowPayment.setOnClickListener {
            changeImage(btnShowPayment,1)
        }

        btnShowPurchaseDate.setOnClickListener {
            changeImage(btnShowPurchaseDate,2)
        }

        initRecyclerView()
        addData()

        adb.setView(views)
        alertDialog = adb.create()
        alertDialog?.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogAnimations_SmileWindow //R.style.DialogAnimations_SmileWindow;
        alertDialog?.setCancelable(true)
        alertDialog?.getWindow()?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        alertDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.show()
        alertDialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    }

    private fun changeImage(image: ImageView?, i: Int) {

        if (image?.resources?.getDrawable(R.drawable.ic_chevron_up_orange)?.constantState==context.resources.getDrawable(R.drawable.ic_chevron_up_orange)){
            image?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_chevron_down))
        }
        else{
            image?.setImageDrawable(context.resources.getDrawable(R.drawable.ic_chevron_up_orange))
        }

        when (i){
            0 -> {
                checkExpandable(expandableProductType)
            }
            1 -> {
                checkExpandable(expandDablePaymentMethod)
            }
            2 -> {
                checkExpandable(expandablePurchaseDate)
            }
        }
    }

    private fun checkExpandable(expandable: ExpandableLinearLayout) {
        if (expandable.isExpanded){
            expandable.collapse()
        }
        else {
            expandable.expand()
            scrollDown()
        }
    }

    private fun scrollDown() {
        Globals.delay(500,object :Globals.DelayCallback{
            override fun done() {
                scrollView.fullScroll(View.FOCUS_DOWN)
            }
        })
    }


    private fun initRecyclerView() {
        rv_product_type_filter = views.findViewById(R.id.rv_product_type_filter)
        rv_payment_method_filter = views.findViewById(R.id.rv_payment_method_filter)

        adapterProductType.positionCheckBox = "left"
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_product_type_filter.layoutManager = layoutManager
        rv_product_type_filter.itemAnimator = DefaultItemAnimator()
        rv_product_type_filter.adapter = adapterProductType

        adapterProductType.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {

            }
        })


        adapterPaymentMethod.positionCheckBox = "right"
        val layoutManagerPayment = LinearLayoutManager(context)
        layoutManagerPayment.orientation = LinearLayoutManager.VERTICAL
        rv_payment_method_filter.layoutManager = layoutManagerPayment
        rv_payment_method_filter.itemAnimator = DefaultItemAnimator()
        rv_payment_method_filter.adapter = adapterPaymentMethod


        adapterPaymentMethod.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {

            }
        })
    }

    fun addData() {
        data.clear()
        dataPayment.clear()

        val listProduct = ArrayList<String>()
        val listPayment = ArrayList<String>()
        listProduct.clear()
        listPayment.clear()


        listProduct.add("Flight")
        listProduct.add("Hotel")
        listProduct.add("Train")
        listProduct.add("Tour & travel")

        listPayment.add("Credit Card")
        listPayment.add("Transfer")
        listPayment.add("Atm")
        listPayment.add("Mandiri Clickpay")

        listPayment.forEachIndexed { index, s ->
            val mData = FilterPurchaseModel()
            mData.name = s
            dataPayment.add(mData)
        }

        listProduct.forEachIndexed { index, s ->
            val mData = FilterPurchaseModel()
            mData.name = s
            data.add(mData)
        }

        adapterProductType.setData(data)
        adapterPaymentMethod.setData(dataPayment)
    }



}