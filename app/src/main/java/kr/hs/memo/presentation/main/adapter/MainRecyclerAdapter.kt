package kr.hs.memo.presentation.main.adapter

import android.view.View
import kr.hs.memo.R
import kr.hs.memo.base.BaseMemoAdapter
import kr.hs.memo.base.BaseMemoViewHolder
import kr.hs.memo.model.Memo

class MainRecyclerAdapter : BaseMemoAdapter<MainRecyclerAdapter.MemoRecyclerViewHolder, Memo>() {
    override val itemLayoutId: Int = R.layout.item_main_memo
    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: MemoRecyclerViewHolder, position: Int) {
        holder.apply {

        }
    }

    /**
     * Extension
     */
    override fun View.getViewHolder() = MemoRecyclerViewHolder(this)

    /**
     * ViewHolder
     */
    inner class MemoRecyclerViewHolder(view: View) : BaseMemoViewHolder(view)
}