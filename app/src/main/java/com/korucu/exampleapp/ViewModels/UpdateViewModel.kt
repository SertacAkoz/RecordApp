package com.korucu.exampleapp.ViewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.korucu.exampleapp.Models.RecordDetail
import com.korucu.exampleapp.Models.UpdateResponse
import com.korucu.exampleapp.Services.RecordApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class UpdateViewModel(application: Application):BaseViewModel(application) {

    private val disposable = CompositeDisposable()
    private val recordApiService = RecordApiService(application)

    val record = MutableLiveData<RecordDetail>()

    val isUpdated = MutableLiveData<UpdateResponse>()

    fun updateRecord(record:RecordDetail){
        disposable.add(
            recordApiService.updateRecord(record)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<UpdateResponse>(){
                    override fun onSuccess(t: UpdateResponse) {
                        println("UpdateViewModel-updateRecord --> ${t}")
                        isUpdated.value = t
                    }

                    override fun onError(e: Throwable) {
                        println("Exception-UpdateViewModel-updateRecord --> ${e}")
                        isUpdated.value?.isSuccess = false
                        isUpdated.value?.message = e.toString()
                    }
                })
        )
    }

    fun getRecordToUpdate(id:Int){
        disposable.add(
            recordApiService.getSingleRecord(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RecordDetail>(){
                    override fun onSuccess(t: RecordDetail) {
                        println("UpdateViewModel-getRecordToUpdate --> ${t}")
                        record.value = t
                    }

                    override fun onError(e: Throwable) {
                        println("Exception-UpdateViewModel-getRecordToUpdate --> ${e}")
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}