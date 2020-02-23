package kr.hs.memo.model

sealed class MemoPhoto {
    data class internalPhoto(val filepath: String) : MemoPhoto()
    data class externalPhoto(val url: String) : MemoPhoto()
}