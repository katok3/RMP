package ru.fefu.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.fefu.helloworld.databinding.FragmentMyTabBinding

class MyTabFragment : Fragment() {
    private var _binding: FragmentMyTabBinding? = null
    private val binding get() = _binding!!

    private val activities = mutableListOf<ActivityItem>()
    private var adapter: ActivityAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ActivityAdapter(activities)
        binding.activityRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.activityRecyclerView.adapter = adapter
        updateUI()
    }

    fun addFakeActivity() {
        activities.add(
            ActivityItem(
                distance = "5.00 км",
                time = "30 минут",
                type = "Бег 🏃",
                date = "только что"
            )
        )
        updateUI()
    }

    private fun updateUI() {
        if (activities.isEmpty()) {
            binding.emptyText.visibility = View.VISIBLE
            binding.activityRecyclerView.visibility = View.GONE
        } else {
            binding.emptyText.visibility = View.GONE
            binding.activityRecyclerView.visibility = View.VISIBLE
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 