package kr.hs.memo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.hs.memo.model.Memo

@Entity(tableName = "memo")
data class MemoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val title: String,
    val content: String
)