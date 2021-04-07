package com.opsigo.travelaja.module.accomodation.view_accomodation.fragment.tour

import android.view.View
import android.os.Bundle
import com.opsigo.travelaja.R
import com.squareup.picasso.Picasso
import com.opsigo.travelaja.base.BaseFragment
import kotlinx.android.synthetic.main.tour_fragment.*

//import android.support.v7.widget.DefaultItemAnimator
//import android.support.v7.widget.LinearLayoutManager
//import kotlinx.android.synthetic.main.tour_fragment.*
//import com.khoiron.module_tour.model.DataTourEventDummy
//import com.khoiron.module_tour.model.TourEventParentModel
//import com.khoiron.module_tour.adapter.TourEventParentAdapter
//import com.opsigo.travelaja.utility.OnclickListenerRecyclerViewParent

class TourFragment : BaseFragment() {

    override fun getLayout(): Int { return R.layout.tour_fragment }

//    val data by lazy { ArrayList<TourEventParentModel>() }
//    val adapter by lazy { TourEventParentAdapter(context!!,data) }

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        setImageHeader()
//        setInitRecyclerView()
//        addDataDummy()
    }

//    private fun addDataDummy() {
//        data.clear()
//        data.addAll(DataTourEventDummy().dataDummy())
//        adapter.setData(data)
//    }
//
//    private fun setInitRecyclerView() {
//        val layoutManager = LinearLayoutManager(context)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        rv_tour_travel_parent.layoutManager = layoutManager
//        rv_tour_travel_parent.itemAnimator = DefaultItemAnimator()
//        rv_tour_travel_parent.adapter = adapter
//
//        adapter.setOnclickListener(object :OnclickListenerRecyclerViewParent{
//            override fun onClick(viewsParent: Int, positionParent: Int, viewsChild: Int, positionChild: Int) {
//
//            }
//        })
//    }

    private fun setImageHeader() {
        val image = "https://cdn.zeplin.io/5cff7f4b6fea415dc8c58d99/assets/DCCF7C4B-5D18-4C6E-8DD0-EC142FD7039E.png"
        Picasso.get()
                .load(image)
                .fit()
                .centerCrop()
                .into(image_header)
    }

}