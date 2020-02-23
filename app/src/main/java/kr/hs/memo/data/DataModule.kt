package kr.hs.memo.data

import androidx.room.Room
import kr.hs.memo.data.local.MemoDatabase
import org.koin.dsl.module

val dataModule = module {
    single { Room.databaseBuilder(get(), MemoDatabase::class.java, "memo_db.db").build() }
}