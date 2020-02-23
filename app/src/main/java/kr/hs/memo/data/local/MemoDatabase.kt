package kr.hs.memo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.hs.memo.data.model.MemoEntitiy

@Database(entities = [MemoEntitiy::class], version = 1, exportSchema = false)
abstract class MemoDatabase : RoomDatabase() {
    abstract val memoDao: MemoDao
}