package kr.hs.memo.usecase

import kr.hs.memo.repository.MemoRepository

class GetMemoList(private val memoRepository: MemoRepository) {
    suspend operator fun invoke() = memoRepository.getAllMemoList()
}