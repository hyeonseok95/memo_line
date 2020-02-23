package kr.hs.memo.data.local

import androidx.room.Dao
import androidx.room.Query
import kr.hs.memo.base.BaseDao
import kr.hs.memo.data.model.MemoEntity
import kr.hs.memo.data.model.MemoPhotoEntity

@Dao
interface MemoPhotoDao : BaseDao<MemoPhotoEntity> {
    @Query("SELECT * FROM memophoto WHERE memo_id = :memoId")
    suspend fun selectMemoEntitiyById(memoId: Long): List<MemoPhotoEntity>
}