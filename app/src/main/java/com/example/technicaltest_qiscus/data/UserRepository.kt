package com.example.technicaltest_qiscus.data

import com.example.technicaltest_qiscus.data.source.DataSourceUser
import com.example.technicaltest_qiscus.domain.model.User
import com.example.technicaltest_qiscus.domain.repository.user.IUserRepository
import com.example.technicaltest_qiscus.util.Action

class UserRepository(private val dataSourceUser: DataSourceUser) : IUserRepository {

    override fun login(
        username: String,
        password: String,
        name: String,
        onSuccess: Action<User>,
        onError: Action<Throwable>
    ) {
        dataSourceUser.login(username, password, name, onSuccess, onError)
    }

    override fun getCurrentUser(onSuccess: Action<User>, onError: Action<Throwable>) {
        dataSourceUser.getCurrentUser(onSuccess, onError)
    }

    override fun getUsers(
        page: Long,
        limit: Int,
        query: String,
        onSuccess: Action<List<User>>,
        onError: Action<Throwable>
    ) {
        dataSourceUser.getUsers(page, limit, query, onSuccess, onError)
    }

    override fun logout() {
        dataSourceUser.logout()
    }

    override fun setDeviceToken(token: String) {
        dataSourceUser.setDeviceToken(token)
    }
}