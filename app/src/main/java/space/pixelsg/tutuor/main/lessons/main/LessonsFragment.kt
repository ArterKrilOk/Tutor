package space.pixelsg.tutuor.main.lessons.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import space.pixelsg.tutuor.R
import space.pixelsg.tutuor.databinding.LessonsFragmentBinding

class LessonsFragment : Fragment() {

    private lateinit var binding: LessonsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        println("LessonsFragment " + this.toString())

        binding = LessonsFragmentBinding.inflate(inflater, container, false)

        binding.viewPager.adapter = LessonsFragmentStateAdapter(this)
        binding.viewPager.currentItem = 1

        TabLayoutMediator(binding.tabLayout, binding.viewPager, true) { tab, pos ->
            tab.text = resources.getStringArray(R.array.lessons_tab_names)[pos]
        }.attach()

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_actionLessons_to_createLessonBottomFragment)
        }

        return binding.root
    }
}