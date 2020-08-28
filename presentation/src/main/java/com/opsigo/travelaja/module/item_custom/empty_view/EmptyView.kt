package com.opsigo.travelaja.module.item_custom.empty_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.opsigo.travelaja.R
import kotlinx.android.synthetic.main.empty_layout.view.*
import android.support.transition.Fade
import android.support.transition.TransitionManager
import android.view.ViewGroup


class EmptyView : RelativeLayout, View.OnClickListener {

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
        View.inflate(context, R.layout.empty_layout, this)

        image_empty.setImageDrawable(resources.getDrawable(R.drawable.no_list_approval))
    }

    fun show(){
        val transition = Fade()
        transition.setDuration(600)
        transition.addTarget(R.id.line_empty)
        TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
        line_empty.visibility = View.VISIBLE
    }

    fun showFast(message: String){
        val transition = Fade()
        transition.setDuration(200)
        transition.addTarget(R.id.line_empty)
        TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
        line_empty.visibility = View.VISIBLE
        tv_message_second.text = message
    }

    fun isVisible():Boolean{
        return line_empty.visibility==View.VISIBLE
    }

    fun hide(){
        val transition = Fade()
        transition.setDuration(100)
        transition.addTarget(R.id.line_empty)
        TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
        line_empty.visibility = View.GONE
    }


    interface OnclickButtonListener{
        fun onClicked()
    }

    override fun onClick(v: View?) {

    }


}