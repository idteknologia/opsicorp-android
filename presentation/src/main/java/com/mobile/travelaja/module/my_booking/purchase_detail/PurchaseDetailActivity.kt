package com.mobile.travelaja.module.my_booking.purchase_detail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.base.BaseActivityBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import opsigo.com.datalayer.datanetwork.GetDataMyBooking
import opsigo.com.domainlayer.callback.CallbackDetailMyBooking
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import com.mobile.travelaja.databinding.PurchaseActivityBinding
import opsigo.com.domainlayer.model.my_booking.DetailMyBookingModel
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.module.my_booking.adapter.PriceDetailMyBookingAdapter
import com.mobile.travelaja.module.my_booking.purchase_list_detail.PurchaseDetailListActivity

class PurchaseDetailActivity : BaseActivityBinding<PurchaseActivityBinding>(),
        OnclickListenerRecyclerView,
        ToolbarOpsicorp.OnclickButtonListener{

    override fun bindLayout(): PurchaseActivityBinding {
        return PurchaseActivityBinding.inflate(layoutInflater)
    }

    var dataDetailPurchase = DetailMyBookingModel()
    val data by lazy { ArrayList<Any>() }
    val adapterItem by lazy { PurchaseDetailProductAdapter(this,data) }
    val priceAdapter by lazy { PriceDetailMyBookingAdapter(this) }

    override fun onMain() {
        initToolbar()
        initRecyclerView()
        setInformationTravel()
        Globals.scrollToUp(viewBinding.nestedView)
    }

    private fun setInformationTravel() {
        viewBinding.btnContactUs.setOnClickListener {

        }
        viewBinding.btnSendMessage.setOnClickListener {

        }
        val first = "To better assist you, please give your  "
        val next  = "<font color='#009688'>booking ID</font>"
        val last  = " when you contact to our Customer service, Thank you. "
        viewBinding.tvInfoTravel.text = Html.fromHtml(first + next+last)
    }


    private fun initToolbar() {
        viewBinding.toolbar.hidenBtnCart()

        if (Globals.typeAccomodation=="Flight"||Globals.typeAccomodation=="Train"){
            viewBinding.toolbar.showTitleRoundtripLeft("Surabaya","Jakarta")
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                viewBinding.toolbar.doubleTitleGravity(viewBinding.toolbar.START)
                viewBinding.toolbar.setDoubleTitle("Trans Luxury Bandung","Arjuna, Bandung")
            }
        }
        viewBinding.toolbar.callbackOnclickToolbar(this)
    }

    fun showDetailData(views: View){
        if (viewBinding.btnDropDown.drawable.constantState!!.equals(resources.getDrawable(R.drawable.ic_chevron_up_green).constantState)){
            viewBinding.btnDropDown.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_down_green))
            viewBinding.layDetail.collapse()
        }
        else{
            viewBinding.btnDropDown.setImageDrawable(resources.getDrawable(R.drawable.ic_chevron_up_green))
            viewBinding.layDetail.expand()
        }
    }

    private fun initRecyclerView() {
        viewBinding.rvProductDetail.apply {
            val lm = LinearLayoutManager(this@PurchaseDetailActivity)
            layoutManager = lm
            itemAnimator = DefaultItemAnimator()
            adapter = adapterItem
        }

        val layoutManegePolicy = LinearLayoutManager(this)
        layoutManegePolicy.orientation = LinearLayoutManager.VERTICAL
        viewBinding.rvPrice.layoutManager = layoutManegePolicy
        viewBinding.rvPrice.itemAnimator  = DefaultItemAnimator()
        viewBinding.rvPrice.adapter       = priceAdapter

        adapterItem.setOnclickListener(this)

        getData()
    }

    private fun getData() {
        data.clear()
        showLoadingOpsicorp(false)
        GetDataMyBooking(getBaseUrl()).getDetailMyBooking(getToken(),intent?.getStringExtra(Constants.DATA_SELECT_PURCHASE).toString(),object : CallbackDetailMyBooking{
            override fun success(data: DetailMyBookingModel) {
                hideLoadingOpsicorp()
                dataDetailPurchase = data
                when(data.itemType){
                    Constants.TripType.Airline -> {
                        this@PurchaseDetailActivity.data.addAll(data.dataFlight)
                    }
                    Constants.TripType.KAI -> {
                        this@PurchaseDetailActivity.data.addAll(data.dataTrain)
                    }
                    Constants.TripType.Hotel -> {
                        this@PurchaseDetailActivity.data.add(data.dataHotel)
                    }
                }
                adapterItem.setData(this@PurchaseDetailActivity.data)
                setDataView(data)
            }

            override fun failed(message: String) {
                hideLoadingOpsicorp()
                showAllert(getString(R.string.sorry),message)
            }
        })
    }

    private fun setDataView(data: DetailMyBookingModel) {
        viewBinding.tvPurchaseDate.text = data.purchasedDate
        viewBinding.tvTypePayment.text = data.paymentMethod
        viewBinding.tvTotalPrice.text = "IDR ${Globals.formatAmount(data.totalPaid)}"
        priceAdapter.setData(data.priceDetails)

    }

    override fun onClick(views: Int, position: Int) {
        val bundle = Bundle()
        bundle.putInt(Constants.KEY_POSITION_SELECTED_ITEM,1)
        bundle.putParcelable(Constants.KEY_DATA_PARCELABLE,dataDetailPurchase)
        gotoActivityWithBundle(PurchaseDetailListActivity::class.java,bundle)
    }

    override fun btnBack() {

    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }

}