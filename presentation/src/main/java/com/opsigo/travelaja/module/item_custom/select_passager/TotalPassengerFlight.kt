package com.opsigo.travelaja.module.item_custom.select_passager

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.opsigo.travelaja.R
import com.opsigo.travelaja.databinding.FlightSelectTotalPassengerBinding
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.utility.Globals

class TotalPassengerFlight() {
    var totalInfant = 0
    var totalChild = 0
    var totalAdult  = 1
    var limitAdult  = 0
    var limitInfant = 0
    var limitChild = 0

    lateinit var views : View
    var alertDialog: AlertDialog? = null
    lateinit var callbackDialog : CallbackSelectPasanger
    lateinit var context : Context
    lateinit var binding: FlightSelectTotalPassengerBinding


    fun create(context: Context, callbackDialog: CallbackSelectPasanger){
        val adb = AlertDialog.Builder(context)
        this.context = context
        this.callbackDialog = callbackDialog
        binding = FlightSelectTotalPassengerBinding.inflate(LayoutInflater.from(context))

        binding.btnNext.callbackOnclickButton(object : ButtonDefaultOpsicorp.OnclickButtonListener{
            override fun onClicked() {
                callbackDialog.total(totalInfant,totalChild,totalAdult)
                alertDialog?.dismiss()
            }
        })

        binding.btnAddAdult.setOnClickListener {
            if (totalAdult<limitAdult){
                totalAdult++
            }
            else{
                Globals.setToast("Max Adult is " + limitAdult, context)
            }
            changeTotalView()
        }

        binding.btnMinusAdult.setOnClickListener {
            if(totalAdult!=1){
                totalAdult--
            }
            changeTotalView()
        }

        binding.btnAddChild.setOnClickListener {
            if (totalChild<limitChild){
                totalChild++
            } else {
                Globals.setToast("Max Child is " + limitChild, context)
            }
            changeTotalView()
        }

        binding.btnMinusChild.setOnClickListener {
            if (totalChild!=0){
                totalChild--
            }
            changeTotalView()
        }

        binding.btnAddInfant.setOnClickListener {
            if(totalInfant<limitInfant){
                totalInfant++
            }
            else{
                Globals.setToast("Max Infant is " + limitInfant, context)
            }
            changeTotalView()
        }

        binding.btnMinusInfant.setOnClickListener {
            if(totalInfant!=0){
                totalInfant--
            }
            changeTotalView()
        }

        adb.setView(binding.root)
        alertDialog = adb.create()
        alertDialog?.show()
        alertDialog?.setCancelable(true)
        alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.window?.setGravity(Gravity.CENTER or Gravity.BOTTOM)
        alertDialog?.window?.getAttributes()?.windowAnimations = R.style.DialogAnimations_SmileWindow //R.style.DialogAnimations_SmileWindow;
        alertDialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        changeTotalView()
    }

    fun setLimitSelect(mLimitAdult:Int,mLimitInfant:Int,mLimitChild:Int){
        limitAdult  = mLimitAdult
        limitChild =  mLimitChild
        limitInfant = mLimitInfant
    }

    fun setCurrentSelect(totalAdult:Int,totalInfant:Int,totalChild: Int){
        this.totalInfant  = totalInfant
        this.totalChild = totalChild
        this.totalAdult   = totalAdult
    }

    interface CallbackSelectPasanger{
        fun total(totalInfant:Int,
                  totalChild:Int,
                  totalAdult:Int)
    }

    private fun changeTotalView() {
        binding.tvAdult.text  = "${totalAdult} Adult(s)"
        binding.tvInfant.text  = "${totalInfant} Infant(s)"
        binding.tvChild.text = "${totalChild} Children"

        if(totalAdult==0){
            binding.btnMinusAdult.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.minus_inactive))
        }else{
            binding.btnMinusAdult.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.minus_active))
        }

        if(totalInfant==0){
            binding.btnMinusInfant.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.minus_inactive))
        }
        else{
            binding.btnMinusInfant.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.minus_active))
        }

        if (totalChild==0){
            binding.btnMinusChild.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.minus_inactive))
        } else {
            binding.btnMinusChild.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.minus_active))
        }
    }
}