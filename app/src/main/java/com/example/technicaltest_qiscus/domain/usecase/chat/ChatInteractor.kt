package com.example.technicaltest_qiscus.domain.usecase.chat

import com.example.technicaltest_qiscus.domain.model.User
import com.example.technicaltest_qiscus.domain.repository.chat.IChatRepository
import com.example.technicaltest_qiscus.util.Action
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom

class ChatInteractor(private val chatRepository: IChatRepository) : ChatUseCase {

    override fun getChatRooms(onSuccess: Action<List<QiscusChatRoom>>, onError: Action<Throwable>) {
        chatRepository.getChatRooms(onSuccess, onError)
    }

    override fun createChatRoom(
        user: User,
        onSuccess: Action<QiscusChatRoom>,
        onError: Action<Throwable>
    ) {
        chatRepository.createChatRoom(user, onSuccess, onError)
    }
}