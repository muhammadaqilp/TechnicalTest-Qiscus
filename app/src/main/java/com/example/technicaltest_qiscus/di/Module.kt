package com.example.technicaltest_qiscus.di

import com.example.technicaltest_qiscus.data.UserRepository
import com.example.technicaltest_qiscus.data.source.DataSource
import com.example.technicaltest_qiscus.domain.repository.IUserRepository
import com.example.technicaltest_qiscus.domain.usecase.user.UserInteractor
import com.example.technicaltest_qiscus.domain.usecase.user.UserUseCase
import com.example.technicaltest_qiscus.ui.login.MainViewModel
import com.example.technicaltest_qiscus.util.UserPreferences
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userUseCaseModule = module {
    factory<UserUseCase> { UserInteractor(get()) }
}

val userRepositoryModule = module {
    single { DataSource(get()) }
    single<IUserRepository> { UserRepository(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

val prefModule = module {
    factory { UserPreferences(get()) }
}