package com.mobile.travelaja.module.my_booking.purchase_list_detail

import android.view.View
import android.view.Gravity
import android.graphics.Color
import android.view.ViewGroup
import com.mobile.travelaja.R
import android.content.Context
import android.app.AlertDialog
import android.widget.TextView
import android.widget.ImageView
import android.view.LayoutInflater
import kotlin.collections.ArrayList
import com.mobile.travelaja.utility.Globals
import androidx.core.widget.NestedScrollView
import android.graphics.drawable.ColorDrawable
import android.widget.RelativeLayout
import com.mobile.travelaja.utility.DateConverter
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import com.mobile.travelaja.module.my_booking.model.FilterPurchaseModel
import com.mobile.travelaja.module.my_booking.adapter.FilterPurchaceAdapter
import com.mobile.travelaja.module.item_custom.expandview.ExpandableLinearLayout


class FilterPurchaseDialog(var context: Context) {

    lateinit var rv_product_type_filter : androidx.recyclerview.widget.RecyclerView
    lateinit var rv_payment_method_filter : androidx.recyclerview.widget.RecyclerView
    lateinit var scrollView : NestedScrollView
    lateinit var btnShowProduct : ImageView
    lateinit var btnShowPayment : ImageView
    lateinit var btnShowPurchaseDate :ImageView
    lateinit var btnRiset : RelativeLayout
    lateinit var btnNext  : RelativeLayout
    lateinit var tvDate : TextView
    lateinit var expandDablePaymentMethod : ExpandableLinearLayout
    lateinit var expandableProductType    : ExpandableLinearLayout
    lateinit var expandablePurchaseDate   : ExpandableLinearLayout
    lateinit var callback : CallbackFilterPurchase
    lateinit var views : View
    val data by lazy { ArrayList<FilterPurchaseModel>() }
    val dataPayment by lazy { ArrayList<FilterPurchaseModel>() }
    val adapterProductType by lazy { FilterPurchaceAdapter(context, data) }
    val adapterPaymentMethod by lazy { FilterPurchaceAdapter(context, dataPayment) }

    var alertDialog: AlertDialog? = null

    fun create(dateFrom: String, dateTo: String, dataFilterType: ArrayList<FilterPurchaseModel>,callbackFilterPurchase: CallbackFilterPurchase){
        val adb = AlertDialog.Builder(context)
        callback = callbackFilterPurchase
        views = LayoutInflater.from(context).inflate(R.layout.filter_purchase_list,null)
        btnShowProduct = views.findViewById(R.id.btn_show_product)
        btnShowPayment = views.findViewById(R.id.btn_show_payment_methode)
        btnShowPurchaseDate = views.findViewById(R.id.btn_show_purchase_date)
        scrollView     = views.findViewById(R.id.scroll)
        tvDate        = views.findViewById(R.id.tv_date)
        btnNext       = views.findViewById(R.id.btn_next)
        btnRiset      = views.findViewById(R.id.btn_riset)

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

        tvDate.text = "${DateConverter().getDate(dateFrom,"yyyy-MM-dd","dd MMM yyyy")} - ${DateConverter().getDate(dateTo,"yyyy-MM-dd","dd MMM yyyy")}"
        initRecyclerView()
        addData(dataFilterType)

        btnRiset.setOnClickListener {

        }
        btnNext.setOnClickListener {
            callback.filter(dateTo,dateFrom,data)
            alertDialog?.dismiss()
        }

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
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_product_type_filter.layoutManager = layoutManager
        rv_product_type_filter.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_product_type_filter.adapter = adapterProductType

        adapterProductType.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {

            }
        })


        adapterPaymentMethod.positionCheckBox = "right"
        val layoutManagerPayment = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManagerPayment.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_payment_method_filter.layoutManager = layoutManagerPayment
        rv_payment_method_filter.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_payment_method_filter.adapter = adapterPaymentMethod


        adapterPaymentMethod.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {

            }
        })
    }

    fun addData(dataFilterType: ArrayList<FilterPurchaseModel>) {
        data.clear()
        dataPayment.clear()

        val listPayment = ArrayList<String>()
        listPayment.clear()

        listPayment.add(context.getString(R.string.credit_card))
        listPayment.add(context.getString(R.string.transfer))
        listPayment.add(context.getString(R.string.atm))
        listPayment.add(context.getString(R.string.mandiri__clickpay))

        listPayment.forEachIndexed { index, s ->
            val mData = FilterPurchaseModel()
            mData.name = s
            dataPayment.add(mData)
        }

        data.addAll(dataFilterType)
        adapterProductType.setData(data)
        adapterPaymentMethod.setData(dataPayment)
    }

    interface CallbackFilterPurchase{
        fun filter(dateTo:String,dateFrom: String,itemType:ArrayList<FilterPurchaseModel>)
    }

}