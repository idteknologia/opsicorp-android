package com.mobile.travelaja.module.item_custom.empty_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.mobile.travelaja.R
import androidx.transition.Fade
import androidx.transition.TransitionManager
import android.view.ViewGroup
import kotlinx.android.synthetic.main.loading_layout_view.view.*


class LoadingView : RelativeLayout, View.OnClickListener {

    lateinit var onclick : OnclickButtonListener

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
        View.inflate(context, R.layout.loading_layout_view, this)

        gif.visibility = View.VISIBLE
        gif.gifResource = R.mipmap.ellipsis
        gif.play()
    }

    fun show(){
        val transition = Fade()
        transition.setDuration(600)
        transition.addTarget(R.id.line_loading)
        TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
        line_loading.visibility = View.VISIBLE
    }

    fun showFast(message: String){
        val transition = Fade()
        transition.setDuration(200)
        transition.addTarget(R.id.line_loading)
        TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
        line_loading.visibility = View.VISIBLE
        title2.text = message
    }

    fun isVisible():Boolean{
        return line_loading.visibility==View.VISIBLE
    }

    fun hide(){
        val transition = Fade()
        transition.setDuration(100)
        transition.addTarget(R.id.line_loading)
        TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
        line_loading.visibility = View.GONE
    }


    interface OnclickButtonListener{
        fun onClicked()
    }

    override fun onClick(v: View?) {

    }


}