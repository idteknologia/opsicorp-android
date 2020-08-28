package com.opsigo.travelaja.module.accomodation.ssr

import com.opsigo.travelaja.R
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.utility.Constants
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.DefaultItemAnimator
import kotlinx.android.synthetic.main.ssr_list_activity.*
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import opsigo.com.domainlayer.model.accomodation.flight.DataSsr
import opsigo.com.domainlayer.model.accomodation.flight.SsrModel
import kotlinx.android.synthetic.main.detail_search_filter_activity.*
import com.opsigo.travelaja.module.item_custom.tablayout.TabLayoutOpsicorp

class SsrListActivity : BaseActivity(),
        TabLayoutOpsicorp.OnclickButtonListener,OnclickListenerRecyclerView{

    val data = ArrayList<String>()


    override fun getLayout(): Int {
        return R.layout.ssr_list_activity
    }

    val ssr = SsrModel()
    val adapter by lazy { SsrListAdapter(this) }

    val meal = "Meal"
    val ps   = "Participant Assistant"
    var positionTabName = ""
    val ssrMeal = ArrayList<DataSsr>()
    val ssrAssistent = ArrayList<DataSsr>()

    override fun OnMain() {
        addDataTabLayout()
        tablayout.buildTabLayout(data)
        tablayout.callbackOnclickButton(this)
        initRecyclerView()
        setDataRecyclerView()
    }

    private fun setDataRecyclerView() {
        ssrMeal.clear()
        ssrAssistent.clear()
        ssrMeal.addAll(ssr.dataMeal)
        ssrAssistent.addAll(ssr.dataDrink)
        ssrAssistent.addAll(ssr.dataSport)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv_result_flight.layoutManager = layoutManager
        rv_result_flight.itemAnimator = DefaultItemAnimator()
        rv_result_flight.adapter = adapter

        adapter.setOnclickListener(this)
    }

    fun addDataTabLayout(){
        if (ssr.isHaveMeal){
            data.add(meal)
        }
        if (ssr.isHaveSport||ssr.isHaveDrink||ssr.isOther){
            data.add(ps)
        }
    }

    override fun onClicked(positionTab: Int,name:String) {
        when(name){
            meal->{
                adapter.setData(ssrMeal)
            }
            ps->{
                adapter.setData(ssrAssistent)
            }
        }
        positionTabName = name
    }


    override fun onClick(views: Int, position: Int) {
        when(views){
            Constants.KEY_CHECK_BOX_SSR -> {
                if (positionTabName==meal){
                    ssrMeal[position].selected = !ssr.dataMeal[position].selected
                    adapter.notifyItemChanged(position)
                }
                else if(positionTabName==ps){
                    ssrAssistent[position].selected = !ssr.dataMeal[position].selected
                    adapter.notifyItemChanged(position)
                }
            }
        }
    }
}