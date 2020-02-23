package kr.hs.memo.presentation.main.adapter

import android.app.Activity
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_main_memo.view.*
import kr.hs.memo.R
import kr.hs.memo.base.BaseMemoAdapter
import kr.hs.memo.base.BaseMemoViewHolder
import kr.hs.memo.gone
import kr.hs.memo.model.Memo
import kr.hs.memo.model.MemoPhoto
import kr.hs.memo.onClick
import kr.hs.memo.presentation.detail.DetailActivity
import kr.hs.memo.visible
import timber.log.Timber

class MainRecyclerAdapter(private val activity: Activity) : BaseMemoAdapter<MainRecyclerAdapter.MemoRecyclerViewHolder, Memo>() {
    override val itemLayoutId: Int = R.layout.item_main_memo
    override fun getItemCount(): Int = itemList.size

    fun removeAllAndAdd(memos: List<Memo>) {
        itemList.clear()
        itemList.addAll(memos)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MemoRecyclerViewHolder, position: Int) {
        val currentItem = itemList[position]

        holder.itemView.apply {
            text_title.text = currentItem.title ?: ""
            text_content.text = currentItem.content ?: ""

            when (currentItem.photoUrls.isNotEmpty()) {
                true -> {
                    img_thumbnail.visible()
                    when (val firstItem = currentItem.photoUrls.first()) {
                        is MemoPhoto.InternalPhoto -> {
                            Picasso.get().load(firstItem.filepath).fit().centerCrop()
                                .into(img_thumbnail)
                        }

                        is MemoPhoto.ExternalPhoto -> {
                            Picasso.get().load(firstItem.url).fit().centerCrop().into(img_thumbnail)
                        }
                    }
                }
                else -> img_thumbnail.gone()
            }

            onClick {
                activity.startActivity(DetailActivity.newIntent(activity, currentItem.id ?: return@onClick))
            }
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