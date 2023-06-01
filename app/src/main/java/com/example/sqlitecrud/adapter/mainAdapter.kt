package com.example.sqlitecrud.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.sqlitecrud.MainActivity
import com.example.sqlitecrud.databinding.ItemViewBinding
import com.example.sqlitecrud.model.ModelData

class mainAdapter(val context: Context, var list : ArrayList<ModelData>) : Adapter<mainAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemViewBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.binding.apply {
            title.text = model.title
            message.text = model.message
        }
    }

    class ViewHolder(item : ItemViewBinding): RecyclerView.ViewHolder(item.root) {
        val binding = item
    }

    fun filterNotes(filterList: ArrayList<ModelData>){
        list = filterList
        notifyDataSetChanged()

    }
}