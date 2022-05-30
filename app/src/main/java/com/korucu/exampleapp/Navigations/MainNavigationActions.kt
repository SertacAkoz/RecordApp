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

        fun actionLoginFragmentToListFragment(){
            val navDirection:NavDirections = LoginFragmentDirections.actionLoginFragmentToListFragment()
            LoginFragment.getView()?.let { view ->
                Navigation.findNavController(view).navigate(navDirection)
            }
        }

        fun actionListFragmentToDetailFragment(id:Int){
            val navDirection:NavDirections = ListFragmentDirections.actionListFragmentToDetailFragment(id)
            ListFragment.getView()?.let { view ->
                Navigation.findNavController(view).navigate(navDirection)
            }
        }

        fun actionDetailFragmentToUpdateFragment(id:Int){
            val navDirection:NavDirections = DetailFragmentDirections.actionDetailFragmentToUpdateFragment(id)
            DetailFragment.getView()?.let { view ->
                Navigation.findNavController(view).navigate(navDirection)
            }
        }

        fun actionListFragmentToAddPasswordFragment(){
            val navDirection:NavDirections = ListFragmentDirections.actionListFragmentToAddPasswordFragment()
            ListFragment.getView()?.let { view ->
                Navigation.findNavController(view).navigate(navDirection)
            }
        }

        fun actionAddPasswordFragmentToListFragment(){
            val navDirection:NavDirections = AddPasswordFragmentDirections.actionAddPasswordFragmentToListFragment()
            AddPasswordFragment.getView()?.let { view ->
                Navigation.findNavController(view).navigate(navDirection)
            }
        }

        fun actionListFragmentToLoginFragment(){
            val navDirection:NavDirections = ListFragmentDirections.actionListFragmentToLoginFragment()
            ListFragment.getView()?.let { view ->
                Navigation.findNavController(view).navigate(navDirection)
            }
        }

        fun actionUpdateFragmentToListFragment(){
            val navDirection:NavDirections = UpdateFragmentDirections.actionUpdateFragmentToListFragment()
            UpdateFragment.getView()?.let { view ->
                Navigation.findNavController(view).navigate(navDirection)
            }
        }

        fun actionDetailFragmentToListFragment(){
            val navDirection:NavDirections = DetailFragmentDirections.actionDetailFragmentToListFragment()
            DetailFragment.getView()?.let { view ->
                Navigation.findNavController(view).navigate(navDirection)
            }
        }
    }
}