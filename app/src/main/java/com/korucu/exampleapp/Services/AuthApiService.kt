package com.korucu.exampleapp.Services

import com.korucu.exampleapp.Dtos.UserLogin
import com.korucu.exampleapp.Dtos.UserRegister
import com.korucu.exampleapp.Models.UpdateResponse
import com.korucu.exampleapp.Models.User
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AuthApiService {

    private val BASE_URL = "https://apipass.yusufkorucu.com"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(AuthAPI::class.java)

    fun login(userLogin: UserLogin): Single<User> {
        return api.login(userLogin)
    }

    fun register(userRegister: UserRegister): Single<UpdateResponse> {
        return api.register(userRegister)
    }
}