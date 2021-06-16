package com.opsigo.travelaja.module.item_custom.button_default

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.opsigo.travelaja.R
import kotlinx.android.synthetic.main.btn_default.view.*
import java.lang.Exception

class ButtonDefaultOpsicorp : LinearLayout, View.OnClickListener {

    lateinit var onclick : OnclickButtonListener

    var mTitle = ""
//    lateinit var backgroundButton : Drawable

    fun callbackOnclickButton(onclickButtonListener: OnclickButtonListener){
        onclick = onclickButtonListener
    }

    constructor(context: Context) : super(context) {
        init()
    }

//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
//        init()
//    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : super(context, attrs, defStyle) {

        val a = context.obtainStyledAttributes(attrs, R.styleable.ButtonDefaultOpsicorp, defStyle, 0)

        try {
            mTitle = a.getString(R.styleable.ButtonDefaultOpsicorp_text).toString()
//            backgroundButton = a.getDrawable(R.styleable.ButtonDefaultOpsicorp_backgroundButton)
        }catch (e:Exception){
            mTitle = ""
        }

        a.recycle()

        init()
    }


    private fun init() {
        setOrientation(VERTICAL)
        View.inflate(context, R.layout.btn_default, this)

        if(mTitle.isNotEmpty()){
            setTextButton(mTitle)
        }
//        try {
//            lay_btn.setBackgroundDrawable(backgroundButton)
//        }catch (e:Exception){ e.printStackTrace() }

        tv_button.setOnClickListener(this)
        lay_btn.setOnClickListener(this)
    }


    interface OnclickButtonListener{
        fun onClicked()
    }

    override fun onClick(v: View?) {
        if(v==lay_btn||v==tv_button){
            onclick.onClicked()
        }
    }

    fun setTextButton(string: String){
        tv_button.text = string
    }

    fun changeBackground(color:Int){
        lay_btn.setBackgroundColor(resources.getColor(color))
    }


    fun changeBackgroundDrawable(drawable:Int){
        lay_btn.setBackgroundDrawable(resources.getDrawable(drawable))
    }

    fun changeTextColorButton(color:Int){
        tv_button.setTextColor(resources.getColor(color))
    }
}