package com.korucu.exampleapp.Views.Fragments.MainFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.korucu.exampleapp.Dtos.UserLogin
import com.korucu.exampleapp.Navigations.MainNavigationActions
import com.korucu.exampleapp.Utils.SweetAlert
import com.korucu.exampleapp.Utils.Utils
import com.korucu.exampleapp.ViewModels.LoginViewModel
import com.korucu.exampleapp.Views.Activities.HamburgerActivity
import com.korucu.exampleapp.Views.Activities.MainActivity
import com.korucu.exampleapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding:FragmentLoginBinding
    private lateinit var viewModel:LoginViewModel

    companion object{
        private var view:View? = null

        private fun setView(view:View){
            this.view = view
        }

        fun getView(): View? {
            return this.view
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        preapareFragment(binding.root)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.checkIsLoggedIn()

//        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
//            user.access_Token?.let {
//                println("TokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenTokenToken")
//                MainNavigationActions.actionLoginFragmentToListFragment()
//            }
//        })

        viewModel.isLoggedIn.observe(viewLifecycleOwner, Observer { isLoggedIn ->

            if (isLoggedIn){

//                val changePage = Intent(MainActivity::class.java, HamburgerActivity::class.java)
//                startActivity(changePage)

                val intent = Intent (activity, HamburgerActivity::class.java)
                activity?.startActivity(intent)
            }
            binding.progressBar.visibility = View.GONE
        })

        binding.lnkRegister.setOnClickListener {
            MainNavigationActions.actionLoginFragmentToRegisterFragment()
        }

        binding.btnLogin.setOnClickListener {

            if (Utils.isValidEmail(binding.txtEmail.text.toString())){
                val user = UserLogin(binding.txtEmail.text.toString(), binding.txtPassword.text.toString())
                viewModel.login(user)
                binding.progressBar.visibility = View.VISIBLE
            }else{
                Toast.makeText(context, "Email is not valid!!!", Toast.LENGTH_LONG).show()
                println("Email is not valid!!!")
            }

//            val user = UserLogin("asbc", "abc")
//            viewModel.login(user)
        }

    }

    private fun preapareFragment(view:View){
        setView(view)
    }
}