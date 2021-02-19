package com.opsicorp.travelaja.feature_flight.booking_contact

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.view.LayoutInflater
import com.opsigo.travelaja.utility.*
import android.support.v7.widget.RecyclerView
import com.opsicorp.travelaja.feature_flight.R
import kotlinx.android.synthetic.main.item_booking_adapter_adult.view.*
import kotlinx.android.synthetic.main.item_booking_adapter_infant.view.*
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.booking_contact.BookingContactAdapterModel
import opsigo.com.datalayer.datanetwork.dummy.accomodation.DataListOrderAccomodation

class BookingContactFlightAdapter (val context: Context, private var items: ArrayList<BookingContactAdapterModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    lateinit var datalist: DataListOrderAccomodation

    override fun getItemCount(): Int { return items.size }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType){

            VIEW_ADULT -> BookingAdultAdapter(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_booking_adapter_adult, parent, false))

            else -> BookingInfantAdapter(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_booking_adapter_infant, parent, false))
        }

    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_ADULT -> (holder as BookingAdultAdapter).bind(items[position],position)
            VIEW_INFANT -> (holder as BookingInfantAdapter).bind(items[position],position)
        }
    }

    inner class BookingAdultAdapter internal constructor(itemView: View) : ViewHolder(itemView) {

        fun bind(data: BookingContactAdapterModel, position: Int) {
            datalist = Serializer.deserialize(Globals.DATA_LIST_FLIGHT, DataListOrderAccomodation::class.java)
            val radiobutton = ArrayList<RadioButton>()
            radiobutton.add(itemView.checkboxIdCart)
            radiobutton.add(itemView.checkboxPassport)
            radiobutton.add(itemView.checkSim)

            onclick.onClick(Constants.BTN_ID_CART,position)
            setCheckRadioButton(radiobutton,0)

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
                itemView.line_vertical_aaditional.gone()
            }
            else{
                itemView.line_vertical.visibility = View.VISIBLE
                itemView.line_vertical_aaditional.visible()
            }

            itemView.name_passanger_by_ktp.text      = data.idcart.fullname
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

            if (!datalist.dataFlight[position].dataSSR.dataBagage.isNullOrEmpty()){
                itemView.card_baggage.visibility = View.VISIBLE
            }
            else{
                itemView.card_baggage.visibility = View.GONE
            }

            if (!datalist.dataFlight[position].dataSSR.bagaggeItemSelected.isNullOrEmpty()){
                itemView.card_baggage.setBackgroundResource(R.drawable.card_background_corner_green)
                itemView.tvBaggageTotalSelect.text = datalist.dataFlight[0].dataSSR.bagaggeItemSelected[0].ssrName
                itemView.tvBaggageBooking.setTextColor(ContextCompat.getColor(context, R.color.green_price))
            } else {
                itemView.card_baggage.setBackgroundResource(R.drawable.card_background_corner_grey)
                itemView.tvBaggageTotalSelect.text = "0 Kg"
                itemView.tvBaggageBooking.setTextColor(ContextCompat.getColor(context, R.color.black))
            }

            if (!datalist.dataFlight[position].dataSSR.ssrSelected.isNullOrEmpty()){
                itemView.card_ssr.setBackgroundResource(R.drawable.card_background_corner_green)
                itemView.tvSsrTotalSelect.text = "${datalist.dataFlight[position].dataSSR.ssrSelected.size} Selected"
                itemView.tvSsrBooking.setTextColor(ContextCompat.getColor(context, R.color.green_price))
            } else {
                itemView.card_ssr.setBackgroundResource(R.drawable.card_background_corner_grey)
                itemView.tvSsrTotalSelect.text = "Meals, etc"
                itemView.tvSsrBooking.setTextColor(ContextCompat.getColor(context, R.color.black))
            }

            if (!datalist.dataFlight[position].dataSSR.dataSsr.isNullOrEmpty()){
                itemView.card_ssr.visibility = View.VISIBLE
            }
            else{
                itemView.card_ssr.visibility = View.GONE
            }
            if (datalist.dataFlight[position].titleAirline.equals("Garuda Indonesia")){
                itemView.card_frequency_flayer.visible()
            } else {
                itemView.card_frequency_flayer.gone()
            }

            if (itemView.card_baggage.visibility.equals(0)||itemView.card_ssr.visibility.equals(0)||itemView.card_frequency_flayer.visibility.equals(0)){
                itemView.tv_additional.visible()
            } else {
                itemView.tv_additional.gone()
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


    open inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)


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