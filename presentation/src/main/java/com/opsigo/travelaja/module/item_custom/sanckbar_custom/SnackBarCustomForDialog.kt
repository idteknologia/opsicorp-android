package com.opsigo.travelaja.module.item_custom.sanckbar_custom

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.opsigo.travelaja.R
import kotlinx.android.synthetic.main.snackbar_layout_error_adult.view.*
import android.view.animation.TranslateAnimation



class SnackBarCustomForDialog : LinearLayout, View.OnClickListener {

    lateinit var onclick : OnclickButtonListener

    var isUp   =  false
    lateinit var myView: RelativeLayout

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
        View.inflate(context, R.layout.snackbar_layout_error_adult, this)

        myView = parent_layout_snackbar
        myView.visibility = View.INVISIBLE

        parent_layout_snackbar.setOnClickListener(this)

    }

    fun showSnackBar(){
        onShowView()
//        Handler().postDelayed({
//            onShowView()
//        }, 3000)
    }

    // slide the myView from below itself to the current position
    fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
                0f, // fromXDelta
                0f, // toXDelta
                view.height.toFloat(), // fromYDelta
                0f)            // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    // slide the myView from its current position to below itself
    fun slideDown(view: View) {
        val animate = TranslateAnimation(
                0f, // fromXDelta
                0f, // toXDelta
                0f, // fromYDelta
                view.height.toFloat()) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
        Handler().postDelayed({
            view.visibility = View.GONE
        }, 500)
    }

    fun onShowView() {
        if (isUp) {
            slideDown(myView)
        } else {
            slideUp(myView)
        }
        isUp = !isUp
    }

    interface OnclickButtonListener{
        fun onClicked()
    }

    override fun onClick(v: View?) {
        if (parent_layout_snackbar==v){
            onShowView()
        }
    }

    fun setTextMessage(string: String){
        textError.text = string
    }

}