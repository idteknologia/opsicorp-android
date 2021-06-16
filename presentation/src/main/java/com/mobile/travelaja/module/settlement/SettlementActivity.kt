package com.mobile.travelaja.module.settlement

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.mobile.travelaja.R
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory

class SettlementActivity : AppCompatActivity() {

    private lateinit var viewModel : SettlementViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settlement)
        viewModel = ViewModelProvider(this, DefaultViewModelFactory(false, this)).get(SettlementViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 9 && resultCode== Activity.RESULT_OK) {
            val selectedCode = data?.getStringExtra(TripSearchActivity.SELECTED)
            viewModel.selectedCode.set(selectedCode)
        }
    }
}