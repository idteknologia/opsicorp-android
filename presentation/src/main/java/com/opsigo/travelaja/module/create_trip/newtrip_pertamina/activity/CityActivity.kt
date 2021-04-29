package com.opsigo.travelaja.module.create_trip.newtrip_pertamina.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.opsigo.travelaja.R
import com.opsigo.travelaja.databinding.ActivityCityBinding
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.adapter.CityAdapter
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.viewmodel.ItineraryViewModel
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.viewmodel.ItineraryViewModelFactory
import com.opsigo.travelaja.utility.Utils

class CityActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCityBinding
    private lateinit var viewModel : ItineraryViewModel
    private lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_city)
        viewModel  = ViewModelProvider(this, ItineraryViewModelFactory(this)).get(ItineraryViewModel::class.java)
        setRecycler()
        val isInternational = intent.getBooleanExtra(SelectTripRoutePertaminaActivity.IS_INTERNATIONAL,false)
        viewModel.fetchCities(isInternational)
        viewModel.cities.observe(this){
            adapter.list = it
            adapter.notifyDataSetChanged()
        }
        viewModel.error.observe(this){
            Utils.handleErrorMessage(this,it){ errorString ->
                Snackbar.make(binding.root,errorString, Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.txt_try_again)){
                    viewModel.fetchCities(isInternational)
                }.show()
            }
        }
        findViewById<SearchView>(R.id.searchView).setOnQueryTextListener(searchListener())
        findViewById<Button>(R.id.buttonClose).setOnClickListener {
            finish()
        }
    }

    private fun searchListener() = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            search(newText!!)
            return false
        }
    }

    private fun search(query : String){
        val list = viewModel.cities.value?.filter { it.cityName.contains(query,true) || it.countryName.contains(query,true) }
        list?.let {
            adapter.list = list
            adapter.notifyDataSetChanged()
        }
    }

    private fun setRecycler(){
        adapter = CityAdapter(activity = this)
        findViewById<RecyclerView>(R.id.rvCity).also {
            it.adapter = adapter
            it.addItemDecoration(DividerItemDecoration(this,RecyclerView.VERTICAL))
        }
    }

    companion object {
        const val RESULT = "RESULT"
    }
}