package com.korucu.exampleapp.Navigations

import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.korucu.exampleapp.Views.Fragments.MainFragments.*

class HamburgerNavigationActions {
    companion object{
        fun actionNavListToDetailFragment(id:Int){
            val navDirection:NavDirections = ListFragmentDirections.actionNavListToDetailFragment(id)
            ListFragment.getView()?.let {
                Navigation.findNavController(it).navigate(navDirection)
            }
        }

        fun actionDetailFragmentToUpdateFragment(id:Int){
            val navDirection:NavDirections = DetailFragmentDirections.actionDetailFragmentToUpdateFragment(id)
            DetailFragment.getView()?.let {
                Navigation.findNavController(it).navigate(navDirection)
            }
        }

        fun actionUpdateFragmentToNavList2(){
            val navDirection:NavDirections = UpdateFragmentDirections.actionUpdateFragmentToNavList2()
            UpdateFragment.getView()?.let {
                Navigation.findNavController(it).navigate(navDirection)
            }
        }

        fun actionDetailFragmentToNavList(){
            val navDirection:NavDirections = DetailFragmentDirections.actionDetailFragmentToNavList()
            DetailFragment.getView()?.let {
                Navigation.findNavController(it).navigate(navDirection)
            }
        }
    }
}