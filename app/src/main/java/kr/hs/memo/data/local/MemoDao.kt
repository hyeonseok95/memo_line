package kr.hs.memo.data.local

import androidx.room.Query
import kr.hs.memo.base.BaseDao
import kr.hs.memo.data.model.MemoEntitiy

interface MemoDao : BaseDao<MemoEntitiy> {
    @Query("SELECT * FROM memo")
    suspend fun selectAllMemo(): List<MemoEntitiy>

    @Query("SELECT * FROM memo WHERE id = :id")
    suspend fun selectMemoEntitiyById(id: Long): MemoEntitiy

    @Query("SELECT COUNT(*) FROM memo")
    suspend fun getAllMemoSize(): Long
}