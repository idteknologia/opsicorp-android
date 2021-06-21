package com.mobile.travelaja.module.item_custom.button_top_filter_approval

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.mobile.travelaja.R
import kotlinx.android.synthetic.main.btn_top_filter_approval.view.*

class ButtonTopFilterApproval : LinearLayout, View.OnClickListener {

    lateinit var onclick : OnclickButtonApproval
    var lines = ArrayList<LinearLayout>()
    var textviews = ArrayList<TextView>()

    fun callbackOnclickFilter(onclickButtonListener: OnclickButtonApproval){
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
        View.inflate(context, R.layout.btn_top_filter_approval, this)

        btn_this_month.setOnClickListener(this)
        btn_last_week.setOnClickListener(this)
        btn_last_month.setOnClickListener(this)

        tv_this_month.setOnClickListener(this)
        tv_last_week.setOnClickListener(this)
        tv_last_month.setOnClickListener(this)

        lines.clear()
        lines.add(btn_this_month)
        lines.add(btn_last_week)
        lines.add(btn_last_month)

        textviews.clear()
        textviews.add(tv_this_month)
        textviews.add(tv_last_week)
        textviews.add(tv_last_month)

    }

    interface OnclickButtonApproval{
        fun thisDay()
        fun onWeek()
        fun onLastMonth()
    }

    override fun onClick(v: View?) {
        if(v==btn_this_month||v==tv_this_month){
            onclick.thisDay()
            changeViewButton(0)
        }
        else if(v==btn_last_week||v==tv_last_week){
            onclick.onWeek()
            changeViewButton(1)
        }
        else if(v==btn_last_month||v==tv_last_month){
            onclick.onLastMonth()
            changeViewButton(2)
        }
    }

    fun setTextAllButton(title:String){
        //tv_all.text = titleHeader
    }

    fun setTextWaitingButton(title:String){
        //tv_waiting.text = titleHeader
    }

    fun setTextApproveButton(title:String){
        //tv_approval.text = titleHeader
    }


    fun firstButtonOn(){
        changeViewButton(0)
    }

    private fun changeViewButton(i: Int) {
        lines.forEachIndexed { index, linearLayout ->
            if(index==i){
                textviews.get(i).setTextColor(resources.getColor(R.color.colorWhite))
                lines.get(i).background = resources.getDrawable(R.drawable.rounded_button_transit_selected)

                val face = Typeface.createFromAsset(context.getAssets(),
                        "font/Roboto_Black.ttf")
                textviews.get(i).typeface = face
            }
            else{
                textviews.get(index).setTextColor(resources.getColor(R.color.colorTextHint))
                lines.get(index).background = resources.getDrawable(R.drawable.rounded_button_filter)

                val face = Typeface.createFromAsset(context.getAssets(),
                        "font/Roboto_Regular.ttf")
                textviews.get(index).typeface = face
            }
        }
    }
}