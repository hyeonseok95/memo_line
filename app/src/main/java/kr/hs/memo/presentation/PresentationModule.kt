package kr.hs.memo.presentation

import kr.hs.memo.presentation.detail.DetailViewModel
import kr.hs.memo.presentation.main.MainViewModel
import kr.hs.memo.presentation.edit.EditViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { EditViewModel(get(), get()) }
    viewModel { DetailViewModel(get(), get()) }
}