package com.example.technicaltest_qiscus

import android.app.Application
import com.example.technicaltest_qiscus.di.prefModule
import com.example.technicaltest_qiscus.di.userRepositoryModule
import com.example.technicaltest_qiscus.di.userUseCaseModule
import com.example.technicaltest_qiscus.di.viewModelModule
import com.qiscus.sdk.chat.core.QiscusCore
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    userRepositoryModule,
                    userUseCaseModule,
                    viewModelModule,
                    prefModule
                )
            )
        }
        QiscusCore.setup(this, BuildConfig.QISCUS_APP_ID)
    }
}