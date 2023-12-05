package com.demo.android.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.android.R
import com.demo.android.data.models.Person
import com.demo.android.databinding.CellPhotoItemBinding
import com.demo.android.presentation.core.BaseRecyclerViewAdapter
import com.demo.android.presentation.home.listener.PhotoItemClickListener
import com.demo.android.presentation.utility.loadImageRound

class PersonListAdapter(val listener: PhotoItemClickListener) : BaseRecyclerViewAdapter<Person>() {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cellPhotoItemBinding = CellPhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(cellPhotoItemBinding)
    }

    override fun bindData(holder: RecyclerView.ViewHolder?, item: Person?, position: Int) {
        if (holder != null && item != null) {
            val viewHolder = holder as ViewHolder
            viewHolder.bindData(item)
        }
    }

    inner class ViewHolder(private val itemBinding: CellPhotoItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(item: Person) {
            //set or bind data
            itemBinding.ivImage.loadImageRound(
                item.avatar.orEmpty(),
                R.drawable.ic_user_placeholder
            )
            itemBinding.tvTitle.text = item.firstName.plus(item.lastName)
            itemBinding.tvEmail.text = item.email


            //onclick event
            itemBinding.root.setOnClickListener {
                listener.onPhotoItemClick(item)
            }
        }
    }
}