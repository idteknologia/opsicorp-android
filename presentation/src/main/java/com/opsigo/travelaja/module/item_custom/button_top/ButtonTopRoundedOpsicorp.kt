package com.opsigo.travelaja.module.item_custom.button_top

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.opsigo.travelaja.R
import kotlinx.android.synthetic.main.button_right_left_rounded.view.*

class ButtonTopRoundedOpsicorp : LinearLayout, View.OnClickListener {

    lateinit var onclick : OnclickButtonListener

    var lineButtons = ArrayList<LinearLayout>()
    var lineSelected = ArrayList<Int>()
    var lineDefault = ArrayList<Int>()
    var textButtons = ArrayList<TextView>()

    fun callbackOnclickToolbar(onclickButtonListener: OnclickButtonListener){
        onclick = onclickButtonListener
        onclick.btnLeft()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        setOrientation(VERTICAL);
        View.inflate(context, R.layout.button_right_left_rounded, this)

        line_button_left.setOnClickListener(this)
        line_button_right.setOnClickListener(this)
        title_btn_left.setOnClickListener(this)
        title_button_right.setOnClickListener(this)

        lineButtons.clear()
        lineDefault.clear()
        lineSelected.clear()

        lineButtons.add(line_button_left)
        lineButtons.add(line_button_right)
        textButtons.add(title_btn_left)
        textButtons.add(title_button_right)
        lineDefault.add(R.drawable.rounded_button_up_flight_selected_left)
        lineDefault.add(R.drawable.rounded_button_up_flight_selected_right)
        lineSelected.add(R.drawable.rounded_button_up_flight_default_left)
        lineSelected.add(R.drawable.rounded_button_up_flight_default_right)
    }

    fun setTextBtnLeft(text:String){
        title_btn_left.text = text
    }

    fun setTextBtnRight(text: String){
        title_button_right.text = text
    }


    interface OnclickButtonListener{
        fun btnLeft()
        fun btnRight()
    }

    override fun onClick(v: View?) {

        if(v==line_button_left||v==title_btn_left){
            changeImageBtn(0)
            onclick.btnLeft()
        }
        else if(v==line_button_right||v==title_button_right){
            changeImageBtn(1)
            onclick.btnRight()
        }
    }

    private fun changeImageBtn(position:Int) {

        lineButtons.forEachIndexed { index, lineView ->
            if (index==position){
                lineView.background = (resources.getDrawable(lineSelected[position]))
                textButtons.get(index).setTextColor(resources.getColor(R.color.green_price))

                val face = Typeface.createFromAsset(context.getAssets(),
                        "font/Roboto_Black.ttf")
                textButtons.get(index).typeface = face
            }
            else{
                lineView.background = (resources.getDrawable(lineDefault[index]))
                textButtons.get(index).setTextColor(resources.getColor(R.color.white))
                val face = Typeface.createFromAsset(context.getAssets(),
                        "font/Roboto_Regular.ttf")
                textButtons.get(index).typeface = face
            }
        }
    }

}