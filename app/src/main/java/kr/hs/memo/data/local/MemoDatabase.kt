package kr.hs.memo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kr.hs.memo.data.model.MemoEntity
import kr.hs.memo.data.model.MemoPhotoEntity

@Database(entities = [MemoEntity::class, MemoPhotoEntity::class], version = 1, exportSchema = false)
abstract class MemoDatabase : RoomDatabase() {
    abstract val memoDao: MemoDao
    abstract val memoPhotoDao: MemoPhotoDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, MemoDatabase::class.java, "memo_db.db").build()
    }
}