package com.mobile.travelaja.module.item_custom.btn_filter

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mobile.travelaja.R
import kotlinx.android.synthetic.main.layout_filter_transit.view.*

class FilterTransitOpsicorp : LinearLayout, View.OnClickListener {

    lateinit var onclick : OnclickFilterListener
    var lines = ArrayList<LinearLayout>()
    var textviews = ArrayList<TextView>()

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
        View.inflate(context, R.layout.layout_filter_transit, this)

        btn_direct.setOnClickListener(this)
        tv_direct.setOnClickListener(this)
        btn_one_transit.setOnClickListener(this)
        tv_one_transit.setOnClickListener(this)
        btn_two_transit.setOnClickListener(this)
        tv_two_transit.setOnClickListener(this)

        lines.clear()
        lines.add(btn_direct)
        lines.add(btn_one_transit)
        lines.add(btn_two_transit)

        textviews.clear()
        textviews.add(tv_direct)
        textviews.add(tv_one_transit)
        textviews.add(tv_two_transit)

    }

    interface OnclickFilterListener{
        fun onDirect()
        fun onOneTransit()
        fun onTwoTransit()
    }

    override fun onClick(v: View?) {
        if(v==btn_direct||v==tv_direct){
            onDirectSeleted()
        }
        else if(v==btn_one_transit||v==tv_one_transit){
            oneTransitSeleted()
        }
        else if(v==btn_two_transit||v==tv_two_transit){
            twoTransitSeleted()
        }
    }

    fun twoTransitSeleted() {
        onclick.onTwoTransit()
        changeViewButton(2)
    }

    fun oneTransitSeleted() {
        changeViewButton(1)
        onclick.onOneTransit()
    }

    fun onDirectSeleted(){
        onclick.onDirect()
        changeViewButton(0)
    }

    private fun changeViewButton(i: Int) {
        lines.forEachIndexed { index, linearLayout ->
            if(index==i){
                if (linearLayout.background.constantState==ContextCompat.getDrawable(context,R.drawable.rounded_button_transit_selected)?.constantState){
                    linearLayout.background = ContextCompat.getDrawable(context,R.drawable.rounded_button_filter)
                    textviews.get(index).setTextColor(resources.getColor(R.color.colorTextHint))
                }
                else {
                    linearLayout.background = ContextCompat.getDrawable(context,R.drawable.rounded_button_transit_selected)
                    textviews.get(index).setTextColor(resources.getColor(R.color.colorWhite))
                }
            }
        }
    }

    fun resetSelected() {
        lines.forEachIndexed { index, linearLayout ->
            linearLayout.background = resources.getDrawable(R.drawable.rounded_button_filter)
            textviews.get(index).setTextColor(resources.getColor(R.color.colorTextHint))
        }
    }

    fun getIsActivatedButton():ArrayList<Int>{
        val dataActivited = ArrayList<Int>()
        lines.forEachIndexed { index, linearLayout ->
            if (linearLayout.background.constantState==ContextCompat.getDrawable(context,R.drawable.rounded_button_transit_selected)?.constantState){
                dataActivited.add(index)
            }
        }
        return dataActivited
    }

}