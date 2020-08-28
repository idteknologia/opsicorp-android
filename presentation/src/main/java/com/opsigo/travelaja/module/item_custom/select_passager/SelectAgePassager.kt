package com.opsigo.travelaja.module.item_custom.select_passager

import android.annotation.SuppressLint
import android.app.DialogFragment
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.item_custom.sanckbar_custom.SnackBarCustomForDialog
import com.opsigo.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.unicode.kingmarket.Base.BaseBottomSheetDialogFrament


@SuppressLint("ValidFragment")
class SelectAgePassager : BaseBottomSheetDialogFrament,ButtonDefaultOpsicorp.OnclickButtonListener,ToolbarOpsicorp.OnclickButtonListener,View.OnClickListener{


    override fun getLayout(): Int {
        return R.layout.fragment_bottom_sheet
    }

    var totalInfant = 0
    var totalChild  = 0
    var totalAdult  = 1
    var limitAdult  = 0
    var limitChild  = 0
    var limitInfant = 0
    lateinit var btn_add_adult: ImageView
    lateinit var btn_add_child: ImageView
    lateinit var btn_add_infant: ImageView
    lateinit var btn_minus_adult: ImageView
    lateinit var btn_minus_child: ImageView
    lateinit var btn_minus_infant: ImageView
    lateinit var lineBottomSheet : RelativeLayout
    lateinit var tv_adult  : TextView
    lateinit var tv_child  : TextView
    lateinit var tv_infant : TextView
    lateinit var snackbar  : SnackBarCustomForDialog
    var style = 0

    override fun onMain(fragment: View) {

        if (style!=0){
            setStyle(DialogFragment.STYLE_NO_TITLE, style)
        }

        var toolbar    = fragment.findViewById(R.id.toolbar) as ToolbarOpsicorp
        var btnNext= fragment.findViewById(R.id.btn_next) as ButtonDefaultOpsicorp

        lineBottomSheet  = fragment.findViewById(R.id.design_bottom_sheet) as RelativeLayout
        btn_add_adult    = fragment.findViewById(R.id.btn_add_adult) as ImageView
        btn_add_child    = fragment.findViewById(R.id.btn_add_child) as ImageView
        btn_add_infant   = fragment.findViewById(R.id.btn_add_infant) as ImageView
        btn_minus_adult  = fragment.findViewById(R.id.btn_minus_adult) as ImageView
        btn_minus_child  = fragment.findViewById(R.id.btn_minus_child) as ImageView
        btn_minus_infant = fragment.findViewById(R.id.btn_minus_infant) as ImageView
        tv_adult         = fragment.findViewById(R.id.tv_adult) as TextView
        tv_child         = fragment.findViewById(R.id.tv_child) as TextView
        tv_infant        = fragment.findViewById(R.id.tv_infant) as TextView
        snackbar         = fragment.findViewById(R.id.snackbar) as SnackBarCustomForDialog


        toolbar.changeImageBtnBack(R.drawable.ic_close_white)
        toolbar.setTitleBar("Select Passengers")
        toolbar.callbackOnclickToolbar(this)
        toolbar.hidenBtnCart()
        btnNext.setTextButton("Search Flight")


        btn_add_adult.setOnClickListener(this)
        btn_add_child.setOnClickListener(this)
        btn_add_infant.setOnClickListener(this)
        btn_minus_adult.setOnClickListener(this)
        btn_minus_child.setOnClickListener(this)
        btn_minus_infant.setOnClickListener(this)
        btnNext.callbackOnclickButton(this)

        changeTotalView()


    }

    @SuppressLint("ValidFragment")
    constructor(fullScreen:Boolean,mStyle: Int){
        if (fullScreen){
            setFullScreen()
        }
        style = mStyle
    }

    fun setLimitSelect(mLimitAdult:Int,mLimitChild:Int,mLimitInfant:Int){
        limitAdult  = mLimitAdult
        limitChild  = mLimitChild
        limitInfant = mLimitInfant
    }


    override fun onClick(v: View?) {
        if (v==btn_add_adult){

            if (totalAdult<limitAdult){
                totalAdult++
            }
            else{
                showSanckBar("Adult",limitAdult)
            }
            changeTotalView()
        }
        else if(v==btn_add_child){
            if (totalChild<limitChild){
                totalChild++
            }
            else{

                showSanckBar("Child",limitChild)
            }

            changeTotalView()
        }
        else if(v==btn_add_infant){
            if(totalInfant<limitInfant){
                totalInfant++
            }
            else{
                showSanckBar("Infant",limitInfant)
            }
            changeTotalView()
        }
        else if(v==btn_minus_adult){
            if(totalAdult!=1){
                totalAdult--
            }
            changeTotalView()
        }
        else if(v==btn_minus_child){
            if(totalChild!=0){
                totalChild--
            }
            changeTotalView()
        }
        else if(v==btn_minus_infant){
            if(totalInfant!=0){
                totalInfant--
            }
            changeTotalView()
        }
    }

    private fun showSanckBar(s: String,limit:Int) {
        snackbar.setTextMessage("The Number of ${s} should not Exceed ${limit}")
        snackbar.showSnackBar()
    }

//    private fun showSanckBar(s: String,limit:Int) {
//        var snackbar  = SnackbarCustom().showSnackbar(lineBottomSheet,R.layout.snack_bar_view_passanger,
//                2000,activity!!,0)
//        var myView = snackbar.getMyView()
//        var tv = myView.findViewById(R.emplaoyId.snackbar_action) as ImageView
//        var text = myView.findViewById(R.emplaoyId.textError) as TextView
//        text.text = "The Number of ${s} should not Exceed ${limit}"
//        tv.setOnClickListener {
//            snackbar.dismiss()
//        }
//        snackbar.show()
//    }

    override fun btnBack() {
        dismiss()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }

    lateinit var callback :CallbackSelectPasanger

    override fun onClicked() {
        callback.total(totalInfant,totalChild,totalAdult)
        dismiss()
    }

    interface CallbackSelectPasanger{
        fun total(totalInfant:Int,
                  totalChild:Int,
                  totalAdult:Int)
    }


    private fun changeTotalView() {
        tv_adult.text  = "${totalAdult} Adult(s)"
        tv_child.text  = "${totalChild} Childern"
        tv_infant.text = "${totalInfant} Infant(s)"

        if(totalAdult==0){
            btn_minus_adult.setImageDrawable(resources.getDrawable(R.drawable.minus_inactive))
        }else{
            btn_minus_adult.setImageDrawable(resources.getDrawable(R.drawable.minus_active))
        }

        if(totalChild==0){
            btn_minus_child.setImageDrawable(resources.getDrawable(R.drawable.minus_inactive))
        }
        else {
            btn_minus_child.setImageDrawable(resources.getDrawable(R.drawable.minus_active))
        }

        if(totalInfant==0){
            btn_minus_infant.setImageDrawable(resources.getDrawable(R.drawable.minus_inactive))
        }
        else{
            btn_minus_infant.setImageDrawable(resources.getDrawable(R.drawable.minus_active))
        }
    }

}