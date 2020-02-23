package kr.hs.memo.usecase

import kr.hs.memo.repository.MemoRepository

class GetMemo(private val memoRepository: MemoRepository) {
    suspend operator fun invoke(id: Long) = memoRepository.getMemoById(id)
}