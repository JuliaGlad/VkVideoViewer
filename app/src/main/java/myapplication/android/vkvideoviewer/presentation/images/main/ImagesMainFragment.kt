package myapplication.android.vkvideoviewer.presentation.images.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import myapplication.android.vkvideoviewer.R
import myapplication.android.vkvideoviewer.databinding.FragmentMainImagesBinding
import myapplication.android.vkvideoviewer.presentation.images.images.ImagesFragment


class ImagesMainFragment : Fragment() {

    private var _binding: FragmentMainImagesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainImagesBinding.inflate(layoutInflater)
        addFragments()
        return binding.root
    }

    private fun addFragments() {
        val transaction = childFragmentManager.beginTransaction()
        val categories = resources.getStringArray(R.array.image_categories)
        for (category in categories) {
            val fragment = ImagesFragment.getInstance(category)
            transaction.add(R.id.viewPager, fragment, category)
            if (category != categories[0]) transaction.hide(fragment)
        }
        transaction.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabs()
    }

    private fun initTabs() {
        val categories = resources.getStringArray(R.array.image_categories)
        for (i in categories) {
            binding.tabs.addTab(binding.tabs.newTab().setText(i))
        }
        binding.tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                showFragment(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                Log.i("TabUnselected", "unselected: ${tab.position}")
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                Log.i("Tab reselected", "reselected: ${tab.position}")
            }
        })

    }

    private fun showFragment(position: Int) {
        with(childFragmentManager) {
          val transaction = beginTransaction()
            val categories = resources.getStringArray(R.array.image_categories)
            for (i in 0 until fragments.size) {
                val fragment: Fragment = fragments[i]
                transaction.hide(fragment)
            }

            val tag = categories[position]
            val fragmentToShow = childFragmentManager.findFragmentByTag(tag)
            if (fragmentToShow != null) {
                transaction.show(fragmentToShow)
            }
            transaction.commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}