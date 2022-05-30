package com.korucu.exampleapp.Views.Fragments.MainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.korucu.exampleapp.Dtos.UserRegister
import com.korucu.exampleapp.Navigations.MainNavigationActions
import com.korucu.exampleapp.R
import com.korucu.exampleapp.Utils.SweetAlert
import com.korucu.exampleapp.Utils.Utils
import com.korucu.exampleapp.ViewModels.LoginViewModel
import com.korucu.exampleapp.ViewModels.RegisterViewModel
import com.korucu.exampleapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding:FragmentRegisterBinding
    private lateinit var viewModel:RegisterViewModel

    companion object{
        private var view:View? =  null

        private fun setView(view:View){
            this.view = view
        }

        fun getView():View?{
            return this.view
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareFragment(binding.root)

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        binding.lnkLogin.setOnClickListener {
            MainNavigationActions.actionRegisterFragmentToLoginFragment()
        }

        binding.btnRegister.setOnClickListener {
            if (Utils.isValidEmail(binding.txtEmail.text.toString())){
                val user = UserRegister(binding.txtName.text.toString(), binding.txtEmail.text.toString(), binding.txtPassword.text.toString())
                viewModel.register(user)
            }
        }


        observeLiveData()
    }

    private fun prepareFragment(view:View){
        setView(view)
    }

    private fun observeLiveData(){
        viewModel.response.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccess == true){
                SweetAlert.successPopup(context, "Success", response.message)
                MainNavigationActions.actionRegisterFragmentToLoginFragment()
            }else if (response.isSuccess == false){
                SweetAlert.errorPopup(context, "Error", response.message)
            }
        })
    }
}