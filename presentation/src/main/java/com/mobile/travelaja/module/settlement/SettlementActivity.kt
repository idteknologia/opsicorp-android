package com.mobile.travelaja.module.settlement

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mobile.travelaja.R
import com.mobile.travelaja.databinding.ActivitySettlementBinding
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import java.util.jar.Manifest

//layout nya detail_trip_activity_view

class SettlementActivity : AppCompatActivity(),FilePermissionListener {
    private lateinit var viewModel: SettlementViewModel
    private lateinit var binding: ActivitySettlementBinding
    private val permissionsLaunch =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {

        }
    private val startActivityLaunch =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        }

    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

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

        permissionsLaunch.launch(permissions)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 9 && resultCode == Activity.RESULT_OK) {
            val selectedCode = data?.getStringExtra(TripSearchActivity.SELECTED)
            viewModel.selectedCode.set(selectedCode)
        }
    }
    private fun shouldShowRequestPermission(permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
    }

    override fun onPermissionInputFile(filePermissionGranted : Boolean) {
        val map = permissions.map { it to shouldShowRequestPermission(it) }.toMap()
        if (map.containsValue(true)) {
            permissionsLaunch.launch(permissions)
        } else {
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.alert)
                .setCancelable(false)
                .setMessage(R.string.alert_application_need_permission_storage_and_camera)
                .setPositiveButton(R.string.open_setting) { d, which ->
                    startActivityLaunch.launch(intentSettingApp())
                    d.dismiss()
                }.setNegativeButton(R.string.cancel) { d, which ->
                    d.dismiss()
                }.show()
        }
    }

    private fun intentSettingApp(): Intent {
        val uri = Uri.fromParts(
            "package", packageName, null
        )
        return Intent().also {
            it.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            it.data = uri
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }



}