package com.korucu.exampleapp.ViewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.korucu.exampleapp.Dtos.RecordAdd
import com.korucu.exampleapp.Models.UpdateResponse
import com.korucu.exampleapp.Models.User
import com.korucu.exampleapp.Services.RecordApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class AddPasswordViewModel(application: Application):BaseViewModel(application) {
    private val disposable = CompositeDisposable()
    private var recordApiService = RecordApiService(application)

    val response = MutableLiveData<UpdateResponse>()

    fun addRecord(record:RecordAdd){
        disposable.add(
            recordApiService.addRecord(record)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<UpdateResponse>(){
                    override fun onSuccess(t: UpdateResponse) {
                        println("AddPasswordViewModel-addRecord --> ${t}")
                        response.value = t
                    }

                    override fun onError(e: Throwable) {
                        println("Exception-AddPasswordViewModel-addRecord --> ${e}")
                    }

                })
        )
    }
}