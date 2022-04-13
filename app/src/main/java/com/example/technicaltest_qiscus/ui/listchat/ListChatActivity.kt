package com.example.technicaltest_qiscus.ui.listchat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.technicaltest_qiscus.R
import com.example.technicaltest_qiscus.databinding.ActivityListChatBinding

class ListChatActivity : AppCompatActivity(R.layout.activity_list_chat) {
    private val binding: ActivityListChatBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}