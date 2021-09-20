package com.mobile.travelaja.module.create_trip.newtrip_pertamina.activity

import android.content.Intent
import com.mobile.travelaja.R
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.module.settlement.view.BankListFragment
import com.mobile.travelaja.module.settlement.view.BankListener

class SelectBankTransferActivity : BaseActivity(), BankListener {

    val bankListFragment = BankListFragment()

    override fun getLayout(): Int {
        return R.layout.activity_select_bank_transfer
    }

    override fun OnMain() {
        loadFragment(bankListFragment,R.id.fragmentBankList)
    }

    override fun onClickItemBank(id: String) {
        var intent = Intent()
        intent.putExtra("keyBank",id)
        setResult(1,intent)
        finish()
    }

    override fun onIvBack() {
        finish()
    }


}