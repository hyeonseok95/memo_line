package kr.hs.memo.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.hs.memo.base.BaseMemoViewModel
import kr.hs.memo.model.Memo
import kr.hs.memo.usecase.GetMemoList
import kr.hs.memo.usecase.GetMemoSize

class MainViewModel(private val getMemoSize: GetMemoSize, private val getMemoList: GetMemoList) :
    BaseMemoViewModel() {
    val memoSizeLiveData = MutableLiveData<Long>(0)
    val memoListLiveData = MutableLiveData<List<Memo>>()

    // 페이징 대비
    fun requestMemoSize() {
        viewModelScope.launch {
            memoSizeLiveData.postValue(getMemoSize())
        }
    }

    fun requestMemoList() {
        viewModelScope.launch {
            memoListLiveData.postValue(getMemoList())
        }
    }
}