package com.opsigo.travelaja.module.create_trip.newtrip_pertamina.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import opsigo.com.datalayer.model.result.City
import opsigo.com.datalayer.model.result.Result

class ItineraryViewModel(private val repository: CityRepository) : ViewModel() {
    val isInternational = ObservableBoolean(false)

    var itineraries = mutableListOf(Itinerary())

    fun getItinerary() : Itinerary  = itineraries[0]

    fun setOriginFrom(origin : String ){
        getItinerary().setOriginFrom(origin)
    }

    fun setDestination(destination : String){
        getItinerary().setDestinationTo(destination)
    }

    fun setTypeTransportation(pos : Int){
        getItinerary().setTransport(pos)
    }

    fun setStartDate(date : String){
        getItinerary().setDate(date)
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

    private val _error = MutableLiveData<Throwable>()
    val error : LiveData<Throwable> = _error


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
        }else {
            _error.postValue((result as Result.Error).exception)
        }
    }
}

