package com.example.technicaltest_qiscus.ui.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.technicaltest_qiscus.domain.model.User
import com.example.technicaltest_qiscus.domain.usecase.chat.ChatUseCase
import com.example.technicaltest_qiscus.domain.usecase.user.UserUseCase
import com.example.technicaltest_qiscus.util.Action
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom

class ListContactViewModel(
    private val chatUseCase: ChatUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _listContact = MutableLiveData<List<User>>()
    val listContact: LiveData<List<User>> = _listContact

    private val _chatUser = MutableLiveData<QiscusChatRoom>()
    val chatUser: LiveData<QiscusChatRoom> = _chatUser

    fun getListContact(page: Long, limit: Int, query: String) {
        userUseCase.getUsers(page, limit, query, object : Action<List<User>> {
            override fun call(t: List<User>) {
                _listContact.value = t
            }
        }, object : Action<Throwable> {
            override fun call(t: Throwable) {

            }

        })
    }

    fun createChat(contact: User) {
        chatUseCase.createChatRoom(contact, object : Action<QiscusChatRoom> {
            override fun call(t: QiscusChatRoom) {
                _chatUser.value = t
            }

        }, object : Action<Throwable> {
            override fun call(t: Throwable) {

            }
        })
    }

}