package com.korucu.exampleapp.ViewModels

import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import com.korucu.exampleapp.Dtos.UserLogin
import com.korucu.exampleapp.Models.User
import com.korucu.exampleapp.Navigations.MainNavigationActions
import com.korucu.exampleapp.Services.AuthApiService
import com.korucu.exampleapp.Utils.CustomSharedPreferences
import com.korucu.exampleapp.Views.Activities.HamburgerActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class LoginViewModel(application: Application):BaseViewModel(application) {

    private val passwordApiService = AuthApiService()
    private val disposable = CompositeDisposable()
    private var customSharedPreferences = CustomSharedPreferences(getApplication())

    val user = MutableLiveData<User>()
    val loginError = MutableLiveData<Boolean>()
    val isLoggedIn = MutableLiveData<Boolean>()

    fun checkIsLoggedIn(){
        if (!customSharedPreferences.getToken().equals("null")){
            isLoggedIn.value = true
        }
    }

    fun login(userLogin:UserLogin){
        println("LoginViewModel-login-LoggedIn --> ${user}")

        disposable.add(
            passwordApiService.login(userLogin)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<User>(){
                    override fun onSuccess(t: User) {
                        println("LoginViewModel-login-onSuccess --> ${t}")
                        user.value = t
                        user.value!!.access_Token?.let { token -> customSharedPreferences.saveToken(token) }
                        user.value!!.email?.let { customSharedPreferences.saveEmail(it) }
                        println("LoginViewModel-login-SP-Token --> ${customSharedPreferences.getToken()}")
                        isLoggedIn.value = true
                    }

                    override fun onError(e: Throwable) {
                        isLoggedIn.value = false
                        println("Exception-LoginViewModel-login --> ${e}")
                    }

                })
        )

//        MainNavigationActions.actionLoginFragmentToListFragment()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}