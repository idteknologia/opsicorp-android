package com.opsigo.travelaja.module.home.presenter

import android.content.Context
import androidx.lifecycle.*
import com.opsigo.travelaja.module.home.repository.HomeDefaultRepository
import com.opsigo.travelaja.module.home.repository.HomeRepository
import kotlinx.coroutines.launch
import opsigo.com.datalayer.network.ServiceApi
import opsigo.com.domainlayer.model.trip.TripResult
import opsigo.com.datalayer.model.result.Result
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(val repository: HomeRepository) : ViewModel() {
    private val _trip = MutableLiveData<TripResult>()
    val trip: LiveData<TripResult> = _trip

    private val _error = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading : LiveData<Boolean> = _isLoading

    fun getTrip() {
        if (trip.value != null){
            return
        }
        _isLoading.value = true
        fetchTrip()
    }

    private fun fetchTrip(){
        val sdfOutput = SimpleDateFormat("yyyy-MM-dd")
        val dateNow = sdfOutput.format(Date())
        viewModelScope.launch {
            val query = mutableMapOf("Size" to "5", "Index" to "1", "OrderBy" to "Code", "Direction" to "1")
            query["CreatedDateFrom"] = dateNow
            query["CreatedDateTo"] = dateNow
            query["Status"] = "4"
            val result = repository.getTrip(query)
            computeTripResult(result)
        }
    }

    private fun computeTripResult(result: Result<TripResult>) {
        if (result is Result.Success) {
            _trip.value = result.data
            _isLoading.value = false
        } else {
            if (trip.value == null)
                _error.value = true
            _isLoading.value = false
        }
    }
}

@Suppress("UNCHECKED_CAST")
class DefaultViewModelFactory(private val isFake: Boolean, private val context: Context) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val api = ServiceApi.createRequest(context)
            val repository: HomeRepository = if (isFake) {
                /*FakeHomeRepository(context)*/
                HomeDefaultRepository(api)
            } else {
                HomeDefaultRepository(api)
            }
            return HomeViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
