package com.opsigo.travelaja.module.item_custom.slider

import android.content.Context
import android.os.Handler
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.opsigo.travelaja.R
import kotlinx.android.synthetic.main.slider_image_opsicorp.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf



class SliderImageOpsicorp : LinearLayout, View.OnClickListener,KoinComponent {

    lateinit var callback : OnclickButtonListener
    var posisitionPage = 0
    val snapHelper = androidx.recyclerview.widget.LinearSnapHelper()
    var dataList = ArrayList<SliderImageModel>()
    val adapter by inject<SliderImageAdapter> { parametersOf(dataList) }

    var cancelDelay = false

    fun callbackSlider(onclickButtonListener: OnclickButtonListener){
        callback = onclickButtonListener
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        setOrientation(VERTICAL);
        View.inflate(context, R.layout.slider_image_opsicorp, this)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
        rv_slider.layoutManager = layoutManager
        rv_slider.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_slider.adapter = adapter

        snapHelper.attachToRecyclerView(rv_slider)

        rv_slider.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: androidx.recyclerview.widget.RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val centerView = snapHelper.findSnapView(layoutManager)
                posisitionPage = layoutManager.getPosition(centerView!!)
                if (newState == androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE || (posisitionPage == 0 && newState == androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING)) {
                    callback.onCenter(posisitionPage)
                    cancelDelay = true
                    Log.e("Tag","BINDING positionView SCROLL_STATE_IDLE: $posisitionPage")
                }
            }
        })
    }

    fun setData(mData:ArrayList<SliderImageModel>){
        dataList.clear()
        dataList = mData
        adapter.setData(dataList)

        rv_slider.post(Runnable {
            rv_slider.smoothScrollToPosition(1)
        })

        slideAutomatic(dataList)
    }

    fun slideAutomatic(data: ArrayList<SliderImageModel>){

        /*val runnable = object : Runnable {
            override fun run() {
                if (cancelDelay) {
                    rv_slider.post(Runnable {

                        if (posisitionPage==data.size-1){
                            posisitionPage = 0
                        }
                        else{
                            posisitionPage++
                        }
                        rv_slider.smoothScrollToPosition(posisitionPage)

                    })

                    slideAutomatic(data)
                    handler.postDelayed(this, 5000)
                } else {
                    cancelDelay = false
                    handler.removeCallbacks(this)
                    slideAutomatic(data)
                }
            }
        }*/

        var delay = Handler().postDelayed({


            rv_slider.post(Runnable {
                if (cancelDelay){
                    cancelDelay = false
                }
                else {
                    if (posisitionPage==data.size-1){
                        posisitionPage = 0
                    }
                    else{
                        posisitionPage++
                    }
                    rv_slider.smoothScrollToPosition(posisitionPage)
                }
            })
            slideAutomatic(data)

        }, 5000)
    }


    interface OnclickButtonListener{
        fun onCenter(position:Int)
    }

    override fun onClick(v: View?) {

    }

}