package com.opsigo.travelaja.module.item_custom.description_hotel

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.opsigo.travelaja.R
import android.support.design.widget.TabLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import com.opsigo.travelaja.utility.Globals
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
        tv_prize_hotel.text = "IDR "+ Globals.formatAmount(data.prize)
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
        val mLayoutManager = GridLayoutManager(context,2)
        rv_facility.layoutManager = mLayoutManager
        rv_facility.itemAnimator = DefaultItemAnimator()
        rv_facility.hasFixedSize()
        rv_facility.adapter = adapter
    }

    fun setDataListFacility(mData:ArrayList<FacilityHotelModel>){
        data.clear()
        data.addAll(mData.filter { it.image.isEmpty() })
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