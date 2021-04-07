package com.opsigo.travelaja.module.cart.view

import android.content.Context
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.cart.adapter.CartAdapterNew
import com.opsigo.travelaja.module.cart.model.CartModel
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.page_detail_cart_bisnis_trip.view.*

class PageDetailPersonalTrip : LinearLayout,OnclickListenerRecyclerView{

    lateinit var onclick : Callback

    fun callbackOnclickButton(onclickButtonListener: Callback){
        onclick = onclickButtonListener
    }

    val data = ArrayList<CartModel>()
    val adapter by lazy { CartAdapterNew(context) }

    constructor(context: Context) : super(context) {
        init()
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : super(context, attrs, defStyle) {

        val a = context.obtainStyledAttributes(attrs, R.styleable.ButtonDefaultOpsicorp, defStyle, 0)
        a.recycle()

        init()
    }

    private fun init() {
        setOrientation(VERTICAL)
        View.inflate(context, R.layout.page_detail_cart_bisnis_trip, this)

        initRecyclerView()
        initCheckEmpty()
    }

    private fun initCheckEmpty() {
        if (data.isEmpty()){
            line_empty.visibility = View.VISIBLE
        }
        else {
            line_empty.visibility = View.INVISIBLE
        }

    }

    override fun onClick(views: Int, position: Int) {

    }

    interface Callback {

    }

    private fun initRecyclerView() {

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        list_item_personal.layoutManager = layoutManager
        list_item_personal.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        list_item_personal.adapter = adapter
        adapter.setOnclickListener(this)
    }

}