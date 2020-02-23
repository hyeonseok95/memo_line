package kr.hs.memo.data.local

import androidx.room.Dao
import androidx.room.Query
import kr.hs.memo.base.BaseDao
import kr.hs.memo.data.model.MemoEntity

@Dao
interface MemoDao : BaseDao<MemoEntity> {
    @Query("SELECT * FROM memo")
    suspend fun selectAllMemoEntity(): List<MemoEntity>

    @Query("SELECT * FROM memo WHERE id = :id")
    suspend fun selectMemoEntityById(id: Long): MemoEntity

    @Query("SELECT COUNT(*) FROM memo")
    suspend fun getAllMemoSize(): Long
}