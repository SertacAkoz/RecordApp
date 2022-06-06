package com.korucu.exampleapp.Navigations

import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.korucu.exampleapp.Views.Fragments.MainFragments.*

abstract class MainNavigationActions {
    companion object{
        fun actionLoginFragmentToRegisterFragment(){
            val navDirection:NavDirections = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            LoginFragment.getView()?.let { view ->
                Navigation.findNavController(view).navigate(navDirection)
            }
        }

        fun actionRegisterFragmentToLoginFragment(){
            val navDirection:NavDirections = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            RegisterFragment.getView()?.let { view ->
                Navigation.findNavController(view).navigate(navDirection)
            }
        }
    }
}