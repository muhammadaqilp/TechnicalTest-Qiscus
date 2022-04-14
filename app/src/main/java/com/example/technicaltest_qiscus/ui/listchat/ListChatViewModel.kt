package com.example.technicaltest_qiscus.ui.listchat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.technicaltest_qiscus.domain.usecase.chat.ChatUseCase
import com.example.technicaltest_qiscus.util.Action
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom

class ListChatViewModel(private val chatUseCase: ChatUseCase) : ViewModel() {

    private val _listChat = MutableLiveData<List<QiscusChatRoom>>()
    val listChat: LiveData<List<QiscusChatRoom>> = _listChat

    private val _chatRoom = MutableLiveData<QiscusChatRoom>()
    val chatRoom: LiveData<QiscusChatRoom> = _chatRoom

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loadChatRooms() = chatUseCase.getChatRooms(object : Action<List<QiscusChatRoom>> {
        override fun call(t: List<QiscusChatRoom>) {
            _listChat.value = t
        }

    }, object : Action<Throwable> {
        override fun call(t: Throwable) {
            _errorMessage.value = t.message
        }
    })

    fun openChatRoom(chatRoom: QiscusChatRoom) {
        if (!chatRoom.isGroup) {
            _chatRoom.value = chatRoom
        }
    }
}