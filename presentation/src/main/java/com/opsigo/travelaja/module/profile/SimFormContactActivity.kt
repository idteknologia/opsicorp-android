package com.opsigo.travelaja.module.profile

import android.content.Intent
import android.os.Build
import android.view.Gravity.CENTER
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.passport_form_activity_view.*
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.*
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.line_btn_mr
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.line_btn_mrs
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.line_btn_ms
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.toolbar
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.tv_btn_mr
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.tv_btn_mrs
import kotlinx.android.synthetic.main.sim_form_booking_contact_view.tv_btn_ms
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.booking_contact.SimModel
import java.lang.Exception

class SimFormContactActivity : BaseActivity(),View.OnClickListener, ToolbarOpsicorp.OnclickButtonListener {
    override fun getLayout(): Int { return R.layout.sim_form_booking_contact_view }

    val texts = ArrayList<TextView>()
    val lines = ArrayList<LinearLayout>()

    override fun OnMain() {
        initDataIntent()
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
        initToolbar()
    }

    private fun initDataIntent() {
        try {
            if (intent.getBundleExtra(Constants.KEY_BUNDLE).getString(Constants.INPUT_EDIT_SIM,"").isNotEmpty()){
                val dataString = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(Constants.INPUT_EDIT_SIM,"")
                val dataSim    = Serializer.deserialize(dataString,SimModel::class.java)

                et_number_driver_license.setText(dataSim.idSim)
                et_number_driver_license.setText(dataSim.title)
                et_name_driver_license.setText(dataSim.name)
            }
        }catch (e:Exception){}
    }

    private fun initToolbar() {
        toolbar.hidenBtnCart()
        toolbar.callbackOnclickToolbar(this)
        toolbar.setDoubleTitle("Adult Passenger","Age 3 and older ")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.doubleTitleGravity(CENTER)
        }
        toolbar.changeImageBtnBack(R.drawable.ic_close_white)
    }

    fun discardListener(view: View){
        finish()
    }

    fun saveListener(view: View){
        val model = SimModel()
        model.id          = ""
        model.idSim       = et_number_driver_license.text.toString()
        model.title       = "Mr"
        model.name        = et_name_driver_license.text.toString()
        model.codePos     = ""
        model.address     = ""
        model.birtDate    = ""

        val intent = Intent()
        intent.putExtra(Constants.RESULT_EDIT_SIM, Serializer.serialize(model))
        setLog("oke----1")
        Globals.finishResultOk(this,intent)
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