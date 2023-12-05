package com.demo.android.presentation.home.adapter



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.android.R
import com.demo.android.data.models.Person
import com.demo.android.databinding.CellPhotoItemBinding
import com.demo.android.presentation.home.listener.PhotoItemClickListener
import com.demo.android.presentation.utility.loadImageRound

class PostAdapter(val list: MutableList<Person>, val listener: PhotoItemClickListener) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    fun addData(list: List<Person>) {
        val oldSize = this.list.size
        this.list.addAll(list)
        notifyItemRangeInserted(oldSize, this.list.size)
    }

    fun setData(list: List<Person>) {
        val oldSize = this.list.size
        this.list.clear()
        notifyItemRangeRemoved(0, oldSize)
        this.list.addAll(list)
        notifyItemRangeInserted(oldSize, this.list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            CellPhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list.get(position)
        holder.bind(item)
    }

    inner class ViewHolder(val itemBinding: CellPhotoItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Person) {
            itemBinding.ivImage.loadImageRound(
                item.avatar.orEmpty(),
                R.drawable.ic_user_placeholder
            )
            itemBinding.tvTitle.text = item.firstName.plus(item.lastName)
            itemBinding.tvEmail.text = item.email
        }

        init {
            itemView.setOnClickListener {
                listener.onPhotoItemClick(list.get(adapterPosition))
            }
        }
    }
}