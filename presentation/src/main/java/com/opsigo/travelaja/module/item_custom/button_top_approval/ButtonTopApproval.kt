package com.opsigo.travelaja.module.item_custom.button_top_approval

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.opsigo.travelaja.R
import kotlinx.android.synthetic.main.btn_top_approval.view.*

class ButtonTopApproval : LinearLayout, View.OnClickListener {

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
        View.inflate(context, R.layout.btn_top_approval, this)

        btn_all.setOnClickListener(this)
        btn_waiting.setOnClickListener(this)
        btn_approve.setOnClickListener(this)
        btn_rejected.setOnClickListener(this)
        btn_expired.setOnClickListener(this)
        tv_all.setOnClickListener(this)
        tv_waiting.setOnClickListener(this)
        tv_expired.setOnClickListener(this)
        tv_approval.setOnClickListener(this)
        tv_rejected.setOnClickListener(this)
        tv_partially_approved.setOnClickListener(this)
        tv_partially_rejected.setOnClickListener(this)

        lines.clear()
        lines.add(btn_all)
        lines.add(btn_waiting)
        lines.add(btn_approve)
        lines.add(btn_rejected)
        lines.add(btn_partially_approved)
        lines.add(btn_partially_rejected)
        lines.add(btn_expired)

        textviews.clear()
        textviews.add(tv_all)
        textviews.add(tv_waiting)
        textviews.add(tv_approval)
        textviews.add(tv_rejected)
        textviews.add(tv_partially_approved)
        textviews.add(tv_partially_rejected)
        textviews.add(tv_expired)

    }

    interface OnclickButtonApproval{
        fun onAll()
        fun onWaiting()
        fun onApproval()
        fun onRejected()
        fun onPartiallyApproved()
        fun onPartiallyRejected()
        fun onExpired()
    }

    override fun onClick(v: View?) {
        if(v==btn_all||v==tv_all){
            onclick.onAll()
            changeViewButton(0)
        }
        else if(v==btn_waiting||v==tv_waiting){
            onclick.onWaiting()
            changeViewButton(1)
        }
        else if(v==btn_approve||v==tv_approval){
            onclick.onApproval()
            changeViewButton(2)
        }
        else if(v==btn_rejected||v==tv_rejected){
            onclick.onRejected()
            changeViewButton(3)
        }
        else if(v==btn_partially_approved||v==tv_partially_approved){
            onclick.onPartiallyApproved()
            changeViewButton(4)
        }
        else if(v==btn_partially_rejected||v==tv_partially_rejected){
            onclick.onPartiallyRejected()
            changeViewButton(5)
        }
        else if(v==btn_expired||v==tv_expired){
            onclick.onExpired()
            changeViewButton(6)
        }
    }

    fun setTextAllButton(title:String){
        tv_all.text = title
    }

    fun setTextWaitingButton(title:String){
        tv_waiting.text = title
    }

    fun setTextApproveButton(title:String){
        tv_approval.text = title
    }

    fun setTextRejectedButton(title:String){
        tv_rejected.text = title
    }

    fun setTextExpiredButton(title:String){
        tv_expired.text = title
    }

    fun setTextPartiallApprovedyButton(title:String){
        tv_partially_approved.text = title
    }

    fun setTextParticiallyRejectedButton(title:String){
        tv_partially_rejected.text = title
    }



    fun allButtonSelected(){
        changeViewButton(0)
    }

    fun waitingButtonSelected(){
        changeViewButton(1)
    }

    fun approvalButtonSelected(){
        changeViewButton(2)
    }

    fun rejectedButtonSelected(){
        changeViewButton(3)
    }

    fun partiallyApprovedButtonSelected(){
        changeViewButton(4)
    }

    fun partiallyRejectedButtonSelected(){
        changeViewButton(5)
    }


    fun expiredSetSelection(){
        changeViewButton(6)
    }

    private fun changeViewButton(i: Int) {
        lines.forEachIndexed { index, linearLayout ->
            if(index==i){
                textviews.get(i).setTextColor(resources.getColor(R.color.colorWhite))
                lines.get(i).background = resources.getDrawable(R.drawable.rounded_button_transit_selected)
                val face = Typeface.createFromAsset(context.getAssets(),
                        "font/Roboto_Regular.ttf")
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