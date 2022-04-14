package com.example.technicaltest_qiscus.ui.listchat

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.technicaltest_qiscus.R
import com.example.technicaltest_qiscus.databinding.ActivityListChatBinding
import com.example.technicaltest_qiscus.ui.contact.ListContactActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListChatActivity : AppCompatActivity(R.layout.activity_list_chat),
    ListChatAdapter.OnChatClickCallback {

    private val chatViewModel: ListChatViewModel by viewModel()
    private val binding: ActivityListChatBinding by viewBinding()
    private var roomChatAdapter: ListChatAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "List Qiscus Chat"

        roomChatAdapter = ListChatAdapter(this)

        chatViewModel.loadChatRooms()

        chatViewModel.listChat.observe(this, { chatRoom ->
            if (chatRoom != null) {
                if (chatRoom.isEmpty()) {
                    binding.rvListChat.visibility = View.GONE
                    binding.tvNoData.visibility = View.VISIBLE
                } else {
                    binding.tvNoData.visibility = View.GONE
                    binding.rvListChat.visibility = View.VISIBLE
                }
                roomChatAdapter?.addOrUpdate(chatRoom)
            }
        })

        with(binding.rvListChat) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ListChatActivity)
            adapter = roomChatAdapter
        }

        binding.fab.setOnClickListener {
            ListContactActivity.start(this)
        }
    }

    override fun onChatClicked(position: Int) {
        val chat = roomChatAdapter?.getData()?.get(position)
        if (chat != null) {
            chatViewModel.openChatRoom(chat)
        }
        chatViewModel.chatRoom.observe(this, { chat ->
        })
    }
}