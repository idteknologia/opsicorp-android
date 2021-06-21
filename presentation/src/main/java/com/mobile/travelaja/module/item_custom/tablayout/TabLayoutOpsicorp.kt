package com.mobile.travelaja.module.item_custom.tablayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.tab_layout.view.*

class TabLayoutOpsicorp: LinearLayout {

    lateinit var onclick : OnclickButtonListener
    var sizeText  = 0
    var colorText = 0
    var colorLineSelected = 0
    var colorLineDefault = 0
    var sizeLineSelected  = 0
    var sizeLineDefault   = 0

    fun callbackOnclickButton(onclickButtonListener: OnclickButtonListener){
        onclick = onclickButtonListener
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }


    private fun init() {
        setOrientation(VERTICAL)
        View.inflate(context, R.layout.tab_layout, this)

    }

    fun buildTabLayout(mData : ArrayList<String>){
        data.clear()
        data.addAll(mData)
        initRecyclerView()
    }

    var data = ArrayList<String>()
    var adapter = TabOpsicorpAdapter(context, data)

    private fun initRecyclerView() {
        val mLayoutManager = androidx.recyclerview.widget.GridLayoutManager(context, data.size)
        rv_tab.layoutManager = mLayoutManager
        rv_tab.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_tab.hasFixedSize()

        adapter.setColor(colorText)
        adapter.setSize(sizeText)
        adapter.setSizeLinesSeledted(sizeLineSelected)
        adapter.setSizeLinesDefault(sizeLineDefault)
        adapter.setColorLinesSelected(colorLineSelected)
        adapter.setColorLinesDefault(colorLineDefault)
        rv_tab.adapter = adapter

        adapter.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                adapter.notifyDataSetChanged()
                onclick.onClicked(position,data[position])
            }
        })


        adapter.setData(data)

    }


    interface OnclickButtonListener{
        fun onClicked(positionTab: Int,name:String)
    }

}