package com.mobile.travelaja.module.item_custom.dialog_cabin_class

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.travelaja.R
import com.mobile.travelaja.databinding.CabinClassDialogBinding
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataDummyAccomodation
import opsigo.com.domainlayer.model.accomodation.flight.FilterFlightModel

class CabisClassDialog {

    var selectedClass = ""
    var dataCabin = ArrayList<FilterFlightModel>()
    val adapterCabinClass by lazy { CabinClassAdapter(dataCabin) }

    lateinit var views : View
    var alertDialog: AlertDialog? = null
    lateinit var callbackDialog : CallbackSelectCabin
    lateinit var context : Context
    lateinit var binding: CabinClassDialogBinding

    fun create(context: Context, callbackDialog: CallbackSelectCabin){
        val adb = AlertDialog.Builder(context)
        this.context = context
        this.callbackDialog = callbackDialog
        binding = CabinClassDialogBinding.inflate(LayoutInflater.from(context))

        binding.btnNext.callbackOnclickButton(object : ButtonDefaultOpsicorp.OnclickButtonListener{
            override fun onClicked() {
                callbackDialog.select(selectedClass)
                alertDialog?.dismiss()
            }
        })

        setData()
        initRv()

        adb.setView(binding.root)
        alertDialog = adb.create()
        alertDialog?.show()
        alertDialog?.setCancelable(true)
        alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.window?.setGravity(Gravity.CENTER or Gravity.BOTTOM)
        alertDialog?.window?.getAttributes()?.windowAnimations = R.style.DialogAnimations_SmileWindow //R.style.DialogAnimations_SmileWindow;
        alertDialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

    }

    private fun initRv() {
        val layoutManagerCabin = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManagerCabin.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        binding.rvCabinClass.layoutManager = layoutManagerCabin
        binding.rvCabinClass.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        binding.rvCabinClass.adapter = adapterCabinClass

        adapterCabinClass.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when (views){
                    -1 -> {
                        selectedClass = dataCabin[position].name
                        adapterCabinClass.notifyItemChanged(position)
                    }
                }
            }
        })

    }

    private fun setData() {
        dataCabin = DataDummyAccomodation().addCabinClass()
        adapterCabinClass.setData(dataCabin)
    }

    fun setCurrentSelect(selectedClass: String){
        this.selectedClass  = selectedClass

    }


    interface CallbackSelectCabin{
        fun select(selectedClass:String)
    }
}