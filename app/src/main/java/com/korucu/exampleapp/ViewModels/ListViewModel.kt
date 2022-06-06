package com.korucu.exampleapp.ViewModels

import android.app.Application
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.korucu.exampleapp.Models.Record
import com.korucu.exampleapp.Navigations.MainNavigationActions
import com.korucu.exampleapp.Services.RecordApiService
import com.korucu.exampleapp.Utils.CustomSharedPreferences
import com.korucu.exampleapp.Views.Activities.HamburgerActivity
import com.korucu.exampleapp.Views.Activities.MainActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.adapter.rxjava3.HttpException

class ListViewModel(application: Application):BaseViewModel(application) {

    private val recordApiService = RecordApiService(application)
    private val disposable = CompositeDisposable()
    private var customSharedPreferences = CustomSharedPreferences(getApplication())
    private val tokenString = customSharedPreferences.getToken()

    val localApplication = application

    val passwordListFake = MutableLiveData<List<String>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val tokenError = MutableLiveData<Boolean>()

    val recordList = MutableLiveData<List<Record>>()

    fun getPasswords(){
        error.value = false
        loading.value = false
        val list = arrayListOf<String>("123","213","321")
        passwordListFake.value = list
    }

    fun deneme(){
        val headers = HashMap<String, String>()
        headers["KEY_AUTHORIZATION"] = "paste AUTHORIZATION value here"
        headers["KEY_TOKEN"] = "paste TOKEN value here"
    }

    fun getRecordList(){
        println("ListViewModel-tokenString --> ${tokenString}")
        if (!tokenString.equals("null")){
            println("ListViewModel-getRecordList-If")
            disposable.add(
                recordApiService.getRecordList()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Record>>(){
                        override fun onSuccess(t: List<Record>) {
                            println("ListViewModel-getPasswordList --> ${t}")
//                            println("ListViewModel-getPasswordList-ResponseCode --> ${t.hashCode()}")

                            error.value = false
                            loading.value = false
                            recordList.value = t

                        }

                        override fun onError(e: Throwable) {
                            error.value = true
                            loading.value = false
                            println("Exception-ListViewModel-getRecordList --> ${e}")
                            println("Exception-ListViewModel-getRecordList-StatusCode --> ${(e as HttpException).code()}")
                            if ((e as HttpException).code() == 401){
                                tokenError.value = true
                                customSharedPreferences.saveToken("null")
                            }
                        }


                    })
            )
        }
    }

    fun logout(){
        customSharedPreferences.saveToken("null")

        val intent = Intent (localApplication, MainActivity::class.java)
        localApplication.startActivity(intent)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}