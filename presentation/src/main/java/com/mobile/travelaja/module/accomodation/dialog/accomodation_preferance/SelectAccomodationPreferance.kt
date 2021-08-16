package com.mobile.travelaja.module.accomodation.dialog.accomodation_preferance

import android.annotation.SuppressLint
import android.app.DialogFragment
import android.content.DialogInterface
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.travelaja.R
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.module.item_custom.toolbar_view.ToolbarOpsicorp
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import com.unicode.kingmarket.Base.BaseBottomSheetDialogFrament

@SuppressLint("ValidFragment")
class SelectAccomodationPreferance : BaseBottomSheetDialogFrament,ToolbarOpsicorp.OnclickButtonListener,ButtonDefaultOpsicorp.OnclickButtonListener {

    override fun getLayout(): Int { return R.layout.select_acoomodation_view }

    var style = 0
    lateinit var toolbar : ToolbarOpsicorp
    lateinit var buttonSearch : ButtonDefaultOpsicorp
    lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    var data = ArrayList<AccomodationPreferanceModel>()
    val adapter by lazy { AccomodationPreferanceAdapter(requireContext(),data) }

    @SuppressLint("WrongConstant")
    override fun onMain(fragment: View) {
        if (style!=0){ setStyle(DialogFragment.STYLE_NO_TITLE, style) }

        toolbar = fragment.findViewById(R.id.toolbar)
        buttonSearch = fragment.findViewById(R.id.btn_next)
        recyclerView = fragment.findViewById(R.id.rv_select_accomodation)

        toolbar.setTitleBar(getString(R.string.airlines_preferences))
        toolbar.hidenBtnCart()
        toolbar.showBtnReset()
        toolbar.changeImageBtnBack(R.drawable.ic_close_white)
        toolbar.singgleTitleGravity(toolbar.START)
        toolbar.callbackOnclickToolbar(this)

        buttonSearch.callbackOnclickButton(this)
        buttonSearch.setTextButton(getString(R.string.set_flight_preference))

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter

        adapter.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when (views){
                    -5 -> {
                        data.get(position).checked = !data.get(position).checked
                        adapter.notifyItemChanged(position)
                    }
                    -2 -> {
                        data.forEachIndexed { index, accomodationPreferanceModel ->
                            accomodationPreferanceModel.checked = true
                        }
                        adapter.notifyDataSetChanged()
                    }
                    -3 -> {
                        data.forEachIndexed { index, accomodationPreferanceModel ->
                            accomodationPreferanceModel.checked = false
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })

        adapter.setData(data)
    }

    @SuppressLint("ValidFragment")
    constructor(fullScreen:Boolean,mStyle: Int,mData:ArrayList<AccomodationPreferanceModel>){
        if (fullScreen){
            setFullScreen()
        }
        style = mStyle
        data = mData
    }

    override fun btnBack() {
        dismiss()
    }

    override fun logoCenter() {

    }

    override fun btnCard() {

    }

    override fun onClicked() {

        var dataName = ""
        data.filter {
            it.checked
        }.forEachIndexed { index, accomodationPreferanceModel ->
            if (index!=data.filter { it.checked }.size-1){
                dataName = dataName+accomodationPreferanceModel.name+ " , "
            }
            else {
                dataName = dataName+accomodationPreferanceModel.name
            }
        }

        callback.callback(dataName,data)
        dismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    lateinit var callback : CallbackSelectPreferance

    fun setCallbackListener(callbackSelectPreferance: CallbackSelectPreferance){
        callback = callbackSelectPreferance
    }

    interface CallbackSelectPreferance{
        fun callback(string: String,data:ArrayList<AccomodationPreferanceModel>)
    }

}