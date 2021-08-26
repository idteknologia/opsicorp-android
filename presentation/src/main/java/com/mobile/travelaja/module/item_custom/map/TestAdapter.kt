package com.mobile.travelaja.module.item_custom.map

import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import com.mobile.travelaja.R
import kotlinx.android.synthetic.main.test_adapter.view.*
import java.util.*

class TestAdapter (context: Context, private var items: ArrayList<String>): androidx.recyclerview.widget.RecyclerView.Adapter<TestAdapter.ViewHolder>() {

   val context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.test_adapter, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, positionParent: Int) {

        val data = items.get(positionParent)
        holder.itemView.tv_test.text = data
    }

    fun setData(data:ArrayList<String>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(row) {

    }
}