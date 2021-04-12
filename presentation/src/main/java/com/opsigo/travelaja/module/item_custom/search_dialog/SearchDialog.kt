package com.opsigo.travelaja.module.item_custom.search_dialog

import com.opsigo.travelaja.module.signin.select_nationality.adapter.SelectNationalityAdapter
import opsigo.com.domainlayer.model.create_trip_plane.SelectNationalModel
import com.unicode.kingmarket.Base.BaseBottomSheetDialogFrament
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.DialogFragment
import com.opsigo.travelaja.utility.Globals
import android.content.DialogInterface
import android.annotation.SuppressLint
import android.widget.ImageView
import android.text.TextWatcher
import android.widget.TextView
import android.widget.EditText
import com.opsigo.travelaja.R
import android.text.Editable
import android.view.View

@SuppressLint("ValidFragment")
class SearchDialog : BaseBottomSheetDialogFrament {
    override fun getLayout(): Int { return R.layout.search_dialog_bottom }

    var style = 0
    lateinit var rv_search : androidx.recyclerview.widget.RecyclerView
    var data = ArrayList<SelectNationalModel>()
    val temp = ArrayList<SelectNationalModel>()
    var isActivated  = false

    val adapter by lazy { SelectNationalityAdapter(data)  }
    lateinit var et_filter : EditText
    lateinit var tv_title : TextView

    lateinit var ic_back : ImageView

    override fun onMain(fragment: View) {
        if (style!=0){
            setStyle(androidx.fragment.app.DialogFragment.STYLE_NO_TITLE, style)
        }

        initView(fragment)
        initRecyclerView()
    }

    private fun initView(view: View) {
        rv_search = view.findViewById(R.id.rv_nationality)
        et_filter = view.findViewById(R.id.et_filter)
        tv_title  = view.findViewById(R.id.tv_title)
        ic_back   = view.findViewById(R.id.ic_back)

        ic_back.setOnClickListener{
            dismiss()
        }

        et_filter.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (s.length>0){
                    isActivated = true
                    filterDataAdapter(s.toString())
                }
                else{
                    isActivated = false
                    adapter.setData(data)
                }
            }
        })
    }

    fun setTitle(title:String){
        tv_title.text = title
    }

    fun setHintSearch(name:String){
        et_filter.hint = name
    }

    private fun initRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        rv_search.layoutManager = layoutManager
        rv_search.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rv_search.adapter = adapter
        adapter.setData(data)

        adapter.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when (views){
                    -1 -> {
                        if (isActivated){
                            Globals.delay(1000,object : Globals.DelayCallback{
                                override fun done() {
                                    callback.callback(temp[position])
                                    dismiss()
                                }
                            })
                        }
                        else{
                            Globals.delay(1000,object : Globals.DelayCallback{
                                override fun done() {
                                    callback.callback(data[position])
                                    dismiss()
                                }
                            })
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })

    }

    @SuppressLint("ValidFragment")
    constructor(fullScreen:Boolean,mStyle: Int,mData:ArrayList<SelectNationalModel>){
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
        fun callback(model: SelectNationalModel)
    }

    fun filterDataAdapter(string: String) {
        temp.clear()
        for (n in 0..data.size-1) {
            val d = data.get(n)
            if (d.name.toLowerCase().contains(string.toLowerCase())) {
                val mData = SelectNationalModel()
                mData.name = d.name
                mData.id   = d.id
                temp.add(mData)
            }
        }
        adapter.setData(temp)
    }
}