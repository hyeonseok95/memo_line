package kr.hs.memo.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*
import kr.hs.memo.R
import kr.hs.memo.base.BaseMemoActivity
import kr.hs.memo.databinding.ActivityDetailBinding
import kr.hs.memo.presentation.detail.adapter.DetailImageAdapter
import kr.hs.memo.presentation.edit.EditActivity
import kr.hs.memo.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseMemoActivity() {
    private val binding by lazy {
        ActivityDetailBinding.inflate(
            layoutInflater,
            null,
            true
        )
    }

    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.detailviewmodel = detailViewModel

        bindView()

        intent.getLongExtra(EXTRA_MEMO_ID, -1).takeIf { it != -1L }?.let {
            detailViewModel.requestMemo(it)
        } ?: finish()
    }

    private fun bindView() {
        toolbar.apply {
            setNavigationOnClickListener {
                onBackPressed()
            }

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.edit -> {
                        startActivity(
                            EditActivity.newIntent(
                                this@DetailActivity,
                                detailViewModel.memoLiveData.value?.id
                            )
                        )
                        finish()
                    }

                    R.id.remove -> {
                        detailViewModel.removeMemo()
                    }
                }
                true
            }
        }

        viewpager_images.adapter = DetailImageAdapter(this)

        detailViewModel.memoLiveData.observe {
            it.photoUrls.forEach {
                viewpager_images.visible()
                text_image_title.visible()
                (viewpager_images.adapter as DetailImageAdapter).add(it)
            }
        }

        detailViewModel.finishLiveData.observe {
            finish()
        }
    }

    companion object {
        private const val EXTRA_MEMO_ID = "extra_memo_id"
        fun newIntent(context: Context, memoId: Long) =
            Intent(context, DetailActivity::class.java).putExtra(EXTRA_MEMO_ID, memoId)

    }
}