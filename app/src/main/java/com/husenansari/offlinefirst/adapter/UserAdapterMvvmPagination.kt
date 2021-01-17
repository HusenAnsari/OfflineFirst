package com.husenansari.offlinefirst.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.husenansari.offlinefirst.R
import com.husenansari.offlinefirst.model.Item

import java.util.*

class UserAdapterMvvmPagination(private val context: Context) :  PagedListAdapter<Item, RecyclerView.ViewHolder>(differCallback) {


    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Item>() {
           override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
               return oldItem.account_id == newItem.account_id
           }

           override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
               return oldItem == newItem
           }
       }
    }


    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rvstack, viewGroup, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val searchViewHolder = viewHolder as UserViewHolder
        searchViewHolder.setValues(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtName: TextView = view.findViewById(R.id.txtName)
        private val txtReputation: TextView = view.findViewById(R.id.txtReputation)
        private val imgUserImage: ImageView = view.findViewById(R.id.imgUserImage)

        @SuppressLint("SetTextI18n")
        fun setValues(items: Item) {

            txtName.text = items.display_name
            txtReputation.text = items.reputation.toString()
            Glide.with(context)
                .load(items.profile_image)
                .dontAnimate()
                .into(imgUserImage)

        }

    }
}