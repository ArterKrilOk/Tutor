package space.pixelsg.tutuor.common.adapter

import androidx.paging.LoadState
import androidx.viewbinding.ViewBinding

abstract class CommonLoadStateViewHolder<B : ViewBinding>(binding: B) :
    CommonViewHolder<LoadState, B>(binding)