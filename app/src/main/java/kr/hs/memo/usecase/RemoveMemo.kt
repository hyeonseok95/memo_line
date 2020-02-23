package kr.hs.memo.usecase

import kr.hs.memo.model.Memo
import kr.hs.memo.repository.MemoRepository

class RemoveMemo(private val memoRepository: MemoRepository) {
    suspend operator fun invoke(memo: Memo) = memoRepository.removeMemo(memo)
}