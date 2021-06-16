package com.opsicorp.travelaja.feature_flight.multi_city

import android.view.View
import java.lang.Exception
import android.view.ViewGroup
import android.content.Context
import android.widget.ImageView
import android.view.LayoutInflater
import com.squareup.picasso.Picasso
import com.opsigo.travelaja.utility.Globals
import com.opsicorp.travelaja.feature_flight.R
import androidx.recyclerview.widget.RecyclerView
import com.mobile.travelaja.utility.DateConverter
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import kotlinx.android.synthetic.main.multi_city_list_adapter.view.*
import opsigo.com.domainlayer.model.accomodation.flight.ResultListFlightModel

class FlightMultiCityListAdapter(val context: Context): RecyclerView.Adapter<FlightMultiCityListAdapter.ViewHolder>() {

    lateinit var onclick: OnclickListenerRecyclerView
    var items = ArrayList<ResultListFlightModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.multi_city_list_adapter, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnclickListener(onclickListenerRecyclerView: OnclickListenerRecyclerView){
        this.onclick = onclickListenerRecyclerView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items.get(position)

        holder.itemView.tv_flight_number.text   = "Flight ${position+1}"
        holder.itemView.tv_flight_date.text     = DateConverter().getDate(data.departDate, "yyyy-MM-dd", "dd MMM yyyy") //"17 Jan 2021"
        holder.itemView.tv_flight_time.text     = "${data.departTime} - ${data.arriveTime}"//"04:00 - 06:00"
        holder.itemView.tv_flight_airline.text  = "${data.originAirport} - ${data.nameClass}" //"Sriwijaya Air - Economy - Subclass-X"

        if (data.titleAirline.isNotEmpty()){
            holder.itemView.tvSelectFlight2.text            = "Change"
            holder.itemView.dotFlight.visibility            = View.VISIBLE
            holder.itemView.tv_flight_time.visibility       = View.VISIBLE
            holder.itemView.tv_flight_airline.visibility    = View.VISIBLE
            holder.itemView.img_airline2.visibility         = View.VISIBLE
            setImage(holder.itemView.img_airline2,data.imgAirline)
            holder.itemView.tv_flight_code2.text            = "${data.origin} - ${data.destination}"//"CGK - KNO"
            holder.itemView.tv_price.text                   = "Sub Total - ${Globals.formatAmount(data.price)} IDR/pax"
        }
        else {
            holder.itemView.tvSelectFlight2.text            = "Select"
            holder.itemView.dotFlight.visibility            = View.GONE
            holder.itemView.tv_flight_time.visibility       = View.GONE
            holder.itemView.tv_flight_airline.visibility    = View.GONE
            holder.itemView.img_airline2.visibility         = View.GONE
            holder.itemView.tv_flight_code2.text            = "${data.origin} - ${data.destination}"//"CGK - KNO"
            holder.itemView.tv_price.text                   = "${data.originName} - ${data.destinationName}"
        }

        holder.itemView.setOnClickListener{
            onclick.onClick(SELECT_FLIGHT,position)
        }
    }

    private fun setImage(imgAirline2: ImageView,url:String) {
        try {
            Picasso.get()
                    .load(url)
                    .fit()
                    .centerInside()
                    .into(imgAirline2)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun setData(data: ArrayList<ResultListFlightModel>) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }
}