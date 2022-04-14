package com.example.technicaltest_qiscus.domain.usecase.user

import com.example.technicaltest_qiscus.domain.model.User
import com.example.technicaltest_qiscus.domain.repository.user.IUserRepository
import com.example.technicaltest_qiscus.util.Action

class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {
    override fun login(
        username: String,
        password: String,
        name: String,
        onSuccess: Action<User>,
        onError: Action<Throwable>
    ) {
        userRepository.login(username, password, name, onSuccess, onError)
    }

    override fun getCurrentUser(onSuccess: Action<User>, onError: Action<Throwable>) {
        userRepository.getCurrentUser(onSuccess, onError)
    }

    override fun getUsers(
        page: Long,
        limit: Int,
        query: String,
        onSuccess: Action<List<User>>,
        onError: Action<Throwable>
    ) {
        userRepository.getUsers(page, limit, query, onSuccess, onError)
    }

    override fun logout() {
        userRepository.logout()
    }

    override fun setDeviceToken(token: String) {
        userRepository.setDeviceToken(token)
    }
}