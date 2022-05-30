package com.korucu.exampleapp.Services

import com.korucu.exampleapp.Dtos.UserLogin
import com.korucu.exampleapp.Dtos.UserRegister
import com.korucu.exampleapp.Models.UpdateResponse
import com.korucu.exampleapp.Models.User
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface AuthAPI {

//    @GET("/User/Login")
//    fun getPasswordList(@HeaderMap headers: Map<String, String>): Single<List<String>>

    @POST("/User/Login")
    fun login(@Body userLogin: UserLogin):Single<User>

    @POST("/User/CreateUser")
    fun register(@Body userRegister: UserRegister):Single<UpdateResponse>
}