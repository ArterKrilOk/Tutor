package space.pixelsg.tutuor.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class BindingFragment<B : androidx.viewbinding.ViewBinding>(
    private val bind: (inflater: LayoutInflater, container: ViewGroup?) -> B
) : Fragment() {

    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = bind(inflater, container)
        return binding.root
    }
}