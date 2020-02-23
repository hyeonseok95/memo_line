package kr.hs.memo.usecase

import org.koin.dsl.module

val usecaseModule = module {
    factory { GetMemoSize(get()) }
}