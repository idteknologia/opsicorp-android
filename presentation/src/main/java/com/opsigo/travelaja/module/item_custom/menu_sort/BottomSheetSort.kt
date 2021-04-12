package com.opsigo.travelaja.module.item_custom.menu_sort

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.opsigo.travelaja.R
import kotlinx.android.synthetic.main.fragment_bottom_sheet_sort.view.*

@SuppressLint("ValidFragment")
class BottomSheetSort(currentSort: Int) : BottomSheetDialogFragment() {

    private var mBottomSheetListener: BottomSheetListener?=null

    var currsort = currentSort
    lateinit var img_lowest_price: ImageView
    lateinit var img_earliest_departure: ImageView
    lateinit var img_latest_departure: ImageView
    lateinit var img_shortest_duration: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_bottom_sheet_sort, container, false)

        img_lowest_price = v.findViewById(R.id.imgLowestPrice)
        img_earliest_departure = v.findViewById(R.id.imgEarliestDeparture)
        img_latest_departure = v.findViewById(R.id.imgLatestDeparture)
        img_shortest_duration = v.findViewById(R.id.imgShortestDuration)

        setCurrentArrow()

        v.tv_close.setOnClickListener {
            dismiss()
        }
        v.lowestPrice.setOnClickListener {
            mBottomSheetListener!!.onOptionSortClick("Lowest Price", 0)
            dismiss()
        }
        v.earliestDeparture.setOnClickListener {
            mBottomSheetListener!!.onOptionSortClick("Earliest Departure", 1)
            dismiss()
        }
        v.latestDeparture.setOnClickListener {
            mBottomSheetListener!!.onOptionSortClick("Latest Departure", 2)
            dismiss()
        }
        v.shortestDuration.setOnClickListener {
            mBottomSheetListener!!.onOptionSortClick("Shortest Duration", 3)
            dismiss()
        }

        return v
    }

    interface BottomSheetListener{
        fun onOptionSortClick(text: String, sort: Int)
    }

    fun setCurrentArrow() {
        img_lowest_price.visibility = View.GONE
        img_earliest_departure.visibility = View.GONE
        img_latest_departure.visibility = View.GONE
        img_shortest_duration.visibility = View.GONE

        if(currsort == 0){
            img_lowest_price.visibility = View.VISIBLE
        }else if(currsort == 1){
            img_earliest_departure.visibility = View.VISIBLE
        }else if(currsort == 2){
            img_latest_departure.visibility = View.VISIBLE
        }else if(currsort == 3){
            img_shortest_duration.visibility = View.VISIBLE
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mBottomSheetListener = context as BottomSheetListener?
        }
        catch (e: ClassCastException){
            throw ClassCastException(context.toString())
        }
    }
}