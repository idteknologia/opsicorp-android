package com.opsicorp.travelaja.feature_flight.booking_contact

import android.content.Context
import androidx.core.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.view.LayoutInflater
import com.mobile.travelaja.utility.*
import com.opsicorp.travelaja.feature_flight.R
import kotlinx.android.synthetic.main.item_booking_adapter_adult.view.*
import kotlinx.android.synthetic.main.item_booking_adapter_infant.view.*
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.booking_contact.BookingContactAdapterModel
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation

class BookingContactFlightAdapter(val context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    lateinit var items: ArrayList<BookingContactAdapterModel>

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {

        return when (viewType) {

            VIEW_ADULT -> BookingAdultAdapter(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_booking_adapter_adult, parent, false))

            else -> BookingInfantAdapter(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_booking_adapter_infant, parent, false))
        }

    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView) {
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_ADULT -> (holder as BookingAdultAdapter).bind(items[position], position)
            VIEW_INFANT -> (holder as BookingInfantAdapter).bind(items[position], position)
        }
    }

    inner class BookingAdultAdapter internal constructor(itemView: View) : ViewHolder(itemView) {

        fun bind(data: BookingContactAdapterModel, position: Int) {
            val radiobutton = ArrayList<RadioButton>()
            radiobutton.add(itemView.checkboxIdCart)
            radiobutton.add(itemView.checkboxPassport)
            radiobutton.add(itemView.checkSim)

            /*onclick.onClick(Constants.BTN_ID_CART,position)*/
            when (data.checktype) {
                Constants.TYPE_KTP -> {
                    setCheckRadioButton(radiobutton, 0)
                }
                Constants.TYPE_PASSPORT -> {
                    setCheckRadioButton(radiobutton, 1)
                }
                Constants.TYPE_SIM -> {
                    setCheckRadioButton(radiobutton, 2)
                }
                else -> {
                    setCheckRadioButton(radiobutton, -1)
                }
            }

            itemView.line_id_cart.setOnClickListener {
                onclick.onClick(Constants.BTN_ID_CART, position)
                setCheckRadioButton(radiobutton, 0)
            }

            if (itemView.checkboxIdCart.isChecked) {
                itemView.btn_id_cart.text = "Edit"
                itemView.btn_id_cart.setTextColor(ContextCompat.getColor(context, R.color.green_price))
            } else {
                itemView.btn_id_cart.text = "Add"
                itemView.btn_id_cart.setTextColor(ContextCompat.getColor(context, R.color.orange_price))
            }

            itemView.line_passport.setOnClickListener {
                onclick.onClick(Constants.BTN_PASSPORT, position)
                setCheckRadioButton(radiobutton, 1)
            }

            if (itemView.checkboxPassport.isChecked) {
                itemView.btn_id_passport.text = "Edit"
                itemView.btn_id_passport.setTextColor(ContextCompat.getColor(context, R.color.green_price))
            } else {
                itemView.btn_id_passport.text = "Add"
                itemView.btn_id_passport.setTextColor(ContextCompat.getColor(context, R.color.orange_price))
            }

            itemView.line_sim.setOnClickListener {
                onclick.onClick(Constants.BTN_SIM, position)
                setCheckRadioButton(radiobutton, 2)
            }

            if (itemView.checkSim.isChecked) {
                itemView.btn_id_sim.text = "Edit"
                itemView.btn_id_sim.setTextColor(ContextCompat.getColor(context, R.color.green_price))
            } else {
                itemView.btn_id_sim.text = "Add"
                itemView.btn_id_sim.setTextColor(ContextCompat.getColor(context, R.color.orange_price))
            }

            if (data.idcard.fullname.isEmpty()){
                itemView.name_passanger_by_ktp.text = context.getString(R.string.string_requaired_input)
                itemView.name_passanger_by_ktp.setTextColor(context.resources.getColor(R.color.colorRedUndo))
            }
            else {
                itemView.name_passanger_by_ktp.text = data.idcard.fullname
                itemView.name_passanger_by_ktp.setTextColor(ContextCompat.getColor(context, R.color.gray_50_subtitle))
            }

            if (!data.pasport.fullname.isNullOrEmpty()) {
                itemView.name_passanger_by_passport.setTextColor(ContextCompat.getColor(context, R.color.gray_50_subtitle))
                itemView.name_passanger_by_passport.text = data.pasport.fullname
            }
            if (!data.sim.name.isNullOrEmpty()) {
                itemView.name_passanger_by_sim.setTextColor(ContextCompat.getColor(context, R.color.gray_50_subtitle))
                itemView.name_passanger_by_sim.text = data.sim.name
            }

            itemView.btn_id_cart.setOnClickListener { onclick.onClick(Constants.BTN_ID_CART, position) }
            itemView.btn_id_passport.setOnClickListener { onclick.onClick(Constants.BTN_PASSPORT, position) }
            itemView.btn_id_sim.setOnClickListener { onclick.onClick(Constants.BTN_SIM, position) }

            itemView.number_data_adult.text = (position + 1).toString()


            if (!data.ssr.dataBagage.isNullOrEmpty()) {
                itemView.card_baggage.visibility = View.VISIBLE
            } else {
                itemView.card_baggage.visibility = View.GONE
            }


            if (!data.ssr.dataSsr.isNullOrEmpty()) {
                itemView.card_ssr.visibility = View.VISIBLE
            } else {
                itemView.card_ssr.visibility = View.GONE
            }

            if (!data.ssr.bagaggeItemSelected.filter { it.ssrType=="1" }.isNullOrEmpty()){
                itemView.card_baggage.setBackgroundResource(R.drawable.card_background_corner_green)
                itemView.tvBaggageTotalSelect.text = data.ssr.bagaggeItemSelected.find { it.ssrType=="1" }?.ssrName?.replace("+", "")?.replace("Baggage", "")?.replace("Checked", "")?.replace("baggage", "")
                itemView.tvBaggageBooking.setTextColor(ContextCompat.getColor(context, R.color.green_price))
            } else {
                itemView.card_baggage.setBackgroundResource(R.drawable.card_background_corner_grey)
                itemView.tvBaggageTotalSelect.text = "0 Kg"
                itemView.tvBaggageBooking.setTextColor(ContextCompat.getColor(context, R.color.black))
            }

            if (!data.ssr.ssrSelected.isNullOrEmpty()){
                itemView.card_ssr.setBackgroundResource(R.drawable.card_background_corner_green)
                itemView.tvSsrTotalSelect.text = "${data.ssr.ssrSelected.size} Selected"
                itemView.tvSsrBooking.setTextColor(ContextCompat.getColor(context, R.color.green_price))
            } else {
                itemView.card_ssr.setBackgroundResource(R.drawable.card_background_corner_grey)
                itemView.tvSsrTotalSelect.text = "Meals, etc"
                itemView.tvSsrBooking.setTextColor(ContextCompat.getColor(context, R.color.black))
            }

            if (data.ssr.dataBagage.isNotEmpty()){
                itemView.line_baggage.visibility             = View.VISIBLE
                itemView.line_vertical_aditional.visibility  = View.VISIBLE
            }
            else {
                itemView.line_baggage.visibility            = View.GONE
                itemView.line_vertical_aditional.visibility = View.GONE
            }

            if (items.size-1==position) {
                itemView.line_vertical.visibility           = View.GONE
                itemView.line_vertical_aditional.visibility = View.GONE
            } else {
                itemView.line_vertical.visibility           = View.VISIBLE
                itemView.line_vertical_aditional.visibility = View.GONE
            }

            val datalist = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)

            try {
                if (datalist.dataFlight[0].titleAirline.equals("Garuda Indonesia")){
                    itemView.card_frequency_flayer.visible()
                } else {
                    itemView.card_frequency_flayer.gone()
                }
            }catch (e:Exception){}

            if (itemView.card_baggage.visibility.equals(0) || itemView.card_ssr.visibility.equals(0) || itemView.card_frequency_flayer.visibility.equals(0)) {
                itemView.tv_additional.visible()
            } else {
                itemView.tv_additional.gone()
            }

            itemView.card_baggage.setOnClickListener {
                onclick.onClick(Constants.KEY_ACTIVITY_BAGAGE, position)
            }
            itemView.card_ssr.setOnClickListener {
                onclick.onClick(Constants.KEY_ACTIVITY_SSR, position)
            }
            itemView.card_frequency_flayer.setOnClickListener {
                onclick.onClick(Constants.KEY_ACTIVITY_FREQUENCE, position)
            }
        }

    }

    inner class BookingInfantAdapter internal constructor(itemView: View) : ViewHolder(itemView) {

        fun bind(data: BookingContactAdapterModel, position: Int) {
            itemView.number_data_infant.text = (position + 1).toString()

            if (position == (items.size - 1)) {
                itemView.line_vertical_infant.visibility = View.GONE
            } else {
                itemView.line_vertical_infant.visibility = View.VISIBLE
            }
        }

    }

    fun setData(data: ArrayList<BookingContactAdapterModel>) {
        items = data
        notifyDataSetChanged()
    }

    companion object {
        val VIEW_ADULT = 1
        val VIEW_INFANT = 2
    }


    open inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)


    override fun getItemViewType(position: Int): Int {
        return when (items.get(position).typeContact) {
            Constants.ADULT -> VIEW_ADULT
            else -> VIEW_INFANT
        }
    }

    fun setCheckRadioButton(data: ArrayList<RadioButton>, position: Int) {
        data.forEachIndexed { index, radioButton ->
            radioButton.isChecked = position == index
        }
    }
}