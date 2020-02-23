package kr.hs.memo.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseMemoAdapter<VH : BaseMemoViewHolder, M> : RecyclerView.Adapter<VH>() {
    protected open val itemList = mutableListOf<M>()
    abstract val itemLayoutId: Int
    abstract override fun getItemCount(): Int
    abstract override fun onBindViewHolder(holder: VH, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        LayoutInflater.from(parent.context).inflate(
            itemLayoutId,
            parent,
            false
        ).getViewHolder()

    /**
     * Extension
     */
    protected abstract fun View.getViewHolder(): VH
}