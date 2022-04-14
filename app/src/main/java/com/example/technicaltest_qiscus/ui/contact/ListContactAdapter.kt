package com.example.technicaltest_qiscus.ui.contact

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.example.technicaltest_qiscus.R
import com.example.technicaltest_qiscus.databinding.ItemListContactsBinding
import com.example.technicaltest_qiscus.domain.model.User
import com.example.technicaltest_qiscus.util.SortedRecyclerViewAdapter
import com.qiscus.nirmana.Nirmana

class ListContactAdapter(private val listener: OnContactClickCallback) :
    SortedRecyclerViewAdapter<User, ListContactAdapter.ViewHolder>() {

    override fun getItemClass(): Class<User> = User::class.java

    override fun compare(item1: User, item2: User): Int = item1.compareTo(item2)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListContactAdapter.ViewHolder {
        val binding =
            ItemListContactsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListContactAdapter.ViewHolder, position: Int) {
        val data = getData().get(position)
        holder.bind(data)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun addOrUpdate(items: List<User>) {
        for (contact in items) {
            val i = findPosition(contact)
            if (i == -1) {
                getData().add(contact)
            } else {
                getData().updateItemAt(i, contact)
            }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemListContactsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            Nirmana.getInstance().get().setDefaultRequestOptions(
                RequestOptions().placeholder(R.drawable.ic_qiscus_avatar)
                    .error(R.drawable.ic_qiscus_avatar).dontAnimate()
            ).load(user.avatar).into(binding.ivAvatar)

            binding.tvName.text = user.name

            itemView.setOnClickListener {
                listener.onContactClicked(absoluteAdapterPosition)
            }
        }
    }

    interface OnContactClickCallback {
        fun onContactClicked(position: Int)
    }
}