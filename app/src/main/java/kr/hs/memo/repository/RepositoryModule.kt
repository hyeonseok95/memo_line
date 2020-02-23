package kr.hs.memo.repository

import org.koin.dsl.module

val repositoryModule = module {
    single { MemoRepository(get()) }
}