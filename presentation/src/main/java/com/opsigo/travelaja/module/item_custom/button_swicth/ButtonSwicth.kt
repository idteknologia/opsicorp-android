package com.opsigo.travelaja.module.item_custom.button_swicth

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.opsigo.travelaja.R
import kotlinx.android.synthetic.main.btn_swich.view.*

class ButtonSwicth : LinearLayout, View.OnClickListener {

    lateinit var onclickSwicth : OnclickButtonSwitch
    lateinit var mEditText: TextView
    lateinit var mEditText2: TextView

    fun callbackOnclickButtonSwicht(onclickButtonSwicth: OnclickButtonSwitch){
        onclickSwicth = onclickButtonSwicth
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    fun setItemSwicth(editText: TextView,editText2: TextView){
        this.mEditText = editText
        this.mEditText2 = editText2
    }

    private fun init() {
        setOrientation(VERTICAL)
        View.inflate(context, R.layout.btn_swich, this)

        img_switch.setOnClickListener(this)
        line_switch.setOnClickListener(this)
    }


    interface OnclickButtonSwitch{
        fun onSwitch()
    }

    override fun onClick(v: View?) {
        if(v==img_switch||v==line_switch){
            onclickSwicth.onSwitch()
            swicth()
        }
    }

    fun swicth(){
        var string = ""
        var stringHint = ""
        string = mEditText.text.toString()
        stringHint = mEditText.text.toString()
        mEditText.setText(mEditText2.text.toString())
        mEditText.setHint(mEditText2.text.toString())
        mEditText2.setText(string)
        mEditText2.setHint(stringHint)
    }


}