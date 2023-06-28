package com.example.githubuser.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.ApiConfig
import com.example.githubuser.responses.DetailUserResponse
import com.example.githubuser.responses.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _followList = MutableLiveData<List<ItemsItem>>()
    val followList: LiveData<List<ItemsItem>> = _followList

    private val _login = MutableLiveData<String>()
    val login: LiveData<String> = _login

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser


    fun setLogin(login: String) {

        _login.value = login
        detailUser()

    }

    private fun detailUser() {

        _isLoading.value = true

        val client = ApiConfig.getApiService().detailUser(login.value!!)
        client.enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {

                    _detailUser.value = response.body()

                }
                else {
                    Log.e("DetailViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {

                _isLoading.value = false
                Log.e("DetailViewModel", "onFailure: ${t.message.toString()}")

            }

        })
    }

    fun follUser(isFollower : Boolean, login: String) {

        _isLoading.value = true

        val client = if (isFollower)
            ApiConfig.getApiService().getFollowers(login)
        else
            ApiConfig.getApiService().getFollowing(login)

        client.enqueue(object : Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {

                    _followList.value = response.body()

                }
                else {
                    Log.e("DetailViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {

                _isLoading.value = false
                Log.e("DetailViewModel", "onFailure: ${t.message.toString()}")

            }

        })
    }

}