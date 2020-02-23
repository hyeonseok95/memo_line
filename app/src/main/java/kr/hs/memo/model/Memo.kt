package kr.hs.memo.model

import kr.hs.memo.data.model.MemoEntitiy

data class Memo(
    val id: Long,
    val title: String,
    val content: CharSequence,
    val photoUrls: MutableList<String>
) {
    fun toMemoEntity() = MemoEntitiy(id, title, content, photoUrls)
}