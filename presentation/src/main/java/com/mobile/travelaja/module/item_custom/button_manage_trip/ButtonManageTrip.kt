package com.mobile.travelaja.module.item_custom.button_manage_trip

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.Globals.getBaseUrl
import com.mobile.travelaja.utility.gone
import com.mobile.travelaja.utility.visible
import kotlinx.android.synthetic.main.button_right_left_rounded.view.*

class ButtonManageTrip : LinearLayout, View.OnClickListener {

    lateinit var onclick : OnclickButtonApproval2

    var lineButtons = ArrayList<LinearLayout>()
    var lineSelected = ArrayList<Int>()
    var lineDefault = ArrayList<Int>()
    var textButtons = ArrayList<TextView>()

    fun callbackOnclickFilter(onclickButtonListener: OnclickButtonApproval2){
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
        View.inflate(context, R.layout.button_right_left_rounded, this)

        if (getBaseUrl(context) == "https://dtmqa.opsinfra.net/") {
            ll_master_button.gone()
        } else {
            ll_master_button.visible()
        }

        line_button_left.setOnClickListener(this)
        line_button_right.setOnClickListener(this)
        title_btn_left.setOnClickListener(this)
        title_button_right.setOnClickListener(this)

        setTextBtnLeft("Draft")
        setTextBtnRight("Approved")

        lineButtons.clear()
        lineDefault.clear()
        lineSelected.clear()

        lineButtons.add(line_button_left)
        lineButtons.add(line_button_right)
        textButtons.add(title_btn_left)
        textButtons.add(title_button_right)
        lineDefault.add(R.drawable.rounded_button_up_flight_default_left)
        lineDefault.add(R.drawable.rounded_button_up_flight_default_right)
        lineSelected.add(R.drawable.rounded_button_up_flight_selected_left)
        lineSelected.add(R.drawable.rounded_button_up_flight_selected_right)

    }

    fun setTextBtnLeft(text:String){
        title_btn_left.text = text
    }

    fun setTextBtnRight(text: String){
        title_button_right.text = text
    }

    interface OnclickButtonApproval2{
        fun onDraft()
        fun onCompleted()
    }

    override fun onClick(v: View?) {

        if(v==line_button_left||v==title_btn_left){
            onclick.onDraft()
            changeImageBtn(0)
        }
        else if(v==line_button_right||v==title_button_right){
            onclick.onCompleted()
            changeImageBtn(1)
        }
    }

    private fun changeImageBtn(position:Int) {

        lineButtons.forEachIndexed { index, lineView ->
            if (index==position){
                lineView.background = (resources.getDrawable(lineSelected[position]))
                textButtons.get(index).setTextColor(resources.getColor(R.color.colorWhite))

                val face = Typeface.createFromAsset(context.getAssets(),
                        "font/Roboto_Black.ttf")
                textButtons.get(index).typeface = face
            }
            else{
                lineView.background = (resources.getDrawable(lineDefault[index]))
                textButtons.get(index).setTextColor(resources.getColor(R.color.colorGrayTextButtonFlight))
                val face = Typeface.createFromAsset(context.getAssets(),
                        "font/Roboto_Regular.ttf")
                textButtons.get(index).typeface = face
            }
        }
    }

    fun draftButtonSelected(){
        changeImageBtn(0)
    }

    fun completedButtonSelected(){
        changeImageBtn(1)
    }



//    override fun onClick(v: View?) {
//
////        if(v==btn_all||v==tv_all){
////            onclick.onAll()
////            changeViewButton(0)
////        }
//
////        if(v==btn_all||v==tv_all){
////            onclick.onAll()
////            changeViewButton(0)
////        }
////        else if(v==btn_waiting||v==tv_waiting){
////            onclick.onWaiting()
////            changeViewButton(1)
////        }
////        else if(v==btn_approve||v==tv_approval){
////            onclick.onApproval()
////            changeViewButton(2)
////        }
////        else if(v==btn_rejected||v==tv_rejected){
////            onclick.onRejected()
////            changeViewButton(3)
////        }
////        else if(v==btn_partially_approved||v==tv_partially_approved){
////            onclick.onPartiallyApproved()
////            changeViewButton(4)
////        }
////        else if(v==btn_partially_rejected||v==tv_partially_rejected){
////            onclick.onPartiallyRejected()
////            changeViewButton(5)
////        }
////        else if(v==btn_expired||v==tv_expired){
////            onclick.onExpired()
////            changeViewButton(6)
////        }
////        else if(v==btn_draft1||v==tv_draft2){
////            onclick.onExpired()
////            changeViewButton(7)
////        }
//    }
//
////    fun setTextAllButton(title:String){
////        tv_all.text = title
////    }
////
////    fun setTextWaitingButton(title:String){
////        tv_waiting.text = title
////    }
////
////    fun setTextApproveButton(title:String){
////        tv_approval.text = title
////    }
////
////    fun setTextRejectedButton(title:String){
////        tv_rejected.text = title
////    }
////
////    fun setTextExpiredButton(title:String){
////        tv_expired.text = title
////    }
////
////    fun setTextPartiallApprovedyButton(title:String){
////        tv_partially_approved.text = title
////    }
////
////    fun setTextParticiallyRejectedButton(title:String){
////        tv_partially_rejected.text = title
////    }
//
//
//
////    fun allButtonSelected(){
////        changeViewButton(0)
////    }
////
////    fun waitingButtonSelected(){
////        changeViewButton(1)
////    }
////
////    fun approvalButtonSelected(){
////        changeViewButton(2)
////    }
////
////    fun rejectedButtonSelected(){
////        changeViewButton(3)
////    }
////
////    fun partiallyApprovedButtonSelected(){
////        changeViewButton(4)
////    }
////
////    fun partiallyRejectedButtonSelected(){
////        changeViewButton(5)
////    }
////
////    fun expiredSetSelection(){
////        changeViewButton(6)
////    }
////
////    fun draftSetSelection(){
////        changeViewButton(7)
////    }
//
////    private fun changeViewButton(i: Int) {
////        lines.forEachIndexed { index, linearLayout ->
////            if(index==i){
////                textviews.get(i).setTextColor(resources.getColor(R.color.colorWhite))
////                lines.get(i).background = resources.getDrawable(R.drawable.rounded_button_transit_selected)
////                val face = Typeface.createFromAsset(context.getAssets(),
////                        "font/Roboto_Regular.ttf")
////                textviews.get(i).typeface = face
////            }
////            else{
////                textviews.get(index).setTextColor(resources.getColor(R.color.colorTextHint))
////                lines.get(index).background = resources.getDrawable(R.drawable.rounded_button_filter)
////                val face = Typeface.createFromAsset(context.getAssets(),
////                        "font/Roboto_Regular.ttf")
////                textviews.get(index).typeface = face
////            }
////        }
////    }
//
//    fun allButtonSelected(){
//        changeImageBtn(0)
//    }
//
//    fun waitingButtonSelected(){
//        changeImageBtn(1)
//    }
//
//    private fun changeImageBtn(position:Int) {
//
////        lineButtons.forEachIndexed { index, lineView ->
////            if (index==position){
////                lineView.background = (resources.getDrawable(lineSelected[position]))
////                textButtons.get(index).setTextColor(resources.getColor(R.color.colorWhite))
////
////                val face = Typeface.createFromAsset(context.getAssets(),
////                        "font/Roboto_Black.ttf")
////                textButtons.get(index).typeface = face
////            }
////            else{
////                lineView.background = (resources.getDrawable(lineDefault[index]))
////                textButtons.get(index).setTextColor(resources.getColor(R.color.colorGrayTextButtonFlight))
////                val face = Typeface.createFromAsset(context.getAssets(),
////                        "font/Roboto_Regular.ttf")
////                textButtons.get(index).typeface = face
////            }
////        }
//    }
}