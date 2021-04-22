package com.opsigo.travelaja.module.profile

import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.id_cart_form_layout.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.booking_contact.IdCartModel

class KtpCardFormActivity : BaseActivity(),View.OnClickListener, ToolbarOpsicorp.OnclickButtonListener {
    override fun getLayout(): Int {
        return R.layout.id_cart_form_layout
    }

    val texts = ArrayList<TextView>()
    val lines = ArrayList<LinearLayout>()
    lateinit var dataOrder: OrderAccomodationModel


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

        initDataIntent()
        initToolbar()
    }

    private fun initDataIntent() {
        try {
            if (intent.getBundleExtra(Constants.KEY_BUNDLE).getString(Constants.INPUT_EDIT_KTP,"").isNotEmpty()){
                val dataString = intent.getBundleExtra(Constants.KEY_BUNDLE).getString(Constants.INPUT_EDIT_KTP,"")
                val dataKtp = Serializer.deserialize(dataString,IdCartModel::class.java)
                et_no_id.setText(dataKtp.idCart)
                et_email.setText(dataKtp.email)
                et_fullname.setText(dataKtp.fullname)
                et_no_hp.setText(dataKtp.mobileNum)
            }
        }catch (e:Exception){ }
    }

    private fun initToolbar() {
        dataOrder = Serializer.deserialize(Globals.DATA_ORDER_FLIGHT, OrderAccomodationModel::class.java)

        toolbar.hidenBtnCart()
        toolbar.callbackOnclickToolbar(this)
        toolbar.setDoubleTitle("Adult Passenger","Age 3 and older ")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.doubleTitleGravity(toolbar.CENTER)
        }

        toolbar.changeImageBtnBack(R.drawable.ic_close_white)
    }

    fun discardListener(view: View){
        finish()
    }

    fun saveListener(view: View){
        val model = IdCartModel()
        model.idCart    = et_no_id.text.toString()
        model.email     = et_email.text.toString()
        model.fullname  = et_fullname.text.toString()
        model.mobileNum = et_no_hp.text.toString()
        model.title     = "Mr"
        val intent = Intent()
        intent.putExtra(Constants.RESULT_EDIT_KTP,Serializer.serialize(model))
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