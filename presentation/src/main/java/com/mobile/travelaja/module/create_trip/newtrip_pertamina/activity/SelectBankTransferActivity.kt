package com.mobile.travelaja.module.create_trip.newtrip_pertamina.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobile.travelaja.R
import com.mobile.travelaja.module.settlement.view.BankListener

class SelectBankTransferActivity : AppCompatActivity(), BankListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_bank_transfer)
    }

    override fun onClickItemBank(id: String) {
        var intent = Intent()
        intent.putExtra("keyBank",id)
        setResult(1,intent)
        finish()
    }


}