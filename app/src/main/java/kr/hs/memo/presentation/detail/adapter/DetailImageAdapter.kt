package kr.hs.memo.presentation.detail.adapter

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.item_memo_image.view.*
import kr.hs.memo.R
import kr.hs.memo.base.BaseMemoAdapter
import kr.hs.memo.base.BaseMemoViewHolder
import kr.hs.memo.gone
import kr.hs.memo.model.MemoPhoto
import kr.hs.memo.onClick
import org.koin.core.KoinComponent
import java.lang.Exception

class DetailImageAdapter(private val activity: Activity) :
    BaseMemoAdapter<DetailImageAdapter.MemoImageViewHolder, MemoPhoto>(),
    KoinComponent {
    override val itemLayoutId: Int = R.layout.item_memo_image
    override fun getItemCount(): Int = itemList.size

    fun add(memoPhoto: MemoPhoto) {
        itemList.add(memoPhoto)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MemoImageViewHolder, position: Int) {
        val currentItem = itemList[position]

        holder.apply {
            when (currentItem) {
                is MemoPhoto.InternalPhoto -> {
                    Picasso.get().load(currentItem.filepath).fit().centerCrop()
                        .into(thumbnail, object : Callback {
                            override fun onSuccess() {}
                            override fun onError(e: Exception?) {
                                itemList.remove(currentItem)
                                if (itemList.isEmpty()) {
                                    activity.viewpager_images.gone()
                                    activity.text_image_title.gone()
                                }

                                notifyDataSetChanged()
                                Toast.makeText(
                                    activity,
                                    "${currentItem.filepath} 이미지를 불러오는데 실패하였습니다.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                }

                is MemoPhoto.ExternalPhoto -> {
                    Picasso.get().load(currentItem.url).fit().centerCrop()
                        .into(thumbnail, object : Callback {
                            override fun onSuccess() {}
                            override fun onError(e: Exception?) {
                                itemList.remove(currentItem)
                                if (itemList.isEmpty()) {
                                    activity.viewpager_images.gone()
                                    activity.text_image_title.gone()
                                }

                                notifyDataSetChanged()
                                Toast.makeText(
                                    activity,
                                    "${currentItem.url} 이미지를 불러오는데 실패하였습니다.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                }
            }

            thumbnail.onClick {

            }

            memoRemoveButton.gone()
        }
    }

    /**
     * Extension
     */
    override fun View.getViewHolder() = MemoImageViewHolder(this)

    /**
     * ViewHolder
     */
    inner class MemoImageViewHolder(view: View) : BaseMemoViewHolder(view) {
        val thumbnail: ImageView = view.img_thumbnail
        val memoRemoveButton: TextView = view.btn_memo_remove
    }
}

