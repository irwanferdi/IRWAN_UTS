package com.example.irwan_uts.data.remote

import com.example.irwan_uts.model.ResponseApiUser
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("api/users")
    fun getListUsers(@Query("page") page: String): Call<ResponseApiUser>

    @GET("api/users/{id}")
    fun getUser(@Path("id") id: String): Call<ResponseApiUser>
}