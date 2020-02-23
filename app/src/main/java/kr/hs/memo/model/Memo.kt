package kr.hs.memo.model

data class Memo(
    val id: Long?,
    val title: String?,
    val content: String?,
    val photoUrls: List<MemoPhoto>
)