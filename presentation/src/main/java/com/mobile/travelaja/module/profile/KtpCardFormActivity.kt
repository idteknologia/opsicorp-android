package com.mobile.travelaja.module.profile

import java.util.*
import android.os.Build
import android.view.View
import android.content.Intent
import com.mobile.travelaja.R
import android.widget.TextView
import android.widget.DatePicker
import android.widget.LinearLayout
import android.app.DatePickerDialog
import kotlin.collections.ArrayList
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.utility.Globals
import opsigo.com.datalayer.mapper.Serializer
import com.mobile.travelaja.utility.Constants
import kotlinx.android.synthetic.main.id_cart_form_layout.*
import com.mobile.travelaja.utility.FormatingMonthIndonesian
import opsigo.com.domainlayer.model.booking_contact.IdCartModel
import kotlinx.android.synthetic.main.id_cart_form_layout.toolbar
import kotlinx.android.synthetic.main.id_cart_form_layout.et_email
import kotlinx.android.synthetic.main.id_cart_form_layout.tv_btn_mr
import kotlinx.android.synthetic.main.id_cart_form_layout.tv_btn_ms
import kotlinx.android.synthetic.main.id_cart_form_layout.tv_btn_mrs
import kotlinx.android.synthetic.main.id_cart_form_layout.line_btn_ms
import kotlinx.android.synthetic.main.id_cart_form_layout.line_btn_mr
import kotlinx.android.synthetic.main.id_cart_form_layout.line_btn_mrs
import kotlinx.android.synthetic.main.id_cart_form_layout.tv_day_birtdate
import kotlinx.android.synthetic.main.id_cart_form_layout.tv_year_birtdate
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.utility.DateConverter
import kotlinx.android.synthetic.main.id_cart_form_layout.tv_month_birtdate
import opsigo.com.datalayer.datanetwork.dummy.accomodation.OrderAccomodationModel

class KtpCardFormActivity : BaseActivity(),View.OnClickListener, ToolbarOpsicorp.OnclickButtonListener {
    override fun getLayout(): Int {
        return R.layout.id_cart_form_layout
    }

    val texts = ArrayList<TextView>()
    val lines = ArrayList<LinearLayout>()
    lateinit var dataOrder: OrderAccomodationModel
    var titlePassenger = "Mr"
    var month = 1


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
            if (intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getString(Constants.INPUT_EDIT_KTP,"").toString().isNotEmpty()){
                val dataString = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getString(Constants.INPUT_EDIT_KTP,"")
                val dataKtp = Serializer.deserialize(dataString,IdCartModel::class.java)
                if (dataKtp.idCart.contains("null")||dataKtp.idCart.isEmpty()) et_no_id.setText("") else et_no_id.setText(dataKtp.idCart)
                et_email.setText(dataKtp.email)
                et_fullname.setText(dataKtp.fullname)
                et_no_hp.setText(dataKtp.mobilePhone)
                mappingtitle(dataKtp.title)

                if (dataKtp.birthDate.isNotEmpty()){
                    var bd = dataKtp.birthDate.replace("00:00:00","").trim()
                    tv_year_birtdate.setText(bd.split("-")[0])
                    tv_month_birtdate.setText(DateConverter().getDate(dataKtp.birthDate,"yyyy-MM-dd","MMM"))
                    tv_day_birtdate.setText(bd.split("-")[2])
                    month = DateConverter().getDate(dataKtp.birthDate,"yyyy-MM-dd","M").toInt()
                }
            }
        }catch (e:Exception){ }
    }

    private fun mappingtitle(string: String) {
        titlePassenger = string
        when(string){
            "Mr" -> {
                Globals.changeViewButtonLinearlayout(texts,lines,0,this)
            }
            "Mrs" -> {
                Globals.changeViewButtonLinearlayout(texts,lines,1,this)
            }
            "Ms" -> {
                Globals.changeViewButtonLinearlayout(texts,lines,2,this)
            }
            else -> {
                titlePassenger = "Mr"
            }
        }

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
        if (Globals.validatiEdittext(getField())){
            val model = IdCartModel()
            model.idCart      = et_no_id.text.toString()
            model.email       = et_email.text.toString()
            model.fullname    = et_fullname.text.toString()
            model.mobilePhone = "${et_no_hp.text}"
            model.title       = titlePassenger
            val birthdate     = "${tv_year_birtdate.text}-${month}-${tv_day_birtdate.text}"
            model.birthDate   = DateConverter().getDate(birthdate,"yyyy-MM-dd","yyyy-MM-dd")
            val intent = Intent()
            intent.putExtra(Constants.RESULT_EDIT_KTP,Serializer.serialize(model))
            Globals.finishResultOk(this,intent)
        }
        else {
            showAllert(getString(R.string.sorry),getString(R.string.warning_canot_be_empty))
        }

    }

    private fun getField(): ArrayList<String> {
        val data = ArrayList<String>()
        data.add(et_no_id.text.toString())
        data.add(et_email.text.toString())
        data.add(et_fullname.text.toString())
        data.add(et_no_hp.text.toString())
        data.add(titlePassenger)
        data.add("${tv_year_birtdate.text}-${month}-${tv_day_birtdate.text}")
        return data
    }

    fun getDatePickerBirtday(view: View){
        val cal   = Calendar.getInstance()
        val year  = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day   = cal.get(Calendar.DAY_OF_MONTH)

        Globals.getCalendarSpinner(this,year,month,day,object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                tv_day_birtdate.text =  p3.toString()
                tv_month_birtdate.text = FormatingMonthIndonesian().formatMonth(p2)
                this@KtpCardFormActivity.month = p2
                tv_year_birtdate.text = p1.toString()
            }
        })
    }

    override fun onClick(p0: View?) {
        when (p0){
            tv_btn_mr -> {
                titlePassenger = "Mr"
                Globals.changeViewButtonLinearlayout(texts,lines,0,this)
            }
            line_btn_mr-> {
                titlePassenger = "Mr"
                Globals.changeViewButtonLinearlayout(texts,lines,0,this)
            }
            tv_btn_mrs -> {
                titlePassenger = "Mrs"
                Globals.changeViewButtonLinearlayout(texts,lines,1,this)
            }
            line_btn_mrs -> {
                titlePassenger = "Mrs"
                Globals.changeViewButtonLinearlayout(texts,lines,1,this)
            }
            tv_btn_ms -> {
                titlePassenger = "Ms"
                Globals.changeViewButtonLinearlayout(texts,lines,2,this)
            }
            line_btn_ms -> {
                titlePassenger = "Ms"
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