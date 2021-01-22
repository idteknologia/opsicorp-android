package com.opsigo.travelaja.module.accomodation.ssr

import android.os.Build
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.utility.Constants
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.DefaultItemAnimator
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import opsigo.com.domainlayer.model.accomodation.flight.DataSsr
import opsigo.com.domainlayer.model.accomodation.flight.SsrModel
import com.opsigo.travelaja.module.item_custom.tablayout.TabLayoutOpsicorp
import com.opsigo.travelaja.utility.Globals
import kotlinx.android.synthetic.main.ssr_list_activity.*
import kotlinx.android.synthetic.main.toolbar_view.view.*
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation
import opsigo.com.datalayer.mapper.Serializer

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
    lateinit var datalist: DataListOrderAccomodation

    override fun OnMain() {
        initToolbar()
        addDataTabLayout()
        tablayout.buildTabLayout(data)
        tablayout.callbackOnclickButton(this)
        initRecyclerView()
        setDataRecyclerView()
    }

    private fun initToolbar() {
        toolbar.setTitleBar("SSR list menu")
        toolbar.hidenBtnCart()
        toolbar.changeImageBtnBack(R.drawable.ic_close_white)
        toolbar.btn_back.setOnClickListener { finish() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.singgleTitleGravity(toolbar.START)
        }
    }

    private fun setDataRecyclerView() {
        datalist = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
        ssrMeal.clear()
        ssrAssistent.clear()
        ssrMeal.addAll(ssr.dataMeal)
        ssrAssistent.addAll(ssr.dataDrink)
        ssrAssistent.addAll(ssr.dataSport)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv_list_item_ssr.layoutManager = layoutManager
        rv_list_item_ssr.itemAnimator = DefaultItemAnimator()
        rv_list_item_ssr.adapter = adapter

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