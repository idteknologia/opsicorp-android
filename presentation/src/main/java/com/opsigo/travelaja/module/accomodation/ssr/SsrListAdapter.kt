package com.opsigo.travelaja.module.accomodation.ssr

import android.view.View
import com.opsigo.travelaja.R
import android.view.ViewGroup
import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.opsigo.travelaja.utility.Constants
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.opsigo.travelaja.utility.OnclickListenerRecyclerViewParent
import kotlinx.android.synthetic.main.list_ssr_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.flight.SsrItemModel

class SsrListAdapter (context: Context): RecyclerView.Adapter<SsrListAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerViewParent
    var items = ArrayList<SsrItemModel>()
    val context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_ssr_adapter, parent, false)

        return ViewHolder(itemView)
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerViewParent){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items.get(position)
        /*if (data.dataSSR.dataMeal.isNotEmpty()){
            holder.itemView.tv_ssr_type.text  = data.dataSSR.dataMeal[position].ssrTypeName
        } else if (data.dataSSR.dataSport.isNotEmpty()){
            holder.itemView.tv_ssr_type.text  = data.dataSSR.dataSport[position].ssrTypeName
        } else if (data.dataSSR.dataDrink.isNotEmpty()){
            holder.itemView.tv_ssr_type.text  = data.dataSSR.dataDrink[position].ssrTypeName
        }*/
        holder.itemView.tv_ssr_type.text = data.ssrTypeName
        Log.e("testAdapter",items.size.toString())
        setDataRecycler(holder,data,position)

    }

    private fun setDataRecycler(holder: ViewHolder, data: SsrItemModel, positionParent: Int) {
        val adapter by lazy { SsrListItemAdapter(context) }
        val layoutManager = LinearLayoutManager(context)

        layoutManager.orientation = LinearLayoutManager.VERTICAL
        holder.itemView.rv_list_item_ssr.layoutManager = layoutManager
        holder.itemView.rv_list_item_ssr.itemAnimator = DefaultItemAnimator()
        holder.itemView.rv_list_item_ssr.adapter = adapter

        adapter.setOnclickListener(object  : OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                when(views){
                    Constants.KEY_CHECK_BOX_SSR -> {
                        /*onclick.onClick(Constants.KEY_CHECK_BOX_SSR,positionParent,Constants.KEY_CHECK_BOX_SSR,position)*/
                    }
                }
            }
        })
        /*if (data.dataSSR.dataMeal.isNotEmpty()){
            adapter.setData(data.dataSSR.dataMeal)
        } else if (data.dataSSR.dataDrink.isNotEmpty()){
            adapter.setData(data.dataSSR.dataDrink)
        } else if (data.dataSSR.dataSport.isNotEmpty()){
            adapter.setData(data.dataSSR.dataSport)
        }*/
        adapter.setData(data.ssrItem)
    }


    fun setData(data: ArrayList<SsrItemModel>) {
        items = data
        Globals.setLog(data.size.toString())
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}