package com.opsigo.travelaja.module.item_custom.galery

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.galery_view.*

class MyGaleryActivity :BaseActivity(),OnclickListenerRecyclerView{

    override fun getLayout(): Int {
        return R.layout.galery_view
    }

    val snapHelper = LinearSnapHelper()
    var positionCenter = -1
    val adapter by lazy { MyGaleryAdapter(this,MyGalery.imagesGalery) }

    override fun OnMain() {
        initView()
    }

    private fun initView() {

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv_slider_image.layoutManager = layoutManager
        rv_slider_image.setHasFixedSize(true)
        rv_slider_image.setAdapter(adapter)

        snapHelper.attachToRecyclerView(rv_slider_image)

        rv_slider_image.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val centerView = snapHelper.findSnapView(layoutManager)
                positionCenter = layoutManager.getPosition(centerView!!)
                if (newState == RecyclerView.SCROLL_STATE_IDLE || (positionCenter == 0 && newState == RecyclerView.SCROLL_STATE_DRAGGING)) {
                    setLog("BINDING positionView SCROLL_STATE_IDLE: $positionCenter")
                    setImageHotel(positionCenter)
                }
            }
        })

        btn_close.setOnClickListener { Globals.finishResultCancel(this) }
        adapter.setOnclickListener(this)
        setImageHotel(0)
    }

    private fun setImageHotel(positionCenter: Int) {
        title_image.text = MyGalery.imagesGalery[positionCenter].description
        total_image.text = "${positionCenter+1}/${MyGalery.imagesGalery.size}"
        Picasso.get()
                .load(MyGalery.imagesGalery[positionCenter].imageUri)
                .into(ic_image)
    }

    override fun onClick(views: Int, position: Int) {
        /*Globals.delay(200,object :Globals.DelayCallback{
            override fun done() {
                rv_slider_image.smoothScrollToPosition(position)
            }
        })*/
        setImageHotel(position)

        /*val intent = Intent()
        intent.putExtra("image",MyGalery.imagesGalery[position].imageUri)
        intent.putExtra("title",MyGalery.imagesGalery[position].description)
        intent.putExtra("position",position)
        Globals.finishResultOk(this,intent)*/
    }

    override fun onBackPressed() {
        Globals.finishResultCancel(this)
    }

}