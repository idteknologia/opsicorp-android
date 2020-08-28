package com.opsigo.travelaja.module.accomodation.flight.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.accomodation.train.adapter.TrainShorByAdapter
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView

class FlightShortByDialog(var context: Context) {

    lateinit var views : View
    var alertDialog: AlertDialog? = null
    val data = ArrayList<String>()
    var currentSort = 0
    lateinit var recyclerView : RecyclerView
    val adapterShortBy by lazy { TrainShorByAdapter(context,data) }
    lateinit var callbackDialog : CallbackDialog

    fun create(currentSort: Int,callbackDialog: CallbackDialog){
        val adb = AlertDialog.Builder(context)
        this.currentSort = currentSort
        this.callbackDialog = callbackDialog
        views = LayoutInflater.from(context).inflate(R.layout.train_short_by_dialog,null)
        recyclerView = views.findViewById(R.id.recycler_shor_by)

        initRecyclerView()

        adb.setView(views)
        alertDialog = adb.create()
        alertDialog?.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogAnimations_SmileWindow //R.style.DialogAnimations_SmileWindow;
        alertDialog?.setCancelable(false)
        alertDialog?.getWindow()?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        alertDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.show()
        alertDialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapterShortBy

        adapterShortBy.checkIn = currentSort
        adapterShortBy.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                adapterShortBy.notifyDataSetChanged()
                callbackDialog.selected(position)
                alertDialog?.hide()
            }
        })
        addData()
    }

    interface CallbackDialog{
        fun selected(int: Int)
    }

    fun addData() {
        data.add("Lowest Price")
        data.add("Earliest Departure")
        data.add("Latest Departure")
        data.add("Shortest Durations")

        adapterShortBy.setData(data)
    }

}