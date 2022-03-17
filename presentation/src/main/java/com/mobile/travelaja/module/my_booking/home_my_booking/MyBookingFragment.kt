package com.mobile.travelaja.module.my_booking.home_my_booking

import java.util.*
import android.os.Build
import android.os.Bundle
import android.view.View
import android.content.Intent
import com.mobile.travelaja.R
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.base.BaseFragment
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.DateConverter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.empty_cart_view.*
import opsigo.com.datalayer.datanetwork.GetDataMyBooking
import kotlinx.android.synthetic.main.my_booking_layout.*
import opsigo.com.domainlayer.callback.CallbackListMyBooking
import opsigo.com.domainlayer.model.my_booking.MyBookingModel
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import com.mobile.travelaja.module.my_booking.adapter.MyBookingAdapter
import com.mobile.travelaja.module.my_booking.model.FilterPurchaseModel
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.module.item_custom.calendar.NewCalendarViewOpsicorp
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.module.my_booking.purchase_list_detail.FilterPurchaseDialog

class MyBookingFragment : BaseFragment(),OnclickListenerRecyclerView ,ButtonDefaultOpsicorp.OnclickButtonListener,
    NewCalendarViewOpsicorp.CallbackResult{

    override fun getLayout(): Int { return R.layout.my_booking_layout }

    val data = ArrayList<MyBookingModel>()
    val adapter by lazy { MyBookingAdapter(requireContext(), data) }
    var dateFrom = ""
    var dateTo   = ""
    lateinit var dialogFiter: FilterPurchaseDialog
    val dataFilterType by lazy { ArrayList<FilterPurchaseModel>() }

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {

        initRecyclerView()
        getData(firstDateQuery().first, firstDateQuery().second, "${Constants.TripType.Airline},${Constants.TripType.Hotel},${Constants.TripType.KAI}")
        addDataFilter()
    }

    fun showFilter(){
        dialogFiter = FilterPurchaseDialog(requireContext())
        dialogFiter.create(dateFrom,dateTo,dataFilterType,object :FilterPurchaseDialog.CallbackFilterPurchase{
                override fun filter(
                    dateTo: String,
                    dateFrom: String,
                    itemType: ArrayList<FilterPurchaseModel>
                ) {
                    this@MyBookingFragment.dateTo = dateTo
                    this@MyBookingFragment.dateFrom = dateFrom
                    dataFilterType.clear()
                    dataFilterType.addAll(itemType)
                    var typeItem = ""
                    dataFilterType.forEach {
                        if (it.checlist){
                            typeItem = typeItem+it.id+","
                        }
                    }
                    getData(dateFrom,dateTo,typeItem)
                }
            })
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_my_booking.layoutManager = layoutManager
        rv_my_booking.itemAnimator = DefaultItemAnimator()
        rv_my_booking.adapter = adapter

        adapter.setOnclickListener(this)
    }

    private fun getData(dateFrom: String, dateTo: String, typeItem: String) {
        this@MyBookingFragment.data.clear()
        adapter.notifyDataSetChanged()
        loading_view.visibility = View.VISIBLE
        this@MyBookingFragment.dateFrom = dateFrom
        this@MyBookingFragment.dateTo   = dateTo
        tv_date.text = "${DateConverter().getDate(dateFrom,"yyyy-MM-dd","dd MMM yyyy")} - ${DateConverter().getDate(dateTo,"yyyy-MM-dd","dd MMM yyyy")}"
        GetDataMyBooking(getBaseUrl()).getListMyBooking(getToken(),30,1,typeItem,dateFrom,dateTo,object :CallbackListMyBooking{
            override fun success(data: ArrayList<MyBookingModel>) {
                loading_view.visibility = View.GONE
                this@MyBookingFragment.data.addAll(data) //MapperModelListMybooking().mapper(DataDummyMyBooking().addDataListMybooking())
                adapter.setData(this@MyBookingFragment.data)
                checkEmpety()
            }

            override fun failed(message: String) {
                try {
                    loading_view.visibility = View.GONE
                    Globals.showAlert(getString(R.string.sorry),message,requireContext())
                }catch (e:Exception){}
            }
        })
    }

    private fun checkEmpety() {
        if (data.isEmpty()){
            showEemptyLayout()
        }
        else {
            showRecyclerView()
        }
    }

    private fun showRecyclerView() {
        rv_my_booking.visibility = View.VISIBLE
        lay_empty.visibility     = View.GONE
    }

    private fun showEemptyLayout() {
        lay_empty.visibility     = View.VISIBLE
        rv_my_booking.visibility = View.GONE
        btn_home_page.callbackOnclickButton(this)

        img_empty.setImageDrawable(resources.getDrawable(R.drawable.no_transaction))
        tv_massage_empty.text = getString(R.string.you_havent_made_any_purchase)
        tv_title_empty.text = getString(R.string.oh_crap)
        btn_home_page.setTextButton(getString(R.string.make_a_new_purchase))
    }

    override fun onClick(views: Int, position: Int) {
        when(views){
            -1-> {

            }
        }
    }

    fun firstDateQuery():Pair<String,String>{
        val referenceDate = Date()
        val c: Calendar = Calendar.getInstance()
        c.setTime(referenceDate)
        c.add(Calendar.DAY_OF_MONTH, -90)

        val calendar = Calendar.getInstance()
        val mdformat = SimpleDateFormat("yyyy-MM-dd")
        val toDay  = mdformat.format(calendar.getTime())

        return Pair(SimpleDateFormat("yyyy-MM-dd").format(c.getTime()),toDay)
    }

    fun addDataFilter() {
        dataFilterType.clear()

        val listProduct = ArrayList<String>()
        val listPayment = ArrayList<String>()
        listProduct.clear()
        listPayment.clear()


        listProduct.add(getString(R.string.txt_flight))
        listProduct.add(getString(R.string.txt_hotel))
        listProduct.add(getString(R.string.txt_train))

        listProduct.forEachIndexed { index, s ->
            val mData = FilterPurchaseModel()
            mData.name = s
            mData.id = index.toString()
            dataFilterType.add(mData)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        NewCalendarViewOpsicorp().resultCalendarView(requestCode, resultCode, data,this)
    }

    override fun startDate(displayStartDate: String, startDate: String) {
        dialogFiter.dateFrom = startDate
        dialogFiter.bind.tvDateFrom.text = displayStartDate
    }

    override fun endDate(displayEndDate: String, endDate: String) {
        dialogFiter.dateTo = endDate
        dialogFiter.bind.tvDateTo.text = displayEndDate
        dialogFiter.onCheckOtherDays()
    }

    override fun canceledCalendar() {

    }

    override fun onClicked() {

    }
}