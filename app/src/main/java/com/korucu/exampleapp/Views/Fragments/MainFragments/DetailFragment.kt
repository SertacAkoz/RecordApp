package com.korucu.exampleapp.Views.Fragments.MainFragments

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.korucu.exampleapp.Navigations.HamburgerNavigationActions
import com.korucu.exampleapp.Navigations.MainNavigationActions
import com.korucu.exampleapp.R
import com.korucu.exampleapp.Utils.SweetAlert
import com.korucu.exampleapp.ViewModels.DetailViewModel
import com.korucu.exampleapp.ViewModels.ListViewModel
import com.korucu.exampleapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding:FragmentDetailBinding
    private lateinit var viewModel:DetailViewModel

    private var recordId:Int? = null

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
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareFragment(binding.root)
        arguments?.let {
            recordId = DetailFragmentArgs.fromBundle(it).id
        }
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        recordId?.let {
            viewModel.getSingleRecord(it)
        }

        observeLiveData()
        onCLicks()
    }

    private fun prepareFragment(view:View){
        setView(view)
    }

    private fun observeLiveData(){
        viewModel.record.observe(viewLifecycleOwner, Observer { record ->
            binding.textViewRecordId.text = record.id.toString()
            binding.textViewRecordName.text = record.recordName
            binding.textViewRecordDescription.text = record.recordDescription
            binding.textViewRecordUrl.text = record.recordUrl
            binding.textViewRecordPassword.text = record.recordPassword
        })

        viewModel.response.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccess == true){
                SweetAlert.successPopup(context, "Success", response.message)
                HamburgerNavigationActions.actionDetailFragmentToNavList()
            }
        })
    }

    private fun onCLicks(){
        var clipboardManager: ClipboardManager = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        binding.btnUpdate.setOnClickListener {
            recordId?.let { id -> HamburgerNavigationActions.actionDetailFragmentToUpdateFragment(id) }
        }

        binding.btnDelete.setOnClickListener {
            viewModel.record.value?.id?.let { id -> viewModel.deleteSingleRecord(id) }
        }

        binding.textViewRecordName.setOnClickListener {
            clipboardManager.text = binding.textViewRecordName.text.toString()
            Toast.makeText(context, clipboardManager.text, Toast.LENGTH_SHORT).show()
        }

        binding.textViewRecordDescription.setOnClickListener {
            clipboardManager.text = binding.textViewRecordDescription.text.toString()
            Toast.makeText(context, clipboardManager.text, Toast.LENGTH_SHORT).show()
        }

        binding.textViewRecordId.setOnClickListener {
            clipboardManager.text = binding.textViewRecordId.text.toString()
            Toast.makeText(context, clipboardManager.text, Toast.LENGTH_SHORT).show()
        }

        binding.textViewRecordPassword.setOnClickListener {
            clipboardManager.text = binding.textViewRecordPassword.text.toString()
            Toast.makeText(context, clipboardManager.text, Toast.LENGTH_SHORT).show()
        }

        binding.textViewRecordUrl.setOnClickListener {
            clipboardManager.text = binding.textViewRecordUrl.text.toString()
            Toast.makeText(context, clipboardManager.text, Toast.LENGTH_SHORT).show()
        }

    }
}