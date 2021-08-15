package com.mobile.travelaja.module.settlement.view.adapter

import android.content.res.Resources
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.Utils
import opsigo.com.domainlayer.model.settlement.Attachment

object BindingsAdapter {
    @JvmStatic
    @BindingAdapter("addAttachments")
    fun addDataAttachments(recyclerView: RecyclerView, list : MutableList<Attachment>){
        val adapter = recyclerView.adapter as AttachmentAdapter
        adapter.list = list
        adapter.notifyDataSetChanged()
    }

    @BindingAdapter("app:totalValueIdr","app:totalValueUsd")
    @JvmStatic
    fun formatTextViewCurrency(textView : TextView, amountIdr : Number?, amountUsd : Number?){
        val idr = Utils.formatCurrency(amountIdr)
        val idrLabel = textView.context.getString(R.string.other_expense_idr)
        var text = "$idr $idrLabel"
        if (amountUsd != null && amountUsd.toDouble() > 0.0){
            val usd = Utils.formatCurrency(amountUsd)
            val usdLabel = textView.context.getString(R.string.other_expense_usd)
            text += "\n$usd $usdLabel"
        }
        textView.text = text
    }

    @BindingAdapter("app:iconExpandTextView")
    @JvmStatic
    fun setIconExpand(textView: TextView,isExpand : Boolean){
        val iconExpandDown = textView.context.resources.getDrawable(R.drawable.ic_down_expand)
        val iconExpandUp = textView.context.resources.getDrawable(R.drawable.ic_up_expand)
        textView.setCompoundDrawablesWithIntrinsicBounds(if (isExpand)iconExpandDown else iconExpandUp,null,null,null)
    }
}