package com.example.technicaltest_qiscus.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.technicaltest_qiscus.domain.model.User
import com.example.technicaltest_qiscus.domain.usecase.user.UserUseCase
import com.example.technicaltest_qiscus.util.Action

class MainViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    private val _userLogin = MutableLiveData<User>()
    val userLogin: LiveData<User> = _userLogin

    private val _onErrorLogin = MutableLiveData<String>()
    val onErrorLogin: LiveData<String> = _onErrorLogin

    private val _currentUser = MutableLiveData<Boolean>()
    val currentUser: LiveData<Boolean> = _currentUser

    fun login(
        username: String,
        password: String,
        name: String
    ) = userUseCase.login(username, password, name, object : Action<User> {
        override fun call(t: User) {
            _userLogin.value = t
        }
    }, object : Action<Throwable> {
        override fun call(t: Throwable) {
            _onErrorLogin.value = t.message
        }
    })

    fun start() = userUseCase.getCurrentUser(object : Action<User> {
        override fun call(t: User) {
            _currentUser.value = true
        }
    }, object : Action<Throwable> {
        override fun call(t: Throwable) {
            _onErrorLogin.value = t.message
        }
    })

}