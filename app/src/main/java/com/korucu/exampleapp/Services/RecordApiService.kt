package com.korucu.exampleapp.Services

import android.app.Application
import com.korucu.exampleapp.Dtos.RecordAdd
import com.korucu.exampleapp.Models.Record
import com.korucu.exampleapp.Models.RecordDetail
import com.korucu.exampleapp.Models.UpdateResponse
import com.korucu.exampleapp.Utils.CustomSharedPreferences
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RecordApiService(application: Application) {
    private val BASE_URL = "https://apipass.yusufkorucu.com"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(RecordAPI::class.java)

    init {
        println("*******************************************************************")
    }
    private var customSharedPreferences = CustomSharedPreferences(application)
    private val tokenString = customSharedPreferences.getToken()

    fun getRecordList(): Single<List<Record>> {
        return api.getRecordList("Bearer " + tokenString)
    }

    fun getSingleRecord(id:Int): Single<RecordDetail> {
        return api.getSingleRecord(id,"Bearer " + tokenString)
    }

    fun addRecord(record:RecordAdd): Single<UpdateResponse> {
        return api.addRecord(record, "Bearer " + tokenString)
    }

    fun updateRecord(record:RecordDetail): Single<UpdateResponse> {
        return api.updateRecord(record,"Bearer " + tokenString)
    }

    fun deleteSingleRecord(id: Int): Single<UpdateResponse> {
        return api.deleteSinglePano(id, "Bearer " + tokenString)
    }
}