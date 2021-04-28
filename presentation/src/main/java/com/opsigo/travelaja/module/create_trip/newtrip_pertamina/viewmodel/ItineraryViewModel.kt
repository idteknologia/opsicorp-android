package com.opsigo.travelaja.module.create_trip.newtrip_pertamina.viewmodel

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import opsigo.com.datalayer.model.result.City
import opsigo.com.datalayer.model.result.Result
import opsigo.com.datalayer.network.ServiceApi

class ItineraryViewModel(private val repository: CityRepository) : ViewModel() {
    val isInternational = ObservableBoolean(false)

    private var itineraries = mutableListOf(Itinerary())

    fun getItinerary() : Itinerary  = itineraries[0]

    fun setOriginFrom(origin : String ){
        getItinerary().Origin = origin
    }

    fun setDestination(destination : String){
        getItinerary().Destination = destination
    }

    fun setTypeTransportation(pos : Int){
        getItinerary().Transportation = pos
    }


    fun setStartDate(date : String){
        getItinerary().DepartureDateView = date
    }

    fun checkedInternational(isChecked : Boolean){
        if (isInternational.get() != isChecked){
            setOriginFrom("")
            setDestination("")
        }
        isInternational.set(isChecked)
    }

    private val _cities = MutableLiveData<List<City>>()
    val cities : LiveData<List<City>> = _cities


    fun fetchCities(isInternational : Boolean){
        viewModelScope.launch {
            val result = repository.getCities()
            computeResultCities(result,isInternational)
        }
    }

    private fun computeResultCities(result : Result<List<City>>,isInternational : Boolean){
        if (result is Result.Success){
            if (!isInternational){
                val list = result.data.filter { it.countryCode == "ID" }
                _cities.postValue(list)
            }else {
                _cities.postValue(result.data)
            }
        }
    }


}


@Suppress("UNCHECKED_CAST")
class ItineraryViewModelFactory(private val context: Context) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItineraryViewModel::class.java)) {
            val api = ServiceApi.createRequest(context)
            val repository = CityDefaultRepository(api)
            return ItineraryViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
