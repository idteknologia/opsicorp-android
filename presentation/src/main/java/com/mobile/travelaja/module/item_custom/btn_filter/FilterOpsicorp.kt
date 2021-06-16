package com.mobile.travelaja.module.item_custom.btn_filter

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.mobile.travelaja.R
import kotlinx.android.synthetic.main.layout_filter_search_flight.view.*

class FilterOpsicorp : LinearLayout, View.OnClickListener {

    lateinit var onclick : OnclickFilterListener
    var images = ArrayList<ImageView>()
    var textNames = ArrayList<TextView>()
    val drawablesSelected = ArrayList<Int>()
    val drawablesDefault = ArrayList<Int>()

    fun callbackOnclickFilter(onclickButtonListener: OnclickFilterListener){
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
        View.inflate(context, R.layout.layout_filter_search_flight, this)

        btn_filter.setOnClickListener(this)
        tv_filter.setOnClickListener(this)
        btn_sort.setOnClickListener(this)
        tv_sort.setOnClickListener(this)
        btn_change_date.setOnClickListener(this)
        tv_change_date.setOnClickListener(this)
        ic_short.setOnClickListener(this)
        ic_filter.setOnClickListener(this)
        ic_change_date.setOnClickListener(this)

        images.clear()
        images.add(ic_filter)
        images.add(ic_short)
        images.add(ic_change_date)

        textNames.clear()
        textNames.add(tv_filter)
        textNames.add(tv_sort)
        textNames.add(tv_change_date)

        drawablesSelected.add(R.drawable.ic_filter_green)
        drawablesSelected.add(R.drawable.ic_sort_green)
        drawablesSelected.add(R.drawable.ic_transparant)

        drawablesDefault.add(R.drawable.ic_filter_grey)
        drawablesDefault.add(R.drawable.ic_sort_grey)
        drawablesDefault.add(R.drawable.ic_transparant)
    }

    fun setTextButtonChangeDate(name:String){
        tv_change_date.text  = name
    }

    interface OnclickFilterListener{
        fun onFilter()
        fun onSort()
        fun onChangeDate()
    }

    override fun onClick(v: View?) {
        when(v){

        }
        if(v==btn_filter||v==tv_filter||v==ic_filter){
            onclick.onFilter()
            changeViewButton(0)
        }
        else if(v==btn_sort||v==tv_sort||v==ic_short){
            changeViewButton(1)
            onclick.onSort()
        }
        else if(v==btn_change_date||v==tv_change_date||v==ic_change_date){
            onclick.onChangeDate()
            changeViewButton(2)

        }
    }

    private fun changeViewButton(i: Int) {
        textNames.forEachIndexed { index, text ->
            if(index==i){
                images[index].setImageDrawable(resources.getDrawable(drawablesSelected[i]))
                text.setTextColor(resources.getColor(R.color.active_icon))
            }
            else{
                images[index].setImageDrawable(resources.getDrawable(drawablesDefault[index]))
                text.setTextColor(resources.getColor(R.color.gray_50_subtitle))
            }
        }
    }

}