package com.example.technicaltest_qiscus.domain.usecase.user

import com.example.technicaltest_qiscus.domain.model.User
import com.example.technicaltest_qiscus.util.Action

interface UserUseCase {

    fun login(
        username: String,
        password: String,
        name: String,
        onSuccess: Action<User>,
        onError: Action<Throwable>
    )

    fun getCurrentUser(onSuccess: Action<User>, onError: Action<Throwable>)

    fun getUsers(
        page: Long,
        limit: Int,
        query: String,
        onSuccess: Action<List<User>>,
        onError: Action<Throwable>
    )

    fun logout()

    fun setDeviceToken(token: String)

}