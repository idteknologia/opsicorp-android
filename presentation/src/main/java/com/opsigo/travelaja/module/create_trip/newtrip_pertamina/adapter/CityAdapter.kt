package com.opsigo.travelaja.module.create_trip.newtrip_pertamina.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.opsigo.travelaja.R
import com.opsigo.travelaja.databinding.ItemCitySelectedBinding
import com.opsigo.travelaja.databinding.SelectRouteTripItemBinding
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.activity.CityActivity
import opsigo.com.datalayer.model.result.City

class CityAdapter(var list: List<City> = mutableListOf(), private val activity: Activity) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {


    inner class CityViewHolder(val v: ItemCitySelectedBinding) : RecyclerView.ViewHolder(v.root) {
        fun onBind(city: City) {
            v.tvCity.text = "${city.cityName}, ${city.countryName}"
            v.tvCity.setOnClickListener {
                val intent = Intent()
                intent.putExtra(CityActivity.RESULT, city.cityName)
                activity.setResult(Activity.RESULT_OK, intent)
                activity.finish()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_city_selected, parent, false)
        return CityViewHolder(ItemCitySelectedBinding.bind(v))
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}