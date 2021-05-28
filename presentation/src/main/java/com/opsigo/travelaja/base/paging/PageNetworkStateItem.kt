package com.opsigo.travelaja.base.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.opsigo.travelaja.R
import com.opsigo.travelaja.databinding.NetworkStateItemBinding

class PageNetworkStateItem(val view: NetworkStateItemBinding,
                           private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(view.root) {

    init {
        view.retryButton.setOnClickListener {
            retryCallback()
        }
    }
    fun bindTo(isLoading : Boolean,isError : Boolean){
        view.isError = isError
        view.isLoading = isLoading
        view.executePendingBindings()
    }
}