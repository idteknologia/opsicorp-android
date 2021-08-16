package com.mobile.travelaja.module.settlement

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mobile.travelaja.R
import com.mobile.travelaja.databinding.ActivitySettlementBinding
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import java.util.jar.Manifest

class SettlementActivity : AppCompatActivity() {
    private lateinit var viewModel: SettlementViewModel
    private lateinit var binding: ActivitySettlementBinding
    private val permissionsLaunch =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settlement)
        viewModel = ViewModelProvider(
            this,
            DefaultViewModelFactory(false, this)
        ).get(SettlementViewModel::class.java)
        binding.viewModel = viewModel
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_settlement_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            binding.includeBottomSettlement.root.isVisible = destination.id == R.id.transportExpenseFragment
//        }

        permissionsLaunch.launch(
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 9 && resultCode == Activity.RESULT_OK) {
            val selectedCode = data?.getStringExtra(TripSearchActivity.SELECTED)
            viewModel.selectedCode.set(selectedCode)
        }
    }
}