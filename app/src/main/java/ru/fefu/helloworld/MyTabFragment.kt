package ru.fefu.helloworld

import android.content.Intent
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

    private val activities = mutableListOf<ActivityListItem>()
    private var adapter: ActivityAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ActivityAdapter(activities) { activity ->
            val intent = Intent(requireContext(), ActivityDetailsActivity::class.java)
            intent.putExtra("distance", activity.distance)
            intent.putExtra("time", activity.time)
            intent.putExtra("type", activity.type)
            intent.putExtra("date", activity.date)
            intent.putExtra("startTime", activity.startTime)
            intent.putExtra("finishTime", activity.finishTime)
            startActivity(intent)
        }
        binding.activityRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.activityRecyclerView.adapter = adapter
        updateUI()
    }

    fun addFakeActivity() {
        // Добавляем в секцию "Вчера"
        if (activities.isEmpty() || activities[0] !is ActivityListItem.Section) {
            activities.add(0, ActivityListItem.Section("Вчера"))
        }
        activities.add(1, ActivityListItem.Activity(
            distance = "5.00 км",
            time = "30 минут",
            type = "Бег 🏃",
            date = "только что",
            startTime = "14:49",
            finishTime = "16:31"
        ))
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

    fun showNewActivityFragment() {
        binding.newActivityContainer.visibility = View.VISIBLE
        childFragmentManager.beginTransaction()
            .replace(R.id.newActivityContainer, NewActivityFragment())
            .commit()
    }

    fun hideNewActivityFragment() {
        binding.newActivityContainer.visibility = View.GONE
        childFragmentManager.beginTransaction()
            .remove(childFragmentManager.findFragmentById(R.id.newActivityContainer) ?: return)
            .commit()
    }

    fun showActiveActivityFragment() {
        binding.newActivityContainer.visibility = View.VISIBLE
        childFragmentManager.beginTransaction()
            .replace(R.id.newActivityContainer, ActiveActivityFragment())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 