package com.opsigo.travelaja.module.approval.dialog

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
import com.opsigo.travelaja.module.approval.adapter.ListParticipantAdapter
import com.opsigo.travelaja.module.approval.summary.ParticipantModel
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView

class ListParticipantDialog(var context: Context) {

    lateinit var views : View
    var alertDialog: AlertDialog? = null
    val data = ArrayList<ParticipantModel>()
    lateinit var recyclerView : RecyclerView
    lateinit var tv_cancel : TextView
    lateinit var tv_total_participant :TextView
    lateinit var btn_add_trip_item : TextView
    lateinit var line_approve_all : RelativeLayout

    val adapterDialog by lazy { ListParticipantAdapter(context,data) }
    lateinit var callbackDialog : CallbackDialog
    var idParticipantAvailableApprove = ArrayList<String>()

    fun create(callbackDialog: CallbackDialog,mData:ArrayList<ParticipantModel>){
        val adb = AlertDialog.Builder(context)
        data.clear()
        data.addAll(mData)
        this.callbackDialog = callbackDialog
        views = LayoutInflater.from(context).inflate(R.layout.list_participant_dialog,null)
        tv_total_participant = views.findViewById(R.id.tv_total_participant)
        recyclerView = views.findViewById(R.id.recycler_participant)
        tv_cancel = views.findViewById(R.id.tv_cancel)
        line_approve_all = views.findViewById(R.id.line_approve_all)
        btn_add_trip_item = views.findViewById(R.id.btn_add_trip_item)

        line_approve_all.setOnClickListener { approvaListener() }

        initRecyclerView()
        tv_total_participant.text = "Selected Participant (${data.size})"

        adb.setView(views)
        alertDialog = adb.create()
        alertDialog?.getWindow()?.getAttributes()?.windowAnimations = R.style.DialogAnimations_SmileWindow //R.style.DialogAnimations_SmileWindow;
        alertDialog?.setCancelable(false)
        alertDialog?.getWindow()?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        alertDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.show()
        alertDialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        tv_cancel.setOnClickListener {
            cancel()
        }
    }

    private fun approvaListener() {
        alertDialog?.dismiss()
        callbackDialog.selected(idParticipantAvailableApprove)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapterDialog

        adapterDialog.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when(views){
                    -89 ->{
                        idParticipantAvailableApprove.clear()
                        idParticipantAvailableApprove.add(data[position].idParticipant)
                    }
                }
            }
        })
    }

    interface CallbackDialog{
        fun selected(data: ArrayList<String>)
    }

    /*fun addData() {
        var particpant = ParticipantModel()
        particpant.name = "Budi Sentosa"
        particpant.jobtitle = "Manager"

        data.add(particpant)

        var particpant2 = ParticipantModel()
        particpant2.name = "Rama Purwanto"
        particpant2.jobtitle = "Staff"

        data.add(particpant2)

        var particpant3 = ParticipantModel()
        particpant3.name = "Reva Maharani"
        particpant3.jobtitle = "Marketing"

        data.add(particpant3)


        adapterShortBy.setData(data)
    }*/

    fun cancel() {
        alertDialog?.hide()
    }

}