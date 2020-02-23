package kr.hs.memo.presentation.main

import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_main.*
import kr.hs.memo.R
import kr.hs.memo.base.BaseMemoActivity
import kr.hs.memo.databinding.ActivityMainBinding
import kr.hs.memo.presentation.main.adapter.MainRecyclerAdapter
import kr.hs.memo.presentation.memo.MemoActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        binding.lifecycleOwner = this
        setContentView(binding.root)

        bindView()
    }

    private fun bindView() {
        recycler_main.adapter = MainRecyclerAdapter()

        /* 윈도우에서 람다식 충돌남 */
        appbar_main.addOnOffsetChangedListener(object :
            AppBarLayout.BaseOnOffsetChangedListener<AppBarLayout> {
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                linear_main_title.alpha =
                    1.0f - kotlin.math.abs(verticalOffset / appBarLayout.totalScrollRange.toFloat() * 2.0f)

                linear_main_subtitle.alpha =
                    1.0f - kotlin.math.abs(verticalOffset / appBarLayout.totalScrollRange.toFloat() * 2.0f)

                linear_main_mini_title.alpha =
                    kotlin.math.abs(verticalOffset / appBarLayout.totalScrollRange.toFloat() * 2.0f)
            }
        })

        btn_main_add.setOnClickListener {
            startActivity(MemoActivity.newIntent(this@MainActivity))
        }

        mainViewModel.requestMemoSize()
    }
}
