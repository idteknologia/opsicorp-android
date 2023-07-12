package com.opsicorp.hotel_feature.result

import android.content.Intent
import com.opsicorp.hotel_feature.R
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.utility.NumberTextWatcher
import kotlinx.android.synthetic.main.activity_filter_price.*
import com.opsicorp.hotel_feature.adapter.FilterRatingAdapter
import com.opsicorp.hotel_feature.adapter.FilterFacilityAdapter
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import opsigo.com.domainlayer.model.accomodation.hotel.StartSelected
import opsigo.com.domainlayer.model.accomodation.hotel.FacilityHotelModel
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp

class FilterPriceActivity : BaseActivity(),
        ButtonDefaultOpsicorp.OnclickButtonListener,
        ToolbarOpsicorp.OnclickButtonListener {

    var minPrice = 0
    var maxPrice = 0
    var rating = ""

    val dataFacility    = ArrayList<FacilityHotelModel>()
    val dataRating      = ArrayList<StartSelected>()
    val adapterFacility = FilterFacilityAdapter(this,dataFacility)
    val adapterRating   = FilterRatingAdapter(this,dataRating)

    override fun getLayout(): Int { return R.layout.activity_filter_price }

    override fun OnMain() {
        initToolbar()
        initRecyclerView()
        setDataListener()
        setOnclikcListener()
    }

    private fun setOnclikcListener() {
        adapterFacility.setOnclickListener(object : OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                when(views){
                    -1 -> {
                        dataFacility[position].active = !dataFacility[position].active
                        adapterFacility.notifyDataSetChanged()
                    }
                }
            }
        })

        adapterRating.setOnclickListener(object : OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                when(views){
                    -1 -> {
                        /*dataRating.forEachIndexed { index, startSelected ->
                            startSelected.selected = index==position
                        }*/
                        rating = dataRating[position].rating.toString()
//                        dataRating[position].selected = !dataRating[position].selected
                        adapterRating.notifyDataSetChanged()
                    }
                }
            }
        })

        et_min.addTextChangedListener(NumberTextWatcher(et_min))
        et_max.addTextChangedListener(NumberTextWatcher(et_max))

        /*et_min.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty()){
                    minPrice = p0.toString().toInt()
                }
                else {
                    minPrice = 0
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        et_max.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty()){
                    maxPrice = p0.toString().toInt()
                }
                else {
                    maxPrice = 0
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })*/



        btn_submit.callbackOnclickButton(this)
        btn_submit.setTextButton("Apply")
    }

    private fun setDataListener() {
        minPrice = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getInt(Constants.MIN_PRICE,0)!!
        maxPrice = intent?.getBundleExtra(Constants.KEY_BUNDLE)?.getInt(Constants.MAX_PRICE,0)!!
//        tv_min_max.text = "${Globals.formatAmount(minPrice.toString())} - ${Globals.formatAmount(maxPrice.toString())}"

        for (i in 5 downTo  1){
            dataRating.add(StartSelected(i,false))
        }

        dataFacility.add(FacilityHotelModel("AC","AC",R.drawable.ac,false))
        dataFacility.add(FacilityHotelModel("RST","Meal",R.drawable.ic_meal,false))
        dataFacility.add(FacilityHotelModel("POOL","Water Pool",R.drawable.ic_swimming_pool,false))
        dataFacility.add(FacilityHotelModel("PRK","Parking",R.drawable.ic_parkir,false))
        dataFacility.add(FacilityHotelModel("RSVC","Contact Center",R.drawable.ic_contact_center,false))
        dataFacility.add(FacilityHotelModel("WIFI","WiFi",R.drawable.ic_wi_fi,false))
        dataFacility.add(FacilityHotelModel("NOSMR","No Smoking Area",R.drawable.ic_no_smoking,false))
        dataFacility.add(FacilityHotelModel("SDBOX","Safe Deposit Box",R.drawable.ic_safe_deposit_box,false))


        adapterFacility.setData(dataFacility)
        adapterRating.setData(dataRating)
    }

    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_facility.layoutManager = layoutManager
        rv_facility.itemAnimator  = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_facility.adapter       = adapterFacility

        val layoutManagerRating = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManagerRating.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_star_rating.layoutManager = layoutManagerRating
        rv_star_rating.itemAnimator  = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_star_rating.adapter       = adapterRating
    }

    override fun onClicked() {
        try {

            parsingDataMinMax()
            if (minPrice!=0||maxPrice!=0){
                if (minPrice>=maxPrice){
                    Globals.showAlert("Sorry","Please input larger amount than minimal price",this)
                }else {
                    setDataIntent()
                }
            }else {
                setDataIntent()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun setDataIntent() {
        val selected = Intent()
        selected.putExtra(Constants.RESULT_FACILITY,facilityCode())
        /*selected.putExtra(Constants.RESULT_STAR,starSelected())*/
        selected.putExtra(Constants.RESULT_STAR,rating)
        selected.putExtra(Constants.MIN_PRICE,minPrice)
        selected.putExtra(Constants.MAX_PRICE,maxPrice)
        setLog("$minPrice - $maxPrice - $rating")
        Globals.finishResultOk(this,selected)
    }

    private fun parsingDataMinMax() {
        minPrice = if (et_min.text.toString().isNotEmpty()) et_min.text.toString().replace(",","").replace(".","").toInt() else 0
        maxPrice = if (et_max.text.toString().isNotEmpty()) et_max.text.toString().replace(",","").replace(".","").toInt() else 0
    }

    private fun starSelected(): ArrayList<String> {
        val mData = ArrayList<String>()
        if (!dataRating.filter { it.selected }.isNullOrEmpty()){
            dataRating.filter { it.selected }.forEach {
                mData.add(it.rating.toString())
            }
        }
//        mData.add(dataRating[adapterFacility.checkedPosition].rating.toString())
        return mData
    }

    private fun facilityCode(): ArrayList<String> {
        val mData = ArrayList<String>()
        if (!dataFacility.filter { it.active }.isNullOrEmpty()){
            dataFacility.filter { it.active }.forEach {
                mData.add(it.code)
            }
        }
        return mData
    }

    private fun initToolbar() {
        toolbar.callbackOnclickToolbar(this)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
        toolbar.hidenBtnCart()
        toolbar.setTitleBar("Filter")
        toolbar.changeImageBtnBack(com.mobile.travelaja.R.drawable.ic_close_white)
    }

    override fun btnBack() {
        finish()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }
}