package com.opsicorp.travelaja.feature_flight.ssr

import android.view.View
import android.view.ViewGroup
import android.content.Context
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.opsigo.travelaja.utility.Constants
import androidx.recyclerview.widget.RecyclerView
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.opsigo.travelaja.utility.OnclickListenerRecyclerViewParent
import kotlinx.android.synthetic.main.list_ssr_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.flight.SsrItemModel

class SsrListAdapter (context: Context): androidx.recyclerview.widget.RecyclerView.Adapter<SsrListAdapter.ViewHolder>() {

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
        holder.itemView.tv_ssr_type.text = Globals.splitCamelCase(data.ssrTypeName)
        /*Log.e("testAdapter",items.size.toString())*/
        setDataRecycler(holder,data,position)

    }

    private fun setDataRecycler(holder: ViewHolder, data: SsrItemModel, positionParent: Int) {
        val adapter by lazy { SsrListItemAdapter(context) }
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)

        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        holder.itemView.rv_list_item_ssr.layoutManager = layoutManager
        holder.itemView.rv_list_item_ssr.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        holder.itemView.rv_list_item_ssr.adapter = adapter

        adapter.setOnclickListener(object  : OnclickListenerRecyclerView{
            override fun onClick(views: Int, position: Int) {
                when(views){
                    Constants.KEY_CHECK_BOX_SSR -> {
                        onclick.onClick(Constants.KEY_CHECK_BOX_SSR,positionParent,Constants.KEY_CHECK_BOX_SSR,position)
                    }
                }
            }
        })
        adapter.setData(data.ssrItem)
    }


    fun setData(data: ArrayList<SsrItemModel>) {
        items = data
        Globals.setLog(data.size.toString())
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}