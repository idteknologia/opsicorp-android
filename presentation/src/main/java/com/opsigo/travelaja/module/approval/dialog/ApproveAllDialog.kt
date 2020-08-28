package com.opsigo.travelaja.module.approval.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R


class ApproveAllDialog(var context: Context) {

    lateinit var views : View
    var alertDialog: AlertDialog? = null

    fun create(){
        val adb = AlertDialog.Builder(context)
        views = LayoutInflater.from(context).inflate(R.layout.approve_all_dialog,null)
//        btnShowProduct = views.findViewById(R.id.btn_show_product)

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

    private fun initRecyclerView() {
//        rv_product_type_filter = views.findViewById(R.id.rv_product_type_filter)
//
//        val layoutManager = LinearLayoutManager(context)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        rv_product_type_filter.layoutManager = layoutManager
//        rv_product_type_filter.itemAnimator = DefaultItemAnimator()
//        rv_product_type_filter.adapter = adapterProductType
//
//        adapterProductType.setOnclickListener(object : OnclickListenerRecyclerView {
//            override fun onClick(views: Int, position: Int) {
//
//            }
//        })
    }

    fun addData() {

    }



}