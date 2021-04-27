package com.opsicorp.travelaja.feature_flight.detail_cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.utility.StringUtils
import kotlinx.android.synthetic.main.detail_cart_price_adapter.view.*
import opsigo.com.domainlayer.model.summary.PaymentsItemModel

class DetailCartPriceAdapter(context: Context): RecyclerView.Adapter<DetailCartPriceAdapter.ViewHolder>() {

    var items = ArrayList<PaymentsItemModel>()
    val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.detail_cart_price_adapter, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)

        holder.itemView.namePriceItem.text = data.title
        holder.itemView.priceItemValue.text = StringUtils().setCurrency("IDR", data.amount!!,false)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(data: ArrayList<PaymentsItemModel>) {
        items = data
        notifyDataSetChanged()
    }

    fun removeLastItem(){
        items.removeAt(items.lastIndex)
        notifyDataSetChanged()
    }

    class ViewHolder (row: View) :RecyclerView.ViewHolder(row) {

    }

}