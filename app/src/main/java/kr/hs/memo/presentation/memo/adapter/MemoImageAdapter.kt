package kr.hs.memo.presentation.memo.adapter

import android.view.View
import kr.hs.memo.R
import kr.hs.memo.base.BaseMemoAdapter
import kr.hs.memo.base.BaseMemoViewHolder
import kr.hs.memo.model.MemoPhoto

class MemoImageAdapter : BaseMemoAdapter<MemoImageAdapter.MemoImageViewHolder, MemoPhoto>() {
    init {
        itemList.apply {
            for (i in 0..10) add(MemoPhoto.externalPhoto(""))
        }
    }

    override val itemLayoutId: Int = R.layout.item_memo_image
    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: MemoImageViewHolder, position: Int) {

    }

    /**
     * Extension
     */
    override fun View.getViewHolder() = MemoImageViewHolder(this)

    /**
     * ViewHolder
     */
    inner class MemoImageViewHolder(view: View) : BaseMemoViewHolder(view)
}

