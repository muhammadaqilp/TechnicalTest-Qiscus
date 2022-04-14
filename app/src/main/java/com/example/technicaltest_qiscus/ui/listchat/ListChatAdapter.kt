package com.example.technicaltest_qiscus.ui.listchat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.example.technicaltest_qiscus.R
import com.example.technicaltest_qiscus.databinding.ItemListRoomchatBinding
import com.example.technicaltest_qiscus.ui.SortedRecyclerViewAdapter
import com.example.technicaltest_qiscus.util.DateUtil
import com.qiscus.nirmana.Nirmana
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom

class ListChatAdapter(private val listener: OnChatClickCallback) :
    SortedRecyclerViewAdapter<QiscusChatRoom, ListChatAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    override fun addOrUpdate(items: List<QiscusChatRoom>) {
        for (chatRoom in items) {
            val index = findPosition(chatRoom)
            if (index == -1) {
                getData().add(chatRoom)
            } else {
                getData().updateItemAt(index, chatRoom)
            }
        }
        notifyDataSetChanged()
    }

    override fun getItemClass(): Class<QiscusChatRoom> {
        return QiscusChatRoom::class.java
    }

    override fun compare(item1: QiscusChatRoom, item2: QiscusChatRoom): Int {
        return item2.lastComment.time.compareTo(item1.lastComment.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListChatAdapter.ViewHolder {
        val binding =
            ItemListRoomchatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListChatAdapter.ViewHolder, position: Int) {
        val chatRoom = getData().get(position)
        holder.bind(chatRoom)
    }

    inner class ViewHolder(private val binding: ItemListRoomchatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatRoom: QiscusChatRoom) {
            with(binding) {
                Nirmana.getInstance().get()
                    .setDefaultRequestOptions(
                        RequestOptions().placeholder(R.drawable.ic_qiscus_avatar)
                            .error(R.drawable.ic_qiscus_avatar).dontAnimate()
                    ).load(chatRoom.avatarUrl).into(avatar)

                val last = chatRoom.lastComment

                if (last != null && last.id > 0) {
                    if (last.sender != null) {
                        var lastMessageText =
                            if (last.isMyComment) "You: " else last.sender
                                .split(" ".toRegex()).toTypedArray()[0] + ": "
                        lastMessageText += last.message
                        tvLastMessage.text = lastMessageText
                    } else {
                        tvLastMessage.text = last.message
                    }
                    tvTime.text = DateUtil.getLastMessageTimestamp(last.time)
                } else {
                    tvLastMessage.text = ""
                    tvTime.text = ""
                }

                tvCountUnread.text = String.format("%d", chatRoom.unreadCount)
                if (chatRoom.unreadCount == 0) layoutUnreadCount.visibility = View.GONE
                else layoutUnreadCount.visibility = View.VISIBLE

                itemView.setOnClickListener {
                    listener.onChatClicked(absoluteAdapterPosition)
                }
            }
        }
    }

    interface OnChatClickCallback {
        fun onChatClicked(position: Int)
    }
}