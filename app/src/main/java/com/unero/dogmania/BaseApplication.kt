package com.unero.dogmania

import android.app.Application
import com.unero.dogmania.core.di.adapterModule
import com.unero.dogmania.core.di.repositoryModule
import com.unero.dogmania.core.di.retrofitModule
import com.unero.dogmania.core.di.roomModule
import com.unero.dogmania.di.useCaseModule
import com.unero.dogmania.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    roomModule,
                    retrofitModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    adapterModule
                )
            )
        }
    }
}