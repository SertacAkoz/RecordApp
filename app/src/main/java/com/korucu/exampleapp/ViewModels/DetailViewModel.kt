package com.korucu.exampleapp.ViewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.korucu.exampleapp.Models.Record
import com.korucu.exampleapp.Models.RecordDetail
import com.korucu.exampleapp.Models.UpdateResponse
import com.korucu.exampleapp.Services.RecordApiService
import com.korucu.exampleapp.Utils.CustomSharedPreferences
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailViewModel(application: Application):BaseViewModel(application) {

    private val disposable = CompositeDisposable()
    private val recordApiService = RecordApiService(application)
    private var customSharedPreferences = CustomSharedPreferences(getApplication())
    private val tokenString = customSharedPreferences.getToken()

    val record = MutableLiveData<RecordDetail>()
    val response = MutableLiveData<UpdateResponse>()

    fun getSingleRecord(id:Int){
        disposable.add(
            recordApiService.getSingleRecord(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RecordDetail>(){
                    override fun onSuccess(t: RecordDetail) {
                        println("DetailViewModel-getSingleRecord --> ${t}")
                        record.value = t
                    }

                    override fun onError(e: Throwable) {
                        println("Exception-DetailViewModel-getSingleRecord --> ${e}")
                    }

                })
        )
    }

    fun deleteSingleRecord(id:Int){
        disposable.add(
            recordApiService.deleteSingleRecord(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<UpdateResponse>(){
                    override fun onSuccess(t: UpdateResponse) {
                        println("DetailViewModel-deleteSingleRecord --> ${t}")
                        response.value = t
                    }

                    override fun onError(e: Throwable) {
                        println("Exception-DetailViewModel-deleteSingleRecord --> ${e}")
                    }

                })
        )
    }
}