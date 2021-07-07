package com.mobile.travelaja.base.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter <T : Any> (var list: MutableList<T> = mutableListOf()) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutItem(viewType), parent, false)
        return viewHolder(view, viewType)
    }

    abstract fun getLayoutItem(viewType: Int): Int

    abstract fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder
}