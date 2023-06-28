package com.example.githubuser.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.ApiConfig
import com.example.githubuser.responses.ItemsItem
import com.example.githubuser.responses.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    private val _users = MutableLiveData<List<ItemsItem>>()
    val users : LiveData<List<ItemsItem>> =_users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _login = MutableLiveData<String>()
    var login: LiveData<String> = _login

    companion object{
        private const val TAG = "UserViewModel"
    }

    fun setLogin(login: String) {

        _login.value = login
        findUser()

    }

    private fun findUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(login.value!!)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _users.value = response.body()?.items
                    Log.e(TAG, "onFailure: ${response.body()!!.items}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

    }


}