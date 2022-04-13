package com.example.technicaltest_qiscus.util

import com.example.technicaltest_qiscus.domain.model.User
import com.qiscus.sdk.chat.core.data.model.QiscusAccount

object UserUtil {

    fun mapFromQiscus(account: QiscusAccount): User {
        return User(account.email, account.username, account.avatar)
    }
}