package com.mobile.travelaja.module.settlement.view.adapter

import android.view.View
import androidx.databinding.ObservableInt
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.R
import com.mobile.travelaja.base.list.BaseListAdapter
import com.mobile.travelaja.databinding.ItemAttachmentSettlementBinding
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import opsigo.com.domainlayer.model.settlement.Attachment

class AttachmentAdapter(val recyclerView: RecyclerView, val viewModel : SettlementViewModel?) : BaseListAdapter<Attachment>() {
    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getLayoutItem(viewType: Int): Int = R.layout.item_attachment_settlement


    override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemAttachmentSettlementBinding.bind(view)
        return AttachmentAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AttachmentAdapterViewHolder) {
            val data = list[position]
            if (viewModel!= null){
                val loading = viewModel.isLoadingFile
                val error = viewModel.isErrorFile
                holder.onBind(data, position,loading,error)
            }else {
                holder.onBind(data,position, ObservableInt(-1),ObservableInt(-1))
            }

        }
    }

    override fun getItemCount(): Int = list.size

    inner class AttachmentAdapterViewHolder(val binding: ItemAttachmentSettlementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Attachment,
                   position: Int,
                   loading : ObservableInt,
                   error : ObservableInt) {
            binding.nameFile = data.Description
            binding.position = position + 1
            binding.loading = loading
            binding.error = error
            binding.executePendingBindings()
            binding.ivDelete.setOnClickListener {
                delete(position)
            }

            binding.tvFailed.setOnClickListener {
                retry(position,data)
            }
        }

        private fun delete(position : Int) {
            viewModel?.deleteFile(position)
        }

        private fun retry(position : Int, data : Attachment){
            viewModel?.uploadFile(position + 1,data)

        }
    }
}

