package kr.hs.memo.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.hs.memo.model.Memo
import kr.hs.memo.usecase.GetMemo
import kr.hs.memo.usecase.RemoveMemo

class DetailViewModel(private val getMemo: GetMemo, private val removeMemo: RemoveMemo) :
    ViewModel() {
    val memoLiveData = MutableLiveData<Memo>()
    val finishLiveData = MutableLiveData<Unit>()

    fun requestMemo(id: Long) {
        viewModelScope.launch {
            memoLiveData.postValue(getMemo(id))
        }
    }

    fun removeMemo() {
        viewModelScope.launch {
            removeMemo(memoLiveData.value ?: run {
                finishLiveData.postValue(Unit)
                return@launch
            })
            finishLiveData.postValue(Unit)
        }
    }
}