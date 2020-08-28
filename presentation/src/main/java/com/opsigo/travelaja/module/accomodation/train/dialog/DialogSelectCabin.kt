package com.opsigo.travelaja.module.accomodation.train.dialog

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
import android.widget.RelativeLayout
import android.widget.TextView
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.accomodation.train.adapter.TrainShorByAdapter
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView

class DialogSelectCabin(var context: Context) {

    lateinit var views : View
    var positionSelected = 0
    var alertDialog: AlertDialog? = null
    val data = ArrayList<String>()
    var currentSort = 0
    lateinit var recyclerView : RecyclerView
    lateinit var apply : TextView
    lateinit var line_apply : RelativeLayout
    lateinit var close : TextView
    lateinit var line_close :RelativeLayout
    val adapter by lazy { TrainShorByAdapter(context,data) }
    lateinit var callbackDialog : CallbackDialog

    fun create(currentSort: Int,data:ArrayList<String>,callbackDialog: CallbackDialog){
        val adb = AlertDialog.Builder(context)
        this.currentSort = currentSort
        this.callbackDialog = callbackDialog
        views = LayoutInflater.from(context).inflate(R.layout.dialog_select_cabin,null)
        recyclerView = views.findViewById(R.id.rv_cabin_list)
        apply        = views.findViewById(R.id.apply)
        line_apply   = views.findViewById(R.id.line_apply)
        close        = views.findViewById(R.id.close)
        line_close   = views.findViewById(R.id.line_close)

        initRecyclerView(data)

        apply.setOnClickListener {
            callbackDialog.selected(positionSelected)
            dismisListener()
        }
        line_apply.setOnClickListener {
            callbackDialog.selected(positionSelected)
            dismisListener()
        }
        close.setOnClickListener {
            dismisListener()
        }
        line_close.setOnClickListener {
            dismisListener()
        }

        adb.setView(views)
        alertDialog = adb.create()
        alertDialog?.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogAnimations_SmileWindow //R.style.DialogAnimations_SmileWindow;
        alertDialog?.setCancelable(false)
        alertDialog?.getWindow()?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        alertDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.show()
        alertDialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun dismisListener() {
        alertDialog?.dismiss()
    }

    private fun initRecyclerView(data: ArrayList<String>) {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter

        adapter.checkIn = currentSort
        adapter.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                adapter.notifyDataSetChanged()
                positionSelected = position
            }
        })

        adapter.setData(data)
    }

    interface CallbackDialog{
        fun selected(int: Int)
    }

}