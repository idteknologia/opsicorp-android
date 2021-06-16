package com.mobile.travelaja.base.paging

import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.databinding.NetworkStateItemBinding

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