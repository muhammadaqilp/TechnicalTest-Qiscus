package com.example.technicaltest_qiscus.data

import com.example.technicaltest_qiscus.data.source.DataSourceChat
import com.example.technicaltest_qiscus.domain.model.User
import com.example.technicaltest_qiscus.domain.repository.chat.IChatRepository
import com.example.technicaltest_qiscus.util.Action
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom

class ChatRepository(private val dataSourceChat: DataSourceChat) : IChatRepository {
    override fun getChatRooms(onSuccess: Action<List<QiscusChatRoom>>, onError: Action<Throwable>) {
        dataSourceChat.getChatRoom(onSuccess, onError)
    }

    override fun createChatRoom(
        user: User,
        onSuccess: Action<QiscusChatRoom>,
        onError: Action<Throwable>
    ) {
        dataSourceChat.createChatRoom(user, onSuccess, onError)
    }
}