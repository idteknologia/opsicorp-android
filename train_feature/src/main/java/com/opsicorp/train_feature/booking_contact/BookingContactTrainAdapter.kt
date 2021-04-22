package com.opsicorp.train_feature.booking_contact

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.opsicorp.train_feature.R
import opsigo.com.domainlayer.model.booking_contact.BookingContactAdapterModel
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.item_booking_train_adapter_adult.view.*
import kotlinx.android.synthetic.main.item_booking_train_adapter_infant.view.*

class BookingContactTrainAdapter (val context: Context, private var items: ArrayList<BookingContactAdapterModel>): androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView

    override fun getItemCount(): Int { return items.size }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {

        return when (viewType){

            VIEW_ADULT -> BookingAdultAdapter(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_booking_train_adapter_adult, parent, false))

            else -> BookingInfantAdapter(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_booking_train_adapter_infant, parent, false))
        }

    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_ADULT -> (holder as BookingAdultAdapter).bind(items[position],position)
            VIEW_INFANT -> (holder as BookingInfantAdapter).bind(items[position],position)
        }
    }

    inner class BookingAdultAdapter internal constructor(itemView: View) : ViewHolder(itemView) {

        fun bind(data: BookingContactAdapterModel, position: Int) {
            val radiobutton = ArrayList<RadioButton>()
            radiobutton.add(itemView.checkboxIdCart)
            radiobutton.add(itemView.checkboxPassport)
            radiobutton.add(itemView.checkSim)

            when(data.checktype){
                Constants.TYPE_KTP -> {
                    setCheckRadioButton(radiobutton,0)
                }
                Constants.TYPE_PASSPORT -> {
                    setCheckRadioButton(radiobutton,1)
                }
                Constants.TYPE_SIM -> {
                    setCheckRadioButton(radiobutton,2)
                }
                else -> {
                    setCheckRadioButton(radiobutton,0)

                }
            }


            itemView.line_id_cart.setOnClickListener {
                onclick.onClick(Constants.BTN_ID_CART,position)
                setCheckRadioButton(radiobutton,0)
            }
            itemView.line_passport.setOnClickListener {
                onclick.onClick(Constants.BTN_PASSPORT,position)
                setCheckRadioButton(radiobutton,1)
            }
            itemView.line_sim.setOnClickListener {
                onclick.onClick(Constants.BTN_SIM,position)
                setCheckRadioButton(radiobutton,2)
            }

            if (position==(items.size-1)){
                itemView.line_vertical.visibility = View.GONE
            }
            else{
                itemView.line_vertical.visibility = View.VISIBLE
            }


            if (data.idcard.fullname.isEmpty()){
                itemView.name_passanger_by_ktp.text      = context.getString(R.string.string_requaired_input)
                itemView.name_passanger_by_ktp.setTextColor(context.resources.getColor(R.color.colorRedUndo))
            }
            else {
                itemView.name_passanger_by_ktp.text      = data.idcard.fullname
                itemView.name_passanger_by_ktp.setTextColor(context.resources.getColor(R.color.gray_50_subtitle))
            }


            if (!data.pasport.firstName.isNullOrEmpty()){
                itemView.name_passanger_by_passport.setTextColor(context.resources.getColor(R.color.gray_50_subtitle))
                itemView.name_passanger_by_passport.text = data.pasport.firstName
            }
            if(!data.sim.name.isNullOrEmpty()) {
                itemView.name_passanger_by_sim.setTextColor(context.resources.getColor(R.color.gray_50_subtitle))
                itemView.name_passanger_by_sim.text      = data.sim.name
            }

            itemView.btn_id_cart.setOnClickListener { onclick.onClick(Constants.BTN_ID_CART,position) }
            itemView.btn_id_passport.setOnClickListener { onclick.onClick(Constants.BTN_PASSPORT,position) }
            itemView.btn_id_sim.setOnClickListener { onclick.onClick(Constants.BTN_SIM,position) }

            itemView.number_data_adult.text = (position+1).toString()

            if (data.ssr.isBagage){
                itemView.card_baggage.visibility = View.VISIBLE
            }
            else{
                itemView.card_baggage.visibility = View.GONE
            }

            if (data.ssr.isHaveMeal||data.ssr.isHaveDrink||data.ssr.isHaveSport){
                itemView.card_ssr.visibility = View.VISIBLE
            }
            else{
                itemView.card_ssr.visibility = View.GONE
            }

            itemView.card_baggage.setOnClickListener {
                onclick.onClick(Constants.KEY_ACTIVITY_BAGAGE,position)
            }
            itemView.card_ssr.setOnClickListener {
                onclick.onClick(Constants.KEY_ACTIVITY_SSR,position)
            }
            itemView.card_frequency_flayer.setOnClickListener {
                onclick.onClick(Constants.KEY_ACTIVITY_FREQUENCE,position)
            }
        }

    }

    inner class BookingInfantAdapter internal constructor(itemView: View) : ViewHolder(itemView) {

        fun bind(data: BookingContactAdapterModel, position: Int) {
            itemView.number_data_infant.text = (position+1).toString()

            if (position==(items.size-1)){
                itemView.line_vertical_infant.visibility = View.GONE
            }
            else{
                itemView.line_vertical_infant.visibility = View.VISIBLE
            }
        }

    }

    fun setData(data: ArrayList<BookingContactAdapterModel>) {
        items = data
        notifyDataSetChanged()
    }

    companion object {
        val VIEW_ADULT  = 1
        val VIEW_INFANT = 2
    }


    open inner class ViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)


    override fun getItemViewType(position: Int): Int {
        return when (items.get(position).typeContact){
            Constants.ADULT -> VIEW_ADULT
            else -> VIEW_INFANT
        }
    }

    fun setCheckRadioButton(data:ArrayList<RadioButton>,position:Int){
        data.forEachIndexed { index, radioButton ->
            radioButton.isChecked = position==index
        }
    }
}