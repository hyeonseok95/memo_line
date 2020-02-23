package kr.hs.memo

import android.app.Application
import android.content.Context
import kr.hs.memo.data.dataModule
import kr.hs.memo.presentation.presentationModule
import kr.hs.memo.repository.repositoryModule
import kr.hs.memo.usecase.usecaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class MemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@MemoApplication)
            modules(
                listOf(
                    applicationModule,
                    presentationModule,
                    usecaseModule,
                    repositoryModule,
                    dataModule
                )
            )
        }
    }

    val applicationModule = module {
        single<Context> { this@MemoApplication }
    }
}