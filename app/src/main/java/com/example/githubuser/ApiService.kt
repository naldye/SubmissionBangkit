package com.example.githubuser

import com.example.githubuser.responses.DetailUserResponse
import com.example.githubuser.responses.ItemsItem
import com.example.githubuser.responses.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") login: String
    ): Call<UserResponse>


    //@FormUrlEncoded
    //@Headers("Authorization: token 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")
    //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
    @GET("users/{login}")
    fun detailUser(
        @Path("login") login: String
    ): Call<DetailUserResponse>

    @GET("users/{login}/followers")
    fun getFollowers(
        @Path("login") login: String
    ): Call<List<ItemsItem>>

    @GET("users/{login}/following")
    fun getFollowing(
        @Path("login") login: String
    ): Call<List<ItemsItem>>
}
