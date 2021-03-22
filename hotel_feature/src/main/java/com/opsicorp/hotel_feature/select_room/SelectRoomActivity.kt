package com.opsicorp.hotel_feature.select_room

import android.os.Build
import android.view.View
import android.content.Intent
import android.widget.TextView
import android.widget.LinearLayout
import com.opsicorp.hotel_feature.R
import com.opsigo.travelaja.utility.*
import com.opsigo.travelaja.BaseActivity
import opsigo.com.datalayer.mapper.Serializer
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.DefaultItemAnimator
import kotlinx.android.synthetic.main.select_room_activity.*
import com.opsicorp.hotel_feature.confirmation.ConfirmationOrderHotel
import opsigo.com.domainlayer.model.accomodation.hotel.SelectRoomModel
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import opsigo.com.domainlayer.model.accomodation.hotel.ResultListHotelModel
import com.opsigo.travelaja.module.item_custom.calendar.CalendarViewOpsicorp
import com.opsigo.travelaja.module.accomodation.view_accomodation.fragment.hotel.DialogSelectDuration

class SelectRoomActivity : BaseActivity(),OnclickListenerRecyclerView,
        ToolbarOpsicorp.OnclickButtonListener,CalendarViewOpsicorp.CallbackResult {


    override fun getLayout(): Int { return R.layout.select_room_activity }

    val data = ArrayList<SelectRoomModel>()
    val adapter by lazy { SelectRoomAdapter(data) }

    var guaranteed = false
    var freeMeal  = false
    var cancelation = false
    lateinit var dataHotel: ResultListHotelModel
    val btn = ArrayList<LinearLayout>()
    val tv  = ArrayList<TextView>()

    override fun OnMain() {
        dataHotel = Serializer.deserialize(Constants.DATA_HOTEL, ResultListHotelModel::class.java)
        initRecyclerView()
        setToolbar()
    }

    private fun getDataListRoom() {
        data.clear()
        data.addAll(dataHotel.room)
        adapter.setData(data)
//        data.addAll(DataDummyAccomodation().addDataDummyRoom())
    }

    fun guarantedListener(view: View){
        guaranteed = false
        changeImageButton(0)
        data.sortBy { it.isGuaranteedBooking }
        adapter.notifyDataSetChanged()
        /*if (guaranteed){
            guaranteed = false
            tv_guaranteed.setTextColor(resources.getColor(R.color.color_text_btn_guaranteed_select_room))
            btn_guaranted.background = resources.getDrawable(R.drawable.rounded_button_filter)
        }
        else{
            guaranteed = true
            tv_guaranteed.setTextColor(resources.getColor(R.color.white))
            btn_guaranted.background = resources.getDrawable(R.drawable.rounded_button_filter_selected)
        }*/
    }

    fun freeMealListener(view: View){
        freeMeal = false
        changeImageButton(1)
        data.sortBy { it.isBreakfast }
        adapter.notifyDataSetChanged()
        /*if (freeMeal){
            freeMeal = false
            tv_fee_meal.setTextColor(resources.getColor(R.color.color_text_btn_guaranteed_select_room))
            btn_free_meal.background = resources.getDrawable(R.drawable.rounded_button_filter)
        }
        else{
            freeMeal = true
            tv_fee_meal.setTextColor(resources.getColor(R.color.white))
            btn_free_meal.background = resources.getDrawable(R.drawable.rounded_button_filter_selected)
        }*/
    }

    fun freeCancelationListener(view: View){
        cancelation = false
        changeImageButton(2)
        data.sortBy { it.isFullCharge }
        adapter.notifyDataSetChanged()
        /*if (cancelation){
            cancelation = false
            tv_free_cancelation.setTextColor(resources.getColor(R.color.color_text_btn_guaranteed_select_room))
            btn_free_cancelation.background = resources.getDrawable(R.drawable.rounded_button_filter)
        }
        else{
            cancelation = true
            tv_free_cancelation.setTextColor(resources.getColor(R.color.white))
            btn_free_cancelation.background = resources.getDrawable(R.drawable.rounded_button_filter_selected)
        }*/
    }

    private fun changeImageButton(position: Int) {
        btn.forEachIndexed { index, linearLayout ->
            if (position==index){
                tv[index].setTextColor(ContextCompat.getColor(this,R.color.white))
                linearLayout.background = resources.getDrawable(R.drawable.rounded_button_filter_selected)
            }
            else {
                tv[index].setTextColor(ContextCompat.getColor(this,R.color.color_text_btn_guaranteed_select_room))
                linearLayout.background = resources.getDrawable(R.drawable.rounded_button_filter)
            }
        }
    }

    private fun setToolbar() {
        toolbar.callbackOnclickToolbar(this)
        toolbar.hidenBtnCart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.doubleTitleGravity(toolbar.START)
        }
        toolbar.setDoubleTitle(dataHotel.nameHotel,"${dataHotel.addressHotel}")
    }
    private fun initRecyclerView() {
        val layoutManagerReview = LinearLayoutManager(this)
        layoutManagerReview.orientation = LinearLayoutManager.VERTICAL
        rv_list_room.layoutManager = layoutManagerReview
        rv_list_room.itemAnimator = DefaultItemAnimator()
        rv_list_room.adapter = adapter

        adapter.setOnclickListener(this)

        getDataListRoom()

        btn.add(btn_guaranted)
        btn.add(btn_free_meal)
        btn.add(btn_free_cancelation)
        tv.add(tv_guaranteed)
        tv.add(tv_fee_meal)
        tv.add(tv_free_cancelation)
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }

    override fun onClick(views: Int, position: Int) {
        when (views) {
            -1 -> {
                Constants.DATA_ROOM_HOTEL = Serializer.serialize(data[position],SelectRoomModel::class.java)
                gotoActivity(ConfirmationOrderHotel::class.java)
            }
            Constants.ONCLICK_INFO_CANCELATION_HOTEL -> {
                val dialog = DialogCancelationPolicy(
                        data[position].cancelLimit)
                showDialogFragment(dialog)
            }
        }
    }

    fun getCalendar(view: View){
        Globals.ONE_TRIP = true
        CalendarViewOpsicorp().showCalendarView(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        CalendarViewOpsicorp().resultCalendarView(requestCode, resultCode, data,this)
    }

    override fun startDate(displayStartDate: String, startDate: String) {
        tv_calendar.text = formatingDate(startDate)
    }

    fun formatingDate(string: String):String{
        val year = DateConverter().formatingDateDefault("yyyy-MM-dd",string).split("-")[0]
        val mount = DateConverter().formatingDateDefault("yyyy-MM-dd",string).split("-")[1].substring(0,3)
        val day = DateConverter().formatingDateDefault("yyyy-MM-dd",string).split("-")[2]

        return "${day} ${mount} ${year}"
    }

    override fun endDate(displayEndDate: String, endDate: String) {

    }

    override fun canceledCalendar() {

    }

    fun selectDialogDuration(view: View){
        DialogSelectDuration(this).create(6,object :DialogSelectDurationHotel{
            override fun duration(duration: String) {
                tv_duration.text = duration+" night(s)"
            }
        })
    }

    fun selectGuestAndRoom(view: View){
        DialogSelectGuestRoom(this).create(object :DialogSelectGuestRoomCallback{
            override fun selected(totalGuest: String, totalRoom: String) {
                tv_room_and_guest.text = "${totalGuest} Guest, ${totalRoom} Room"
            }
        })
    }

}