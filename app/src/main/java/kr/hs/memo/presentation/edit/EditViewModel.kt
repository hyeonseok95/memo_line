package kr.hs.memo.presentation.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.hs.memo.base.BaseMemoViewModel
import kr.hs.memo.model.Memo
import kr.hs.memo.model.MemoPhoto
import kr.hs.memo.usecase.GetMemo
import kr.hs.memo.usecase.UpdateMemo

class EditViewModel(private val updateMemo: UpdateMemo, private val getMemo: GetMemo) :
    BaseMemoViewModel() {
    val memoLiveData = MutableLiveData<Memo>()
    val finishLiveData = MutableLiveData<Unit>()

    fun saveMemo(title: String, content: String, photos: List<MemoPhoto>) {
        viewModelScope.launch {
            updateMemo(Memo(memoLiveData.value?.id, title, content, photos))
            finishLiveData.postValue(Unit)
        }
    }

    fun requestMemo(id: Long) {
        viewModelScope.launch {
            memoLiveData.postValue(getMemo(id))
        }
    }
}