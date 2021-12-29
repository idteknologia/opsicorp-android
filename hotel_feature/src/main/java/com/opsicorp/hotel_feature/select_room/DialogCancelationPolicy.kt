package com.opsicorp.hotel_feature.select_room

import android.os.Bundle
import android.view.View
import com.opsicorp.hotel_feature.R
import android.annotation.SuppressLint
import com.unicode.kingmarket.Base.BaseDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.opsicorp.hotel_feature.adapter.SummaryCancelAdapter
import kotlinx.android.synthetic.main.dialog_cancelation_policy.*

@SuppressLint("ValidFragment")
class DialogCancelationPolicy(val data:List<String>) : BaseDialogFragment() {

    override fun getLayout(): Int {
        transparantBackground()
        return R.layout.dialog_cancelation_policy }

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        btn_cancel.setOnClickListener {
            dismiss()
        }
        val adapter = SummaryCancelAdapter(data)
        val layoutManagerReview = LinearLayoutManager(context)
        layoutManagerReview.orientation = LinearLayoutManager.VERTICAL
        rv_summary_cancel_policy.layoutManager = layoutManagerReview
        rv_summary_cancel_policy.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_summary_cancel_policy.adapter = adapter

        adapter.setData(data)
    }

}