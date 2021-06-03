package com.opsigo.travelaja.module.accomodation.dialog.accomodation_reason_trip

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.opsigo.travelaja.R
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import com.unicode.kingmarket.Base.BaseBottomSheetDialogFrament
import opsigo.com.domainlayer.model.accomodation.ReasonCodeModel

@SuppressLint("ValidFragment")
class SelectReasonAccomodation : BaseBottomSheetDialogFrament {

    override fun getLayout(): Int { return R.layout.select_reason_code_view }

    var style = 0
    lateinit var rv_reason_code : RecyclerView
    lateinit var ic_back : ImageView
    var data = ArrayList<ReasonCodeModel>()
    val adapter by lazy { SelectReasonAdapter(requireContext(),data) }

    override fun onMain(fragment: View) {
        if (style!=0){
            setStyle(DialogFragment.STYLE_NO_TITLE, style)
        }

        rv_reason_code = fragment.findViewById(R.id.rv_reason_code) as RecyclerView
        ic_back        = fragment.findViewById(R.id.ic_back)as ImageView

        ic_back.setOnClickListener {
            dismiss()
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_reason_code.layoutManager = layoutManager
        rv_reason_code.itemAnimator = DefaultItemAnimator()
        rv_reason_code.adapter = adapter

        adapter.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when (views){
                    -1 -> {
                        Globals.delay(1000,object :Globals.DelayCallback{
                            override fun done() {
                                callback.callback(data[position])
                                dismiss()
                            }
                        })
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })

        adapter.setData(data)
    }

    @SuppressLint("ValidFragment")
    constructor(fullScreen:Boolean,mStyle: Int,mData:ArrayList<ReasonCodeModel>){
        if (fullScreen){
            setFullScreen()
        }
        style = mStyle
        data.clear()
        data.addAll(mData)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    lateinit var callback : CallbackSelectPreferance

    fun setCallbackListener(callbackSelectPreferance: CallbackSelectPreferance){
        callback = callbackSelectPreferance
    }

    interface CallbackSelectPreferance{
        fun callback(model: ReasonCodeModel)
    }


}