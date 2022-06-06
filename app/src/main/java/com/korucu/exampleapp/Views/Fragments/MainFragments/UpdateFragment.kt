package com.korucu.exampleapp.Views.Fragments.MainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.korucu.exampleapp.Models.RecordDetail
import com.korucu.exampleapp.Navigations.HamburgerNavigationActions
import com.korucu.exampleapp.Navigations.MainNavigationActions
import com.korucu.exampleapp.R
import com.korucu.exampleapp.Utils.SweetAlert
import com.korucu.exampleapp.Utils.toEditable
import com.korucu.exampleapp.ViewModels.ListViewModel
import com.korucu.exampleapp.ViewModels.UpdateViewModel
import com.korucu.exampleapp.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {

    private lateinit var binding:FragmentUpdateBinding
    private lateinit var viewModel:UpdateViewModel
    private var updateRecordId:Int? = null

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
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareFragment(binding.root)
        viewModel = ViewModelProviders.of(this).get(UpdateViewModel::class.java)
        arguments?.let {
            updateRecordId = UpdateFragmentArgs.fromBundle(it).id
        }

        updateRecordId?.let { viewModel.getRecordToUpdate(it) }

        onClicks()
        observeLiveData()
    }

    private fun prepareFragment(view:View){
        setView(view)
    }

    private fun observeLiveData(){
        viewModel.record.observe(viewLifecycleOwner, Observer { record ->
            binding.txtRecordName.text = record.recordName?.toEditable()
            binding.txtRecordDescription.text = record.recordDescription?.toEditable()
            binding.txtRecordPassword.text = record.recordPassword?.toEditable()
            binding.txtRecordUrl.text = record.recordUrl?.toEditable()
        })

        viewModel.isUpdated.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess == true){
                SweetAlert.successPopup(context, "Success", it.message)
                HamburgerNavigationActions.actionUpdateFragmentToNavList2()
            }else if (it.isSuccess == false){
                SweetAlert.errorPopup(context, "Error", it.message)
            }
        })
    }

    private fun onClicks(){
        binding.btnLogin.setOnClickListener {
            viewModel.record.value?.let { record ->
                println("UpdateFragment-btnUpdate-RecordData --> ${record}")
                val updateRecord = RecordDetail(record.id, binding.txtRecordName.text.toString(), binding.txtRecordDescription.text.toString(), binding.txtRecordUrl.text.toString(), binding.txtRecordPassword.text.toString())
                println("UpdateFragment-onClick --> ${updateRecord}")
                viewModel.updateRecord(updateRecord)
            }
        }
    }
}