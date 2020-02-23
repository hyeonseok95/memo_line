package kr.hs.memo.data

import kr.hs.memo.data.local.MemoDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { MemoDatabase.buildDatabase(androidContext()) }

    single {
        val db: MemoDatabase by inject()
        db.memoDao
    }

    single {
        val db: MemoDatabase by inject()
        db.memoPhotoDao
    }
}