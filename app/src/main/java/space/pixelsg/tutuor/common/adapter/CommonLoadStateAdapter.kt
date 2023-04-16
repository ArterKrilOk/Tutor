package space.pixelsg.tutuor.common.adapter

import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

abstract class CommonLoadStateAdapter<VH : CommonLoadStateViewHolder<*>> : LoadStateAdapter<VH>() {
    override fun onBindViewHolder(holder: VH, loadState: LoadState) =
        holder.bind(loadState)
}