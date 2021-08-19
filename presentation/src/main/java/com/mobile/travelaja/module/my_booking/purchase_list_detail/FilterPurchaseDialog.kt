package com.mobile.travelaja.module.my_booking.purchase_list_detail

import android.view.View
import android.app.Activity
import android.view.Gravity
import android.graphics.Color
import android.view.ViewGroup
import com.mobile.travelaja.R
import android.content.Context
import android.app.AlertDialog
import android.widget.ImageView
import android.view.LayoutInflater
import kotlin.collections.ArrayList
import com.mobile.travelaja.utility.Globals
import android.graphics.drawable.ColorDrawable
import com.mobile.travelaja.databinding.FilterPurchaseListBinding
import com.mobile.travelaja.utility.DateConverter
import com.opsicorp.sliderdatepicker.utils.Constant
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import com.mobile.travelaja.module.my_booking.model.FilterPurchaseModel
import com.mobile.travelaja.module.my_booking.adapter.FilterPurchaceAdapter
import com.mobile.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp
import com.mobile.travelaja.module.item_custom.expandview.ExpandableLinearLayout


class FilterPurchaseDialog(var context: Context) {

    var dateTo   = ""
    var dateFrom = ""
    lateinit var bind :FilterPurchaseListBinding
    lateinit var callback : CallbackFilterPurchase
    val data by lazy { ArrayList<FilterPurchaseModel>() }
    val dataPayment by lazy { ArrayList<FilterPurchaseModel>() }
    val adapterProductType by lazy { FilterPurchaceAdapter(context, data) }
    val adapterPaymentMethod by lazy { FilterPurchaceAdapter(context, dataPayment) }

    var alertDialog: AlertDialog? = null

    fun create(dateFrom: String, dateTo: String, dataFilterType: ArrayList<FilterPurchaseModel>,callbackFilterPurchase: CallbackFilterPurchase) {
        val adb = AlertDialog.Builder(context)
        callback = callbackFilterPurchase
        bind = FilterPurchaseListBinding.inflate(LayoutInflater.from(context))

        bind.btnShowProduct .setOnClickListener {
            changeImage(bind.btnShowProduct,0)
        }

        bind.btnShowPaymentMethode.setOnClickListener {
            changeImage(bind.btnShowPaymentMethode,1)
        }

        bind.btnShowPurchaseDate.setOnClickListener {
            changeImage(bind.btnShowPurchaseDate,2)
        }

        bind.tvDateTo.setOnClickListener {
            NewCalendarViewOpsicorp().showCalendarView(context as Activity, Constant.DOUBLE_SELECTED)
        }
        bind.tvDateFrom.setOnClickListener {
            NewCalendarViewOpsicorp().showCalendarView(context as Activity,Constant.DOUBLE_SELECTED)
        }
        this.dateFrom = dateFrom
        this.dateTo   = dateTo
        bind.tvDateFrom.text = DateConverter().getDate(dateFrom,"yyyy-MM-dd","dd MMM yyyy")
        bind.tvDateTo.text = DateConverter().getDate(dateTo,"yyyy-MM-dd","dd MMM yyyy")
        bind.tvDate.text = "${DateConverter().getDate(dateFrom,"yyyy-MM-dd","dd MMM yyyy")} - ${DateConverter().getDate(dateTo,"yyyy-MM-dd","dd MMM yyyy")}"
        initRecyclerView()
        addData(dataFilterType)

        bind.btnRiset.setOnClickListener {

        }
        bind.btnNext.setOnClickListener {
            callback.filter(this.dateTo,this.dateFrom,data)
            alertDialog?.dismiss()
        }
        bind.line90Days.setOnClickListener {
            onCheck90Days()
        }
        bind.lineOtherDays.setOnClickListener {
            onCheckOtherDays()
        }

        adb.setView(bind.root)
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
                checkExpandable(bind.expandProductType)
            }
            1 -> {
                checkExpandable(bind.expandPaymentMethod)
            }
            2 -> {
                checkExpandable(bind.expandPurchaseDate)
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
                bind.scroll.fullScroll(View.FOCUS_DOWN)
            }
        })
    }

    private fun initRecyclerView() {

        adapterProductType.positionCheckBox = "left"
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        bind.rvProductTypeFilter.layoutManager = layoutManager
        bind.rvProductTypeFilter.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        bind.rvProductTypeFilter.adapter = adapterProductType

        adapterProductType.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {

            }
        })


        adapterPaymentMethod.positionCheckBox = "right"
        val layoutManagerPayment = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManagerPayment.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        bind.rvPaymentMethodFilter.layoutManager = layoutManagerPayment
        bind.rvPaymentMethodFilter.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        bind.rvPaymentMethodFilter.adapter = adapterPaymentMethod


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

    fun onCheck90Days(){
        bind.imgCheck90Days.visibility = View.VISIBLE
        bind.imgCheckOtherDays.visibility = View.INVISIBLE
    }

    fun onCheckOtherDays(){
        bind.imgCheck90Days.visibility = View.INVISIBLE
        bind.imgCheckOtherDays.visibility = View.VISIBLE
    }

}