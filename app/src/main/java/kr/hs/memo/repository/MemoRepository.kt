package kr.hs.memo.repository

import kr.hs.memo.data.local.MemoDatabase
import kr.hs.memo.model.Memo

class MemoRepository(private val memoDatabase: MemoDatabase) {
    suspend fun getAllMemoList() = memoDatabase.memoDao.selectAllMemo().map { it.toMemo() }
    suspend fun getAllMemoSize() = memoDatabase.memoDao.getAllMemoSize()
    suspend fun getMemoById(id: Long) = memoDatabase.memoDao.selectMemoEntitiyById(id).toMemo()
    suspend fun updateMemo(memo: Memo) = memoDatabase.memoDao.update(memo.toMemoEntity())
    suspend fun removeMemo(memo: Memo) = memoDatabase.memoDao.delete(memo.toMemoEntity())
}