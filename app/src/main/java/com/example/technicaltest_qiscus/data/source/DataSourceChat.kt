package com.example.technicaltest_qiscus.data.source

import com.example.technicaltest_qiscus.domain.model.User
import com.example.technicaltest_qiscus.util.Action
import com.qiscus.sdk.chat.core.QiscusCore
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom
import com.qiscus.sdk.chat.core.data.remote.QiscusApi
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class DataSourceChat {
    fun getChatRoom(onSuccess: Action<List<QiscusChatRoom>>, onError: Action<Throwable>) {
        Observable.from(QiscusCore.getDataStore().getChatRooms(100))
            .filter { chatRoom -> chatRoom.lastComment != null }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess::call, onError::call)

        QiscusApi.getInstance().getAllChatRooms(true, false, true, 1, 100)
            .flatMap { iterable: List<QiscusChatRoom> ->
                Observable.from(iterable)
            }
            .doOnNext { chatRoom -> QiscusCore.getDataStore().addOrUpdate(chatRoom) }
            .filter { chat -> chat.lastComment.id != 0L }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess::call, onError::call)
    }

    fun createChatRoom(user: User, onSuccess: Action<QiscusChatRoom>, onError: Action<Throwable>) {
        val chatRoom = QiscusCore.getDataStore().getChatRoom(user.id)
        if (chatRoom != null) {
            onSuccess.call(chatRoom)
            return
        }

        QiscusApi.getInstance().chatUser(user.id, null)
            .doOnNext { chat -> QiscusCore.getDataStore().addOrUpdate(chat) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess::call, onError::call)
    }
}