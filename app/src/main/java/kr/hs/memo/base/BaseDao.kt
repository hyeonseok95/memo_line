package kr.hs.memo.base

import androidx.room.*

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T): Long

    @Delete
    suspend fun delete(obj: T)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(obj: T)
}