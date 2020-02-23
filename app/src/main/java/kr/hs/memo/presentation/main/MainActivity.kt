package kr.hs.memo.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_main.*
import kr.hs.memo.base.BaseMemoActivity
import kr.hs.memo.databinding.ActivityMainBinding
import kr.hs.memo.model.Memo
import kr.hs.memo.presentation.main.adapter.MainRecyclerAdapter
import kr.hs.memo.presentation.edit.EditActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.IllegalStateException

class MainActivity : BaseMemoActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater,
            null,
            true
        )
    }

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        bindView()
    }

    override fun onResume() {
        super.onResume()

        mainViewModel.requestMemoList()
        mainViewModel.requestMemoSize()
    }

    private fun bindView() {
        recycler_main.adapter = MainRecyclerAdapter(this)

        /* 윈도우에서 람다식 충돌남 */
        appbar_main.addOnOffsetChangedListener(AppBarLayout.BaseOnOffsetChangedListener<AppBarLayout> { appBarLayout, verticalOffset ->
            linear_main_title.alpha =
                1.0f - kotlin.math.abs(verticalOffset / appBarLayout.totalScrollRange.toFloat() * 2.0f)

            linear_main_subtitle.alpha =
                1.0f - kotlin.math.abs(verticalOffset / appBarLayout.totalScrollRange.toFloat() * 2.0f)

            linear_main_mini_title.alpha =
                kotlin.math.abs(verticalOffset / appBarLayout.totalScrollRange.toFloat() * 2.0f)
        })

        btn_main_add.setOnClickListener {
            startActivity(EditActivity.newIntent(this@MainActivity))
        }

        mainViewModel.memoListLiveData.observe {
            (recycler_main.adapter as MainRecyclerAdapter)
                .removeAllAndAdd(it.map {
                    Memo(
                        it.id ?: throw IllegalStateException(),
                        it.title,
                        it.content,
                        it.photoUrls
                    )
                })
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
