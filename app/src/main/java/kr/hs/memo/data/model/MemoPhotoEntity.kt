package kr.hs.memo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.hs.memo.model.Memo

@Entity(tableName = "memophoto")
data class MemoPhotoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val memo_id: Long,
    val url: String
)