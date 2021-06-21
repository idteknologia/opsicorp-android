package com.mobile.travelaja.module.item_custom.select_passager

import com.mobile.travelaja.databinding.TrainSelectTotalPassangerBinding
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.utility.Globals.setToast
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import com.mobile.travelaja.R
import android.view.Gravity
import android.view.View

class TotalPassangerTrain() {

    var totalInfant = 0
    var totalAdult  = 1
    var limitAdult  = 0
    var limitInfant = 0

    lateinit var views : View
    var alertDialog: AlertDialog? = null
    lateinit var callbackDialog : CallbackSelectPasanger
    lateinit var context : Context
    lateinit var binding: TrainSelectTotalPassangerBinding


    fun create(context: Context,callbackDialog: CallbackSelectPasanger){
        val adb = AlertDialog.Builder(context)
        this.context = context
        this.callbackDialog = callbackDialog
        binding = TrainSelectTotalPassangerBinding.inflate(LayoutInflater.from(context))

        binding.btnNext.callbackOnclickButton(object :ButtonDefaultOpsicorp.OnclickButtonListener{
            override fun onClicked() {
                callbackDialog.total(totalInfant,totalAdult)
                alertDialog?.dismiss()
            }
        })

        binding.btnAddAdult.setOnClickListener {
            if (totalAdult<limitAdult){
                totalAdult++
            }
            else{
                setToast("Max Adult "+limitAdult,context)
            }
            changeTotalView()
        }

        binding.btnMinusAdult.setOnClickListener {
            if(totalAdult!=1){
                totalAdult--
            }
            changeTotalView()
        }

        binding.btnAddInfant.setOnClickListener {
            if(totalInfant<limitInfant){
                totalInfant++
            }
            else{
                setToast("Max Infant"+limitInfant,context)
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
        alertDialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        alertDialog?.window?.getAttributes()?.windowAnimations = R.style.DialogAnimations_SmileWindow //R.style.DialogAnimations_SmileWindow;
        alertDialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        changeTotalView()
    }

    fun setLimitSelect(mLimitAdult:Int,mLimitInfant:Int){
        limitAdult  = mLimitAdult
        limitInfant = mLimitInfant
    }

    fun setCurrentSelect(totalAdult:Int,totalInfant:Int){
        this.totalInfant  = totalInfant
        this.totalAdult   = totalAdult
    }

    interface CallbackSelectPasanger{
        fun total(totalInfant:Int,
                  totalAdult:Int)
    }

    private fun changeTotalView() {
        binding.tvAdult.text  = "${totalAdult} Adult(s)"
        binding.tvInfant.text  = "${totalInfant} Childern"

        if(totalAdult==0){
            binding.btnMinusAdult.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.minus_inactive))
        }else{
            binding.btnMinusAdult.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.minus_active))
        }

        if(totalInfant==0){
            binding.btnMinusInfant.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.minus_inactive))
        }
        else{
            binding.btnMinusInfant.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.minus_active))
        }
    }
}