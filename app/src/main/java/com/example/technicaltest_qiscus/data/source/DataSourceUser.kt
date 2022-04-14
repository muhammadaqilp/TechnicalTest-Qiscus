package com.example.technicaltest_qiscus.data.source

import com.example.technicaltest_qiscus.domain.model.User
import com.example.technicaltest_qiscus.util.Action
import com.example.technicaltest_qiscus.util.AvatarUtil
import com.example.technicaltest_qiscus.util.UserPreferences
import com.example.technicaltest_qiscus.util.UserUtil
import com.qiscus.sdk.chat.core.QiscusCore
import com.qiscus.sdk.chat.core.data.model.QiscusAccount
import com.qiscus.sdk.chat.core.data.remote.QiscusApi
import rx.Emitter
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class DataSourceUser(private val userPreferences: UserPreferences) {

    fun login(
        username: String,
        password: String,
        name: String,
        onSuccess: Action<User>,
        onError: Action<Throwable>
    ) {
        QiscusCore.setUser(username, password)
            .withUsername(name)
            .withAvatarUrl(AvatarUtil.generateAvatar(name))
            .save()
            .map { user -> UserUtil.mapFromQiscus(user) }
            .doOnNext { user -> userPreferences.setCurrentUser(user) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess::call, onError::call)
    }

    fun getCurrentUser(onSuccess: Action<User>, onError: Action<Throwable>) {
        getCurrentUserObservable().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess::call, onError::call)
    }

    fun getUsers(
        page: Long,
        limit: Int,
        searchUsername: String,
        onSuccess: Action<List<User>>,
        onError: Action<Throwable>
    ) {
        QiscusApi.getInstance().getUsers(searchUsername, page, limit.toLong())
            .flatMap { iterable: List<QiscusAccount> -> Observable.from(iterable) }
            .filter { user: QiscusAccount -> !user.equals(userPreferences.getCurrentUser()) }
            .filter { user: QiscusAccount -> user.username != "" }
            .map { qiscusAccount: QiscusAccount -> UserUtil.mapFromQiscus(qiscusAccount) }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess::call, onError::call)
    }

    fun logout() {
        QiscusCore.removeDeviceToken(userPreferences.getCurrentDeviceToken())
        QiscusCore.clearUser()
        userPreferences.sharedPreferences.edit().clear().apply()
    }

    fun setDeviceToken(token: String) {
        userPreferences.setCurrentDeviceToken(token)
    }

    private fun getCurrentUserObservable(): Observable<User> {
        return Observable.create({ subscriber: Emitter<User> ->
            try {
                subscriber.onNext(userPreferences.getCurrentUser())
            } catch (e: Exception) {
                subscriber.onError(e)
            } finally {
                subscriber.onCompleted()
            }
        }, Emitter.BackpressureMode.BUFFER)
    }
}