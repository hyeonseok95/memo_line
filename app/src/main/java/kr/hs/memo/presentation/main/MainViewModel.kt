package kr.hs.memo.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.hs.memo.base.BaseMemoViewModel
import kr.hs.memo.usecase.GetMemoSize

class MainViewModel(private val getMemoSize: GetMemoSize) : BaseMemoViewModel() {
    val memoSizeLiveData = MutableLiveData<Long>()

    fun requestMemoSize() {
        viewModelScope.launch {
            memoSizeLiveData.postValue(getMemoSize())
        }
    }
}