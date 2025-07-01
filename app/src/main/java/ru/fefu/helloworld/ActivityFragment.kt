package ru.fefu.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.fefu.helloworld.databinding.FragmentActivityBinding
import androidx.viewpager2.adapter.FragmentStateAdapter
import android.widget.Toast

class ActivityFragment : Fragment() {
    private var _binding: FragmentActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ActivityPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "Мои" else "Пользователей"
        }.attach()

        // FAB видим только на вкладке 'Мои'
        binding.viewPager.registerOnPageChangeCallback(object : androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.fab.visibility = if (position == 0) View.VISIBLE else View.GONE
            }
        })
        binding.fab.visibility = View.VISIBLE

        binding.fab.setOnClickListener {
            val fragment = childFragmentManager.findFragmentByTag("f0")
            if (fragment is MyTabFragment) {
                fragment.addFakeActivity()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class ActivityPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2
    override fun createFragment(position: Int): Fragment {
        return if (position == 0) MyTabFragment() else UsersTabFragment()
    }
} 