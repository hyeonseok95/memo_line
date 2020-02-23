package kr.hs.memo.model

import android.net.Uri

sealed class MemoPhoto {
    data class InternalPhoto(val filepath: Uri) : MemoPhoto()
    data class ExternalPhoto(val url: String) : MemoPhoto()
}