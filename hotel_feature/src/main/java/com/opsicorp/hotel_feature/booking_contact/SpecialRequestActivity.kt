package com.opsicorp.hotel_feature.booking_contact

import android.os.Build
import android.view.View
import android.text.Editable
import android.content.Intent
import android.text.TextWatcher
import com.opsicorp.hotel_feature.R
import com.mobile.travelaja.utility.Globals
import opsigo.com.datalayer.mapper.Serializer
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.base.BaseActivity
import kotlinx.android.synthetic.main.activity_special_request.*
import opsigo.com.domainlayer.model.accomodation.hotel.SelectRoomModel
import kotlinx.android.synthetic.main.activity_special_request.toolbar
import kotlinx.android.synthetic.main.activity_special_request.et_notes
import opsigo.com.domainlayer.model.accomodation.hotel.ResultListHotelModel
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp

class SpecialRequestActivity : BaseActivity(),
        ToolbarOpsicorp.OnclickButtonListener,
        ButtonDefaultOpsicorp.OnclickButtonListener {
    override fun getLayout(): Int { return R.layout.activity_special_request }

    lateinit var dataHotel: ResultListHotelModel
    lateinit var dataRoom: SelectRoomModel

    val BED_TYPE_REQUEST    = 1089
    val OTHER_TYPE_REQUEST  = 1088
    val KEY_VALUE           = "value"
    val KEY_REQUEST         = "type"
    var activeButton        = false

    override fun OnMain() {
        initDataOrder()
        initToolbar()
        checkTypeLayout()
        initViewListener()
    }

    private fun initDataOrder() {
        dataHotel = Serializer.deserialize(Constants.DATA_HOTEL, ResultListHotelModel::class.java)
        dataRoom = Serializer.deserialize(Constants.DATA_ROOM_HOTEL, SelectRoomModel::class.java)
    }

    private fun initViewListener() {
        unActiveButtonSubmit()
        et_notes.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().length>0){
                    activeButtonSubmit()
                }
                else {
                    unActiveButtonSubmit()
                }

                val countText: Int
                countText = p0.toString().length
                tv_max_notes.text = "${countText}/100"
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })


        line_rb_1.setOnClickListener { kingSizeBed() }
        rb_1.setOnClickListener { kingSizeBed() }
        line_rb_2.setOnClickListener { queenSizeBed() }
        rb_2.setOnClickListener { queenSizeBed() }
        line_rb_3.setOnClickListener { twinSizeBed() }
        rb_3.setOnClickListener { twinSizeBed() }
        btn_submit.callbackOnclickButton(this)
        btn_submit.setTextButton("Apply")
    }

    private fun unActiveButtonSubmit() {
        btn_submit.changeBackgroundDrawable(com.mobile.travelaja.R.drawable.rounded_button_dark_select_budget)
        activeButton           = false
        btn_submit.isClickable = activeButton
    }

    private fun activeButtonSubmit() {
        btn_submit.changeBackgroundDrawable(com.mobile.travelaja.R.drawable.rounded_button_yellow )
        activeButton           = true
        btn_submit.isClickable = activeButton
    }

    private fun checkTypeLayout() {
        if (intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getInt(KEY_REQUEST)==BED_TYPE_REQUEST){
            line_other_view.visibility = View.GONE
            line_bed_type.visibility   = View.VISIBLE
            title_bed_type.visibility  = View.VISIBLE
        }
        else {
            line_other_view.visibility = View.VISIBLE
            line_bed_type.visibility   = View.GONE
            title_bed_type.visibility  = View.GONE
        }
        name_guest.text            = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getString(Constants.KEY_NAME_GUEST,"").toString()

    }

    private fun initToolbar() {
        toolbar.callbackOnclickToolbar(this)
        toolbar.hidenBtnCart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.doubleTitleGravity(toolbar.START)
        }
        toolbar.setDoubleTitle(dataHotel.nameHotel,"${dataRoom.titleRoom}")
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }

    override fun onClicked() {
        if (activeButton){
            val data = Intent(this,SpecialRequestActivity::class.java)
            data.putExtra(KEY_VALUE,et_notes.text.toString())
            Globals.finishResultOk(this,data)
        }
    }

    private fun twinSizeBed() {
        val data = Intent(this,SpecialRequestActivity::class.java)
        data.putExtra(KEY_VALUE,"Twin Bed")
        Globals.finishResultOk(this,data)
    }

    private fun queenSizeBed() {
        val data = Intent(this,SpecialRequestActivity::class.java)
        data.putExtra(KEY_VALUE,"Queen Size Bed")
        Globals.finishResultOk(this,data)
    }

    private fun kingSizeBed() {
        val data = Intent(this,SpecialRequestActivity::class.java)
        data.putExtra(KEY_REQUEST,BED_TYPE_REQUEST)
        data.putExtra(KEY_VALUE,"King Size Bed")
        Globals.finishResultOk(this,data)
    }

}