package kr.hs.memo.usecase

import kr.hs.memo.repository.MemoRepository

class GetMemoSize(private val memoRepository: MemoRepository) {
    suspend operator fun invoke() = memoRepository.getAllMemoSize()
}