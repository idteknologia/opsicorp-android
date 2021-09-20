package com.mobile.travelaja.module.settlement.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.mobile.travelaja.R
import opsigo.com.domainlayer.model.settlement.RouteTransport

class RouteAdapter(context: Context) :
    ArrayAdapter<RouteTransport>(context, R.layout.item_settlement_dropdown) {



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v = super.getView(position, convertView, parent)
        (v as TextView).contentDescription = "choiceRoute$position"
        setEnabled(v, getItem(position)?.enabled ?: true)
        return v
    }

    override fun isEnabled(position: Int): Boolean {
        return getItem(position)?.enabled ?: true
    }

    private fun setEnabled(textView : TextView, enabled : Boolean){
        textView.isEnabled = enabled
        textView.alpha = if (enabled) 1F else 0.5F
    }
}