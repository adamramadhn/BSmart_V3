package com.bpkp.bsmartapp

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.bpkp.bsmartapp.di.viewModelModule
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
                    viewModelModule
                )
            )
        }
    }

}