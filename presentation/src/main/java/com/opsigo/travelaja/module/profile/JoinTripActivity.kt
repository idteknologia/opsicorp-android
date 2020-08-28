package com.opsigo.travelaja.module.profile

import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.barcode.scanner.ScannerOpsicorp
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import kotlinx.android.synthetic.main.activity_tes.*

class JoinTripActivity : BaseActivity(),ToolbarOpsicorp.OnclickButtonListener {

    override fun getLayout(): Int { return R.layout.activity_tes  }

    override fun btnBack() {

    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        scanner_barcode.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onPause() {
        super.onPause()
        scanner_barcode.onPause()
    }

    override fun onResume() {
        super.onResume()
        scanner_barcode.onResume()
    }

    override fun OnMain() {
        toolbar.changeImageCard(R.drawable.ic_setting_dot_tree_my_booking)
        toolbar.hidenBtnCart()
        toolbar.changeImageBtnBack(R.drawable.ic_back)
        toolbar.setToolbarColorView(R.color.white)
        toolbar.callbackOnclickToolbar(this)

        scanner_barcode.callbackScanner(object :ScannerOpsicorp.CallbackScannerListener{
            override fun responseScanner(result: String?) {

            }
        })

    }



}