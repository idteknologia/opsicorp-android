package com.mobile.travelaja.base.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class PageListAdapter<T : Any>(diffUtil: DiffUtil.ItemCallback<T>) : PagingDataAdapter<T, RecyclerView.ViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutItem(viewType), parent, false)
        return viewHolder(view, viewType)
    }

    abstract fun getLayoutItem(viewType: Int): Int

    abstract fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder

}