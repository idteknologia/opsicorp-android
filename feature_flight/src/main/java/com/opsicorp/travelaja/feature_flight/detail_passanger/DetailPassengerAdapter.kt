package com.opsicorp.travelaja.feature_flight.detail_passanger

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.opsicorp.travelaja.feature_flight.R
import com.opsigo.travelaja.utility.Globals
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.detail_passanger_adapter_new.view.*

class DetailPassengerAdapter (val context: Context): androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    lateinit var items: ArrayList<DetailPassengerModel>

    val TYPE_ADULT  = "Adult"
    val TYPE_CHILD  = "Child"
    val TYPE_INFANT = "Infant"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return when (viewType){

            VIEW_TYPE_ADULT -> AdultHeader(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.detail_passanger_adapter_new, parent, false))

            VIEW_TYPE_CHILD -> ChildHeader(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.detail_passanger_adapter_new, parent, false))

            else            -> InfantHeader(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.detail_passanger_adapter_new, parent, false))
        }
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        when (items.get(position).age) {

            TYPE_ADULT -> {
                (holder as AdultHeader).bind(items[position],position)
            }
            TYPE_CHILD ->{
                (holder as ChildHeader).bind(items[position],position)
            }
            TYPE_INFANT-> {
                (holder as InfantHeader).bind(items[position],position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items.get(position).age){
            TYPE_ADULT  -> VIEW_TYPE_ADULT
            TYPE_CHILD  -> VIEW_TYPE_CHILD
            else        -> VIEW_TYPE_INFANT
        }
    }

    inner class AdultHeader internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(data: DetailPassengerModel, position: Int) {

            itemView.tv_title.text = "Adult"

            itemView.img_down.setOnClickListener {
                if(itemView.expandable.isExpanded){
                    itemView.expandable.collapse()
                }
                else{
                    itemView.expandable.expand()
                }
            }

            saveDataFromEdittext(itemView,position)

//            data.firstname = itemView.et_first_name.text.toString()
//            data.lastname  = itemView.et_last_name.text.toString()
//            data.mobileNumber = itemView.et_phone.text.toString()
//            data.email     = itemView.et_email.text.toString()

        }

    }

    inner class ChildHeader internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(data: DetailPassengerModel, position: Int) {


            itemView.tv_title.text = "Child"

            itemView.img_down.setOnClickListener {
                if(itemView.expandable.isExpanded){
                    itemView.expandable.collapse()
                }
                else{
                    itemView.expandable.expand()
                }
            }

            saveDataFromEdittext(itemView,position)

        }

    }

    inner class InfantHeader internal constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {


        fun bind(data: DetailPassengerModel, position: Int) {

            itemView.tv_title.text = "Infant"


            itemView.img_down.setOnClickListener {
                if(itemView.expandable.isExpanded){
                    itemView.expandable.collapse()
                }
                else{
                    itemView.expandable.expand()
                }
            }

            saveDataFromEdittext(itemView,position)
        }

    }

    fun saveDataFromEdittext(itemView: View,position: Int){

        val line = ArrayList<RelativeLayout>()
        val textviews = ArrayList<TextView>()

        line.clear()
        textviews.clear()

        line.add(itemView.line_mrs)
        line.add(itemView.line_ms)
        line.add(itemView.line_mr)
        textviews.add(itemView.tv_mrs)
        textviews.add(itemView.tv_ms)
        textviews.add(itemView.tv_mr)

        itemView.line_mrs.setOnClickListener {
            items.get(position).title = "Mrs."
            Globals.changeViewButton(textviews,line,0,context)
        }

        itemView.line_ms.setOnClickListener {
            items.get(position).title = "Ms."
            Globals.changeViewButton(textviews,line,1,context)
        }

        itemView.line_mr.setOnClickListener {
            items.get(position).title = "Mr."
            Globals.changeViewButton(textviews,line,2,context)
        }

        itemView.et_first_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().length>0){
                    items.get(position).firstname = p0.toString()
                }
            }
        })


        itemView.et_last_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().length>0){
                    items.get(position).lastname = p0.toString()
                }
            }
        })


        itemView.et_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().length>0){
                    items.get(position).mobileNumber = p0.toString()
                }
            }
        })


        itemView.et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().length>0){
                    items.get(position).email = p0.toString()
                }
            }
        })
    }

    fun setData(data: ArrayList<DetailPassengerModel>) {
        items = data
        notifyDataSetChanged()
    }

    open inner class ViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)

    companion object {
        val VIEW_TYPE_ADULT  = 1
        val VIEW_TYPE_CHILD  = 2
        val VIEW_TYPE_INFANT = 3
    }
}