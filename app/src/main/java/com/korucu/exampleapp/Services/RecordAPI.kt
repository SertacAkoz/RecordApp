package com.korucu.exampleapp.Services

import androidx.room.Delete
import com.korucu.exampleapp.Dtos.RecordAdd
import com.korucu.exampleapp.Models.Record
import com.korucu.exampleapp.Models.RecordDetail
import com.korucu.exampleapp.Models.UpdateResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RecordAPI {

    @GET("/Record/GetRecordList")
    fun getRecordList(@Header("Authorization") authorization:String): Single<List<Record>>

    @GET("/Record/{id}")
    fun getSingleRecord(@Path("id") id:Int?, @Header("Authorization") authorization:String): Single<RecordDetail>

    @POST("/Record/UpdateRecord")
    fun updateRecord(@Body record:RecordDetail, @Header("Authorization") authorization:String): Single<UpdateResponse>

    @POST("/Record/CreateNewRecord")
    fun addRecord(@Body record:RecordAdd, @Header("Authorization") authorization:String): Single<UpdateResponse>

    @POST("/Record/DeleteRecord/{id}")
    fun deleteSinglePano(@Path("id") id:Int?, @Header("Authorization") authorization:String): Single<UpdateResponse>
}