package com.mobile.travelaja.module.item_custom.select_passager

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
import com.mobile.travelaja.databinding.SelectTotalGuestHotelBinding

class TotalGuestHotel() {

    var totalGuest = 0
    var limitGuest = 0

    lateinit var views : View
    var alertDialog: AlertDialog? = null
    lateinit var callbackDialog : CallbackSelectPasanger
    lateinit var context : Context
    lateinit var binding: SelectTotalGuestHotelBinding


    fun create(context: Context,callbackDialog: CallbackSelectPasanger){
        val adb = AlertDialog.Builder(context)
        this.context = context
        this.callbackDialog = callbackDialog
        binding = SelectTotalGuestHotelBinding.inflate(LayoutInflater.from(context))

        binding.btnNext.callbackOnclickButton(object :ButtonDefaultOpsicorp.OnclickButtonListener{
            override fun onClicked() {
                callbackDialog.total(totalGuest)
                alertDialog?.dismiss()
            }
        })

        binding.btnAddAdult.setOnClickListener {
            if (totalGuest<limitGuest){
                totalGuest++
            }
            else{
                setToast("Max Adult "+limitGuest,context)
            }
            changeTotalView()
        }

        binding.btnMinusAdult.setOnClickListener {
            if(totalGuest!=1){
                totalGuest--
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

    fun setLimitSelect(mLimitGuest:Int){
        limitGuest  = mLimitGuest
    }

    fun setCurrentSelect(totalGuest:Int){
        this.totalGuest  = totalGuest
    }

    interface CallbackSelectPasanger{
        fun total(totalGuest:Int)
    }

    private fun changeTotalView() {
        binding.tvAdult.text  = "$totalGuest ${context.getString(R.string.guest)}(s)"

        if(totalGuest==0){
            binding.btnMinusAdult.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.minus_inactive))
        }else{
            binding.btnMinusAdult.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.minus_active))
        }
    }
}