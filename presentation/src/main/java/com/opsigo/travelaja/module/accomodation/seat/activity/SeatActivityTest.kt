package com.opsigo.travelaja.module.accomodation.seat.activity

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.accomodation.seat.adapter.CabinAdapter
import com.opsigo.travelaja.module.accomodation.seat.adapter.PenumpangAdapter2
import com.opsigo.travelaja.module.accomodation.seat.model.AddDummyDataSeat
import com.opsigo.travelaja.module.accomodation.seat.model.CabinModel2
import com.opsigo.travelaja.module.accomodation.seat.model.SeatModel2
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.opsigo.travelaja.utility.OnclickListenerRecyclerViewParent
import kotlinx.android.synthetic.main.seat_activity_view.*


class SeatActivityTest : BaseActivity() {
    override fun getLayout(): Int { return R.layout.seat_activity_view }

    var posisitionPage = 0
    var lastPosition   = 0
    var dataCabin = ArrayList<CabinModel2>()
    var cabinAdapter = CabinAdapter(this,dataCabin)
    var dataPenumpang = ArrayList<SeatModel2>()
    var adapterRvSelected = PenumpangAdapter2(this,dataPenumpang)
    val snapHelper = LinearSnapHelper()
    val layoutManager = LinearLayoutManager(this)

    override fun OnMain() {
        setInitRecyclerCabin()
        setDataCabin()
        setInitRecyclerPenumpang()
        setDataPenumpang()

        testing()
//        setLog((Globals.getWitdhAndHeightLayout(this).first-Globals.toDp(this,40)).toString())
//        setLog("0-------------jdsc ("+(Globals.getWitdhAndHeightLayout(this).first-50)+")")

    }



    private fun testing() {
        val lp = LinearLayout.LayoutParams(Globals.toDp(this,(Globals.getWitdhAndHeightLayout(this).first-40)),
                Globals.toDp(this,50))
        testImage.setLayoutParams(lp)
    }

    private fun setInitRecyclerPenumpang() {
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv_penumpang.layoutManager = layoutManager
        rv_penumpang.adapter = adapterRvSelected
        rv_penumpang.setOnTouchListener(object :View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }
        })

        adapterRvSelected.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                if (dataPenumpang.get(position).actived){
                    setLog("active")
                }
                else{
                    changeDataPenumpangFromRvPenumpang(dataPenumpang.get(position).namePassager)
                }
            }
        })

    }

    private fun setInitRecyclerCabin() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv_cabin.layoutManager = layoutManager
        rv_cabin.itemAnimator = DefaultItemAnimator()
        rv_cabin.adapter = cabinAdapter

        snapHelper.attachToRecyclerView(rv_cabin)

//        rv_cabin.addOnScrollListener(object :RecyclerView.OnScrollListener(){
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                val centerView = snapHelper.findSnapView(layoutManager)
//                posisitionPage = layoutManager.getPosition(centerView!!)
//                if (newState == RecyclerView.SCROLL_STATE_IDLE || (posisitionPage == 0 && newState == RecyclerView.SCROLL_STATE_DRAGGING)) {
//                    setLog("BINDING positionView SCROLL_STATE_IDLE: $posisitionPage")
//                }
//            }
//        })

        cabinAdapter.setOnclickListener(object :OnclickListenerRecyclerViewParent{
            override fun onClick(viewsParent: Int, positionParent: Int, viewsChild: Int, positionChild: Int) {

                when(viewsParent){
                    -11->{
                        if (dataCabin.get(positionParent).Seat.get(positionChild).actived&&dataCabin.get(positionParent).Seat.get(positionChild).selected){ }
                        else if(!dataCabin.get(positionParent).Seat.get(positionChild).actived&&dataCabin.get(positionParent).Seat.get(positionChild).selected){

                            changedDataActivated(positionParent,positionChild)
                        }
                        else{
                            dataCabin.forEachIndexed { n, cabinModel2 ->
                                cabinModel2.Seat.forEachIndexed { index, seatModel2 ->
                                    if (seatModel2.actived == true && seatModel2.type == "bangku") {
                                        seatModel2.selected = false
                                        seatModel2.actived = false
                                        dataCabin.get(positionParent).Seat.get(positionChild).namePassager = seatModel2.namePassager
                                        seatModel2.namePassager = ""
                                        lastPosition = n
                                    }
                                    if(seatModel2.selected == true && seatModel2.type == "bangku"){
                                        seatModel2.positionPage = n
                                    }
                                }
                            }
                            dataCabin.get(positionParent).Seat.get(positionChild).actived = true
                            dataCabin.get(positionParent).Seat.get(positionChild).selected = true
                        }
                        cabinAdapter.notifyItemChanged(positionParent)
                        cabinAdapter.notifyItemChanged(lastPosition)

                        changedDataActivatedPenumpang()

                    }
                }
            }
        })
    }

    private fun setDataCabin() {
        dataCabin.clear()
        dataCabin = AddDummyDataSeat().addDummyDataCabin()
        cabinAdapter.setData(dataCabin)
    }

    fun setDataPenumpang(){
        dataPenumpang.clear()
        dataCabin.forEachIndexed { index, cabinModel2 ->
            dataPenumpang.addAll(cabinModel2.Seat)
        }
        adapterRvSelected.setData(dataPenumpang)
    }

    fun changeDataPenumpangFromRvPenumpang(name:String){

        //get getDataLogin penumpang by name
        dataCabin.forEachIndexed { indexParent, cabinModel2 ->
            cabinModel2.Seat.forEachIndexed { indexChild, seatModel2 ->
                if (seatModel2.namePassager==name){
                    changedDataActivated(indexParent,indexChild)
                    rv_cabin.scrollToPosition(indexParent)
                }
            }
        }
    }

    private fun changedDataActivated(positionParent: Int, positionChild: Int) {
        dataCabin.forEachIndexed { n, cabinModel2 ->
            cabinModel2.Seat.forEachIndexed { index, seatModel2 ->
                if (seatModel2.actived == true && seatModel2.type == "bangku") {
                    seatModel2.actived = false
                    lastPosition = n
                }
                if(seatModel2.selected == true && seatModel2.type == "bangku"){
                    seatModel2.positionPage = n

                }
            }
        }
        dataCabin.get(positionParent).Seat.get(positionChild).actived = true
        cabinAdapter.notifyItemChanged(positionParent)
        cabinAdapter.notifyItemChanged(lastPosition)

        changedDataActivatedPenumpang()
    }

    private fun changedDataActivatedPenumpang() {
        setInitRecyclerPenumpang()
        setDataPenumpang()
    }
}