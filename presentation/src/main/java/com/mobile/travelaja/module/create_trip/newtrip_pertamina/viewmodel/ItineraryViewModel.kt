package com.mobile.travelaja.module.create_trip.newtrip_pertamina.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import opsigo.com.datalayer.model.result.City
import opsigo.com.datalayer.model.result.Result

class ItineraryViewModel(private val repository: CityRepository) : ViewModel() {
    val isInternational = ObservableBoolean(false)

    var isRemoveVisible = ObservableBoolean(false)

    var itineraries = mutableListOf(Itinerary())


    fun isCompleteItems(dateRange : Int) : Boolean{
        itineraries.forEach {
            if (it.Transportation == 2 ){
                if (dateRange > 1 && it.isEmptyField()){
                    return false
                }
                if (dateRange == 1 && !it.isComplete() ){
                    return false
                }
            }
            if (it.Transportation == 1 && !it.isComplete()){
                return false
            }
        }
        return true
    }

    fun isEmptyItems() : Boolean {
        itineraries.forEach {
            if (it.isEmptyField()){
                return true
            }
        }
        return false
    }

    fun getItinerary(pos: Int): Itinerary = itineraries[pos]

    fun setOriginFrom(origin: String, pos: Int) {
        getItinerary(pos).setOriginFrom(origin)
    }

    fun setDestination(destination: String, pos: Int) {
        getItinerary(pos).setDestinationTo(destination)
    }

    fun setStartDate(date: String, pos: Int) {
        getItinerary(pos).setDate(date)
    }

    fun setTypeTransportation(type: Int,pos: Int) {
        getItinerary(pos).setTransport(type)
    }

    fun checkedInternational(isChecked: Boolean) {
        if (isInternational.get() != isChecked) {
            itineraries.forEachIndexed { index, itinerary ->
                setOriginFrom("", index)
                setDestination("", index)
            }
        }
        isInternational.set(isChecked)
    }

    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>> = _cities

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error


    fun fetchCities(isInternational: Boolean) {
        viewModelScope.launch {
            val result = repository.getCities()
            computeResultCities(result, isInternational)
        }
    }

    private fun computeResultCities(result: Result<List<City>>, isInternational: Boolean) {
        if (result is Result.Success) {
            if (!isInternational) {
                val list = result.data.filter { it.countryCode == "ID" }
                _cities.postValue(list)
            } else {
                _cities.postValue(result.data)
            }
        } else {
            _error.postValue((result as Result.Error).exception)
        }
    }
}

