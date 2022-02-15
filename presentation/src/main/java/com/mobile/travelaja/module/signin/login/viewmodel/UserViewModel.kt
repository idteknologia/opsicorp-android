package com.mobile.travelaja.module.signin.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import opsigo.com.datalayer.model.signin.LoginEntity
import opsigo.com.datalayer.model.result.Result

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _login = MutableLiveData<LoginEntity>()
    val login: LiveData<LoginEntity> = _login

    private val _error = MutableLiveData<Throwable>()
    val isError: LiveData<Throwable> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading : LiveData<Boolean> = _isLoading

    fun onLogin(url: String,body : MutableMap<String,Any?>){
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.onLogin(url,body)
            computeLoginEntity(result)
        }
    }

    private fun computeLoginEntity(result : Result<LoginEntity>){
        if (result is Result.Success){
            _login.value = result.data
        }else {
            val error = result as Result.Error
            _error.value = error.exception
        }
        _isLoading.value = false

    }


}