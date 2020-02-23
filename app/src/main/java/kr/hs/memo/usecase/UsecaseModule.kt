package kr.hs.memo.usecase

import org.koin.dsl.module

val usecaseModule = module {
    factory { GetMemoSize(get()) }
    factory { GetMemoList(get()) }
    factory { UpdateMemo(get()) }
    factory { GetMemo(get()) }
    factory { RemoveMemo(get()) }
}