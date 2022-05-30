package com.korucu.exampleapp.Views.Fragments.MainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.korucu.exampleapp.Adapters.PasswordAdapter
import com.korucu.exampleapp.Navigations.MainNavigationActions
import com.korucu.exampleapp.R
import com.korucu.exampleapp.Utils.SweetAlert
import com.korucu.exampleapp.ViewModels.ListViewModel
import com.korucu.exampleapp.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var binding:FragmentListBinding
    private lateinit var viewModel:ListViewModel
    private lateinit var passwordAdapter:PasswordAdapter

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
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareFragment(binding.root)
        passwordAdapter  = PasswordAdapter(arrayListOf(), this.requireActivity())
        binding.passwordList.layoutManager = LinearLayoutManager(context)
        binding.passwordList.adapter = passwordAdapter

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
//        viewModel.getPasswords()
        viewModel.getRecordList()

        binding.refreshLayout.setOnRefreshListener {
            binding.passwordList.visibility = View.GONE
            binding.textViewListError.visibility = View.GONE
            binding.listLoading.visibility = View.VISIBLE
            viewModel.getRecordList()
            binding.refreshLayout.isRefreshing = false
        }

        observeLiveData()
        onClicks()
    }

    private fun observeLiveData(){
        viewModel.passwordListFake.observe(viewLifecycleOwner, Observer { passwordList ->
            passwordList?.let {
                binding.listLoading.visibility = View.GONE
                binding.textViewListError.visibility = View.GONE
//                passwordAdapter.updatePasswordList(passwordList)
            }
        })

        viewModel.recordList.observe(viewLifecycleOwner, Observer { recordList ->
            recordList?.let {
                binding.listLoading.visibility = View.GONE
                binding.textViewListError.visibility = View.GONE
                binding.passwordList.visibility = View.VISIBLE
                passwordAdapter.updatePasswordList(it)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if (error){
                binding.listLoading.visibility = View.GONE
                binding.textViewListError.visibility = View.VISIBLE
            }else{
                binding.listLoading.visibility = View.GONE
                binding.textViewListError.visibility = View.GONE
            }
        })

        viewModel.tokenError.observe(viewLifecycleOwner, Observer { tokenError ->
            if (tokenError){
                SweetAlert.errorPopup(context, "Error", "Expired Token")
                MainNavigationActions.actionListFragmentToLoginFragment()
            }
        })
    }

    private fun prepareFragment(view:View){
        setView(view)
    }

    private fun onClicks(){
        binding.linearLayoutAddRecord.setOnClickListener {
            MainNavigationActions.actionListFragmentToAddPasswordFragment()
        }

        binding.imageViewLogout.setOnClickListener {
            viewModel.logout()
        }
    }

}