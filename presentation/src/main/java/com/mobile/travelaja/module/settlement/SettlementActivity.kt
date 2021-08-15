package com.mobile.travelaja.module.settlement

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mobile.travelaja.R
import com.mobile.travelaja.databinding.ActivitySettlementBinding
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory

class SettlementActivity : AppCompatActivity() {
    private lateinit var viewModel : SettlementViewModel
    private lateinit var binding : ActivitySettlementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_settlement)
        viewModel = ViewModelProvider(this, DefaultViewModelFactory(false, this)).get(SettlementViewModel::class.java)
        binding.viewModel = viewModel
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_settlement_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            binding.includeBottomSettlement.root.isVisible = destination.id == R.id.transportExpenseFragment
//        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 9 && resultCode== Activity.RESULT_OK) {
            val selectedCode = data?.getStringExtra(TripSearchActivity.SELECTED)
            viewModel.selectedCode.set(selectedCode)
        }
    }
}