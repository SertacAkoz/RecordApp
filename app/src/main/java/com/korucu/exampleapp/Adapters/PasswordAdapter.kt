package com.korucu.exampleapp.Adapters

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.korucu.exampleapp.Models.Record
import com.korucu.exampleapp.Navigations.MainNavigationActions
import com.korucu.exampleapp.databinding.ListRowBinding

class PasswordAdapter(val recordList:ArrayList<Record>, val activity: Activity):RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder>() {
    class PasswordViewHolder(var view: ListRowBinding):RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        var binding = ListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PasswordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        holder.view.textViewRecordName.text = recordList[position].recordName
        holder.view.textViewRecordDescription.text = recordList[position].recordDescription

//        holder.view.imageViewCopy.setOnClickListener {
//            try {
//                var clipboardManager:ClipboardManager = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//                clipboardManager.text = passwordlist[position]
//
//                Toast.makeText(activity, clipboardManager.text, Toast.LENGTH_SHORT).show()
//            }catch (e:Exception){
//                println("Exception-PasswordAdapter-ImageViewCopy --> ${e}")
//            }
//        }

        holder.view.linearLayoutListRow.setOnClickListener {
            MainNavigationActions.actionListFragmentToDetailFragment(recordList[position].id!!)
        }
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    fun updatePasswordList(newRecordList:List<Record>){
        recordList.clear()
        recordList.addAll(newRecordList)
        notifyDataSetChanged()
    }
}