package com.korucu.exampleapp.Views.Fragments.MainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.korucu.exampleapp.Dtos.RecordAdd
import com.korucu.exampleapp.Navigations.MainNavigationActions
import com.korucu.exampleapp.R
import com.korucu.exampleapp.Utils.SweetAlert
import com.korucu.exampleapp.Utils.toEditable
import com.korucu.exampleapp.ViewModels.AddPasswordViewModel
import com.korucu.exampleapp.ViewModels.LoginViewModel
import com.korucu.exampleapp.databinding.FragmentAddPasswordBinding

class AddPasswordFragment : Fragment() {

    private lateinit var binding:FragmentAddPasswordBinding
    private lateinit var viewModel:AddPasswordViewModel

    var btnPressed = false

    companion object{
        private var view:View? = null

        private fun setView(view:View){
            this.view = view
        }

        fun getView(): View? {
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
        binding = FragmentAddPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareFragment(binding.root)
        viewModel = ViewModelProviders.of(this).get(AddPasswordViewModel::class.java)

        observeLiveData()
        onClicks()
    }

    private fun prepareFragment(view:View){
        setView(view)
    }

    private fun onClicks(){
        binding.btnLogin.setOnClickListener {

            val recorName = binding.txtRecordName.text.toString()
            val recordDescription = binding.txtRecordDescription.text.toString()
            val recordUrl = binding.txtRecordUrl.text.toString()
            val recordPassword = binding.txtRecordPassword.text.toString()

            val newRecord = RecordAdd(recorName, recordDescription,recordUrl, recordPassword)

            viewModel.addRecord(newRecord)

            btnPressed = true
        }
    }

    private fun observeLiveData(){
        viewModel.response.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess == true && btnPressed){
                SweetAlert.successPopup(context, "Success", it.message)
                clearForm()

            }else if(it.isSuccess == false && btnPressed){
                SweetAlert.errorPopup(context, "Error", it.message)
                clearForm()
            }
        })
    }

    private fun clearForm(){
        binding.txtRecordDescription.text = "".toEditable()
        binding.txtRecordName.text = "".toEditable()
        binding.txtRecordPassword.text = "".toEditable()
        binding.txtRecordUrl.text = "".toEditable()
    }
}