package kr.hs.memo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.hs.memo.model.Memo

@Entity(tableName = "memo")
data class MemoEntitiy(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val content: CharSequence,
    val photoUrls: MutableList<String>
) {
    fun toMemo() = Memo(id, title, content, photoUrls)
}