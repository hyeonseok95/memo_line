package kr.hs.memo.repository

import android.net.Uri
import kr.hs.memo.data.local.MemoDao
import kr.hs.memo.data.local.MemoPhotoDao
import kr.hs.memo.data.model.MemoEntity
import kr.hs.memo.data.model.MemoPhotoEntity
import kr.hs.memo.model.Memo
import kr.hs.memo.model.MemoPhoto
import timber.log.Timber

class MemoRepository(private val memoDao: MemoDao, private val memoPhotoDao: MemoPhotoDao) {
    suspend fun getAllMemoList() = memoDao.selectAllMemoEntity().map {
        val photos = memoPhotoDao.selectMemoEntityById(it.id ?: throw IllegalStateException())
        Memo(it.id, it.title, it.content, photos.map {
            if (it.url.substring(4).apply { Timber.tag("http test").d("$it") } == "http") {
                MemoPhoto.ExternalPhoto(it.url)
            } else {
                MemoPhoto.InternalPhoto(Uri.parse(it.url))
            }
        })
    }

    suspend fun getAllMemoSize() = memoDao.getAllMemoSize()
    suspend fun updateMemo(memo: Memo) {
        if (memo.id == null) {
            val memoId = memoDao.insert(MemoEntity(null, memo.title ?: "", memo.content ?: ""))
            memo.photoUrls.forEach {
                memoPhotoDao.insert(
                    MemoPhotoEntity(
                        null, memoId, when (it) {
                            is MemoPhoto.ExternalPhoto -> it.url
                            is MemoPhoto.InternalPhoto -> it.filepath.toString()
                        }
                    )
                )
            }
        } else {
            memoDao.update(MemoEntity(memo.id, memo.title ?: "", memo.content ?: ""))

            memoPhotoDao.selectMemoEntityById(memo.id).forEach {
                memoPhotoDao.delete(it)
            }

            memo.photoUrls.forEach {
                memoPhotoDao.insert(
                    MemoPhotoEntity(
                        null, memo.id, when (it) {
                            is MemoPhoto.ExternalPhoto -> it.url
                            is MemoPhoto.InternalPhoto -> it.filepath.toString()
                        }
                    )
                )
            }
        }
    }

    suspend fun getMemoById(id: Long): Memo = memoDao.selectMemoEntityById(id).let {
        Memo(it.id, it.title, it.content, memoPhotoDao.selectMemoEntityById(it.id ?: -1).map {
            if (it.url.substring(4).apply { Timber.tag("http test").d("$it") } == "http") {
                MemoPhoto.ExternalPhoto(it.url)
            } else {
                MemoPhoto.InternalPhoto(Uri.parse(it.url))
            }
        })
    }

    suspend fun removeMemo(memo: Memo) {
        memoDao.delete(MemoEntity(memo.id, memo.title ?: "", memo.content ?: ""))
        memoPhotoDao.selectMemoEntityById(memo.id ?: return).forEach {
            memoPhotoDao.delete(it)
        }
    }
}