package kr.hs.memo.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData

abstract class BaseMemoActivity : AppCompatActivity() {

    protected fun <T> LiveData<T>.observe(onChanged: (T) -> Unit) {
        this.observe(this@BaseMemoActivity, androidx.lifecycle.Observer {
            onChanged(it)
        })
    }
}