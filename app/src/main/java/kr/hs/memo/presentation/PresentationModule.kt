package kr.hs.memo.presentation

import kr.hs.memo.presentation.main.MainViewModel
import kr.hs.memo.presentation.memo.MemoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { MemoViewModel() }
}