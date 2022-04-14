package com.example.technicaltest_qiscus.di

import com.example.technicaltest_qiscus.data.ChatRepository
import com.example.technicaltest_qiscus.data.UserRepository
import com.example.technicaltest_qiscus.data.source.DataSourceChat
import com.example.technicaltest_qiscus.data.source.DataSourceUser
import com.example.technicaltest_qiscus.domain.repository.chat.IChatRepository
import com.example.technicaltest_qiscus.domain.repository.user.IUserRepository
import com.example.technicaltest_qiscus.domain.usecase.chat.ChatInteractor
import com.example.technicaltest_qiscus.domain.usecase.chat.ChatUseCase
import com.example.technicaltest_qiscus.domain.usecase.user.UserInteractor
import com.example.technicaltest_qiscus.domain.usecase.user.UserUseCase
import com.example.technicaltest_qiscus.ui.listchat.ListChatViewModel
import com.example.technicaltest_qiscus.ui.login.MainViewModel
import com.example.technicaltest_qiscus.util.UserPreferences
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userUseCaseModule = module {
    factory<UserUseCase> { UserInteractor(get()) }
}

val chatUseCaseModule = module {
    factory<ChatUseCase> { ChatInteractor(get()) }
}

val userRepositoryModule = module {
    single { DataSourceUser(get()) }
    single<IUserRepository> { UserRepository(get()) }
}

val chatRepositoryModule = module {
    single { DataSourceChat() }
    single<IChatRepository> { ChatRepository(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { ListChatViewModel(get()) }
}

val prefModule = module {
    factory { UserPreferences(get()) }
}