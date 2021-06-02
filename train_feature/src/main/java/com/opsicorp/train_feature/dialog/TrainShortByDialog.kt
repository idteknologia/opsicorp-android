package com.opsicorp.train_feature.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.opsicorp.train_feature.R
import com.opsicorp.train_feature.adapter.TrainShorByAdapter
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView

class TrainShortByDialog(var context: Context) {

    lateinit var views : View
    var alertDialog: AlertDialog? = null
    val data = ArrayList<String>()
    var currentSort = 0
    lateinit var recyclerView : RecyclerView
    lateinit var background: RelativeLayout
    val adapterShortBy by lazy { TrainShorByAdapter(context,data) }
    lateinit var callbackDialog : CallbackDialog

    fun create(currentSort: Int,callbackDialog: CallbackDialog){
        val adb = AlertDialog.Builder(context)
        this.currentSort = currentSort
        this.callbackDialog = callbackDialog
        views = LayoutInflater.from(context).inflate(R.layout.train_short_by_dialog,null)
        recyclerView = views.findViewById(R.id.recycler_shor_by)
        background = views.findViewById(R.id.line_background)

        initRecyclerView()

        adb.setView(views)
        alertDialog = adb.create()
        alertDialog?.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogAnimations_SmileWindow //R.style.DialogAnimations_SmileWindow;
        alertDialog?.getWindow()?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        alertDialog?.setCancelable(true)
        alertDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.show()
        alertDialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    }

    private fun initRecyclerView() {
        background.setOnClickListener { alertDialog?.hide() }

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
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
        data.add(context.getString(R.string.lowest_price))
        data.add(context.getString(R.string.earliest_departure))
        data.add(context.getString(R.string.latest_departure))
        data.add(context.getString(R.string.shortest_duration))

        adapterShortBy.setData(data)
    }

}