package myapplication.android.vkvideoviewer.presentation.images.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import myapplication.android.vkvideoviewer.R
import myapplication.android.vkvideoviewer.databinding.FragmentMainImagesBinding
import myapplication.android.vkvideoviewer.presentation.images.images.ImagesFragment
import myapplication.android.vkvideoviewer.presentation.images.main.pager.PagerAdapter

class ImagesMainFragment : Fragment() {

    private var _binding: FragmentMainImagesBinding? = null
    private val binding get() = _binding!!
    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainImagesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPager()
        initTabs()
    }

    private fun initTabs() {
        val categories = resources.getStringArray(R.array.image_categories)
        TabLayoutMediator(binding.tabs, binding.viewPager){ tab, position ->
            tab.text = categories[position]
        }.attach()
    }

    private fun initPager() {
        pagerAdapter = PagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = pagerAdapter
        val categories = resources.getStringArray(R.array.image_categories)
        val instances = mutableListOf<ImagesFragment>()
        categories.forEach { instances.add(ImagesFragment.getInstance(it)) }
        pagerAdapter.update(instances)
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}