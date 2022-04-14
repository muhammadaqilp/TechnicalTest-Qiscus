package com.example.technicaltest_qiscus.domain.repository.chat

import com.example.technicaltest_qiscus.domain.model.User
import com.example.technicaltest_qiscus.util.Action
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom

interface IChatRepository {

    fun getChatRooms(onSuccess: Action<List<QiscusChatRoom>>, onError: Action<Throwable>)

    fun createChatRoom(user: User, onSuccess: Action<QiscusChatRoom>, onError: Action<Throwable>)

}