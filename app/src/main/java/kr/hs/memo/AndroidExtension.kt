package kr.hs.memo

import android.view.View

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.onClick(action: () -> Unit) {
    //TODO : 따닥 방지
    this.setOnClickListener {
        action()
    }
}