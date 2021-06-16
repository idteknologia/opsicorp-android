package com.mobile.travelaja.module.item_custom.menu_bottom

import kotlinx.android.synthetic.main.menu_bottom_view.view.*
import android.widget.LinearLayout
import android.util.AttributeSet
import android.widget.ImageView
import android.content.Context
import com.mobile.travelaja.R
import android.view.View

class MenuBottomOpsicorp : LinearLayout, View.OnClickListener {


    lateinit var onclick : OnclickButtonListener
    var dataImgSelected  = ArrayList<Int>()
    var dataImgDefault  = ArrayList<Int>()
    var buttonImageView = ArrayList<ImageView>()
    var dataTtl  = ArrayList<String>()

    fun callbackAccomodationMenuBottom(onclickButtonListener: OnclickButtonListener){
        onclick = onclickButtonListener
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        setOrientation(LinearLayout.VERTICAL);
        View.inflate(context, R.layout.menu_bottom_view, this)

        initLogicButton()
    }

    fun setDataImage(data: ArrayList<Int>){
        dataImgDefault.clear()
        dataImgDefault.addAll(data)
        setImage()
    }

    fun setDataTitle(data: ArrayList<String>){
        dataTtl.clear()
        dataTtl.addAll(data)
        setTitle()
    }

    fun setDataImageSelected(data: ArrayList<Int>){
        dataImgSelected.clear()
        dataImgSelected.addAll(data)
    }

    private fun setTitle() {
        tv_1.text = dataTtl[0]
        tv_2.text = dataTtl[1]
        tv_3.text = dataTtl[2]
        tv_4.text = dataTtl[3]
        tv_5.text = dataTtl[4]
    }

    private fun setImage() {
        img_1.setImageDrawable(resources.getDrawable(dataImgDefault[0]))
        img_2.setImageDrawable(resources.getDrawable(dataImgDefault[1]))
        img_3.setImageDrawable(resources.getDrawable(dataImgDefault[2]))
        img_4.setImageDrawable(resources.getDrawable(dataImgDefault[3]))
        img_5.setImageDrawable(resources.getDrawable(dataImgDefault[4]))
    }

    public fun goneFive(){
        img_5.visibility = View.GONE
        tv_5.visibility = View.GONE
        line_5.visibility = View.GONE
        //buttonImageView.a
    }

    private fun initLogicButton() {
        img_1.setOnClickListener(this)
        img_2.setOnClickListener(this)
        img_3.setOnClickListener(this)
        img_4.setOnClickListener(this)
        img_5.setOnClickListener(this)

        tv_1.setOnClickListener(this)
        tv_2.setOnClickListener(this)
        tv_3.setOnClickListener(this)
        tv_4.setOnClickListener(this)
        tv_5.setOnClickListener(this)

        line_1.setOnClickListener(this)
        line_2.setOnClickListener(this)
        line_3.setOnClickListener(this)
        line_4.setOnClickListener(this)
        line_5.setOnClickListener(this)

        buttonImageView.clear()
        buttonImageView.add(img_1)
        buttonImageView.add(img_2)
        buttonImageView.add(img_3)
        buttonImageView.add(img_4)
        buttonImageView.add(img_5)
    }

    interface OnclickButtonListener{
        fun one()
        fun two()
        fun three()
        fun four()
        fun five()
    }

    override fun onClick(v: View?) {

        if(v==img_1||v==tv_1||v==line_1){
            changeImageBtn(0)
            onclick.one()
        }
        else if(v==img_2||v==tv_2||v==line_2){
            changeImageBtn(1)
            onclick.two()
        }
        else if (v==img_3||v==tv_3||v==line_3){
            changeImageBtn(2)
            onclick.three()
        }
        else if(v==img_4||v==tv_4||v==line_4){
            changeImageBtn(3)
            onclick.four()
        }
        else if(v==img_5||v==tv_5||v==line_5){
            changeImageBtn(4)
            onclick.five()
        }
    }

    private fun changeImageBtn( position:Int) {

        buttonImageView.forEachIndexed { index, imageView ->
            if (index==position){
                imageView.setImageDrawable(resources.getDrawable(dataImgSelected[position]))
            }
            else{
                imageView.setImageDrawable(resources.getDrawable(dataImgDefault[index]))
            }
        }
    }

    fun setButtonSelectedPosition(position:Int){
        changeImageBtn(position)
        when(position){
            0 ->{
                onclick.one()
            }
            1 ->{
                onclick.two()
            }
            2 ->{
                onclick.three()
            }
            3 ->{
                onclick.four()
            }
            4 ->{
                onclick.five()
            }
        }
    }

    fun setBackgroundLine(color:Int){
        lay_nav_Bottom.setBackgroundColor(resources.getColor(color))
    }

}