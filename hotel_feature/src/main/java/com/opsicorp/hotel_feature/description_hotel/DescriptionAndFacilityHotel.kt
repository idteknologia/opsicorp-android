package com.opsicorp.hotel_feature.description_hotel

import android.view.View
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.opsicorp.hotel_feature.R
import com.opsigo.travelaja.utility.Globals
import com.google.android.material.tabs.TabLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import opsigo.com.domainlayer.model.accomodation.hotel.FacilityHotelModel
import kotlinx.android.synthetic.main.description_and_facility_hotel.view.*
import kotlinx.android.synthetic.main.prize_layout_hotel.view.*
import opsigo.com.domainlayer.model.accomodation.hotel.ResultListHotelModel


class DescriptionAndFacilityHotel : LinearLayout, View.OnClickListener {

    lateinit var onclick : OnclickButtonListener

    val data = ArrayList<FacilityHotelModel>()
    val adapter by lazy { ListFacilityHotelAdapter(context, data) }

    fun callbackOnclickButton(onclickButtonListener: OnclickButtonListener){
        onclick = onclickButtonListener
    }

    constructor(context: Context) : super(context) {
        init()
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        setOrientation(VERTICAL)
        View.inflate(context, R.layout.description_and_facility_hotel, this)

        initTablayout()
        showFacility()
    }

    fun initLinePricing(data : ResultListHotelModel) {
        tv_prize_hotel.text = "IDR "+ Globals.formatAmount(data.price)
        btn_next_step.setOnClickListener(this)

        btn_next_step.setTextColor(resources.getColor(R.color.colorBlueTitle))
        tv_available_room.visibility = View.GONE

        /*if (data.totalAvailable.toInt()!=0){
            btn_next_step.setTextColor(resources.getColor(R.color.colorBlueTitle))
            tv_available_room.visibility = View.GONE
        }
        else{
            tv_available_room.text = "No available room"
            tv_available_room.visibility = View.VISIBLE
            btn_next_step.setTextColor(resources.getColor(R.color.colorBlueTitle))
        }*/
    }

    private fun initTablayout() {
        tablayout.addTab(tablayout.newTab().setText("All Facilities"))
        tablayout.addTab(tablayout.newTab().setText("Description"))

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tabLayout: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(tabLayout: TabLayout.Tab?) {
                when (tabLayout?.position){
                    0 -> showFacility()
                    1 -> showDescription()
                }

            }
        })

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val mLayoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 2)
        rv_facility.layoutManager = mLayoutManager
        rv_facility.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_facility.hasFixedSize()
        rv_facility.adapter = adapter
    }

    fun setDataListFacility(mData:ArrayList<FacilityHotelModel>){
        data.clear()
        data.addAll(mData)
        adapter.setData(data)
    }

    fun setDescriptions(description:String){
        tv_description.text = description
    }

    fun showFacility(){
        rv_facility.visibility      = View.VISIBLE
        tv_description.visibility = View.GONE
    }

    fun showDescription(){
        rv_facility.visibility      = View.GONE
        tv_description.visibility = View.VISIBLE
    }

    interface OnclickButtonListener{
        fun nextStep()
    }

    override fun onClick(v: View?) {
        when (v){
            btn_next_step -> {
                onclick.nextStep()
            }
        }
    }

}