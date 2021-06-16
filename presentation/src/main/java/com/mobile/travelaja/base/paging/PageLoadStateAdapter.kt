package com.mobile.travelaja.base.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.mobile.travelaja.databinding.NetworkStateItemBinding

class PageLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<PageNetworkStateItem>() {
    override fun onBindViewHolder(holder: PageNetworkStateItem, loadState: LoadState) {
        val loading = loadState is LoadState.Loading
        val error = loadState is LoadState.Error
        holder.bindTo(loading,error)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): PageNetworkStateItem {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NetworkStateItemBinding.inflate(layoutInflater,parent,false)
        return PageNetworkStateItem(binding,retry)
    }
}