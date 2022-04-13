package com.example.technicaltest_qiscus.data

import com.example.technicaltest_qiscus.data.source.DataSource
import com.example.technicaltest_qiscus.domain.model.User
import com.example.technicaltest_qiscus.domain.repository.IUserRepository
import com.example.technicaltest_qiscus.util.Action

class UserRepository(private val dataSource: DataSource) : IUserRepository {

    override fun login(
        username: String,
        password: String,
        name: String,
        onSuccess: Action<User>,
        onError: Action<Throwable>
    ) {
        dataSource.login(username, password, name, onSuccess, onError)
    }

    override fun getCurrentUser(onSuccess: Action<User>, onError: Action<Throwable>) {
        dataSource.getCurrentUser(onSuccess, onError)
    }

    override fun getUsers(
        page: Long,
        limit: Int,
        query: String,
        onSuccess: Action<List<User>>,
        onError: Action<Throwable>
    ) {
        dataSource.getUsers(page, limit, query, onSuccess, onError)
    }

    override fun logout() {
        dataSource.logout()
    }

    override fun setDeviceToken(token: String) {
        dataSource.setDeviceToken(token)
    }
}