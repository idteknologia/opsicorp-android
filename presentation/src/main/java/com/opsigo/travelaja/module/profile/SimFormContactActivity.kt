package com.opsigo.travelaja.module.profile

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.*

class SimFormContactActivity : BaseActivity(),View.OnClickListener,ToolbarOpsicorp.OnclickButtonListener {
    override fun getLayout(): Int { return R.layout.sim_form_booking_contact_view }

    val texts = ArrayList<TextView>()
    val lines = ArrayList<LinearLayout>()

    override fun OnMain() {

        texts.add(tv_btn_mr)
        texts.add(tv_btn_mrs)
        texts.add(tv_btn_ms)
        lines.add(line_btn_mr)
        lines.add(line_btn_mrs)
        lines.add(line_btn_ms)

        tv_btn_mr.setOnClickListener(this)
        tv_btn_mrs.setOnClickListener(this)
        tv_btn_ms.setOnClickListener(this)
        line_btn_mr.setOnClickListener(this)
        line_btn_mrs.setOnClickListener(this)
        line_btn_ms.setOnClickListener(this)
        toolbar.callbackOnclickToolbar(this)

    }

    fun discardListener(view: View){
        finish()
    }

    fun saveListener(view: View){
        finish()
    }

    override fun onClick(p0: View?) {
        when (p0){
            tv_btn_mr -> {
                Globals.changeViewButtonLinearlayout(texts,lines,0,this)
            }
            line_btn_mr-> {
                Globals.changeViewButtonLinearlayout(texts,lines,0,this)
            }
            tv_btn_mrs -> {
                Globals.changeViewButtonLinearlayout(texts,lines,1,this)
            }
            line_btn_mrs -> {
                Globals.changeViewButtonLinearlayout(texts,lines,1,this)
            }
            tv_btn_ms -> {
                Globals.changeViewButtonLinearlayout(texts,lines,2,this)
            }
            line_btn_ms -> {
                Globals.changeViewButtonLinearlayout(texts,lines,2,this)
            }
        }
    }

    override fun btnBack() {
        onBackPressed()
    }

    override fun logoCenter() {
    }

    override fun btnCard() {
    }


}