package com.korucu.exampleapp.ViewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.korucu.exampleapp.Dtos.UserRegister
import com.korucu.exampleapp.Models.UpdateResponse
import com.korucu.exampleapp.Models.User
import com.korucu.exampleapp.Services.AuthApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class RegisterViewModel(application: Application):BaseViewModel(application) {

    private val passwordApiService = AuthApiService()
    private val disposable = CompositeDisposable()

    val response = MutableLiveData<UpdateResponse>()

    fun register(userRegister:UserRegister){


        disposable.add(
            passwordApiService.register(userRegister)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<UpdateResponse>(){
                    override fun onSuccess(t: UpdateResponse) {
                        println("RegisterViewModel-register-onSuccess --> ${t}")

                        response.value = t
                    }

                    override fun onError(e: Throwable) {
                        println("Exception-RegisterViewModel-register --> ${e}")
                        response.value?.isSuccess = false
                        response.value?.message = e.toString()
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}