package ru.fefu.helloworld

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.helloworld.databinding.FragmentMyTabBinding
import androidx.lifecycle.ViewModelProvider
import android.util.Log

class MyTabFragment : Fragment() {
    private var _binding: FragmentMyTabBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ActivityViewModel
    private var adapter: ActivityAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = ActivityRepository(requireContext().applicationContext)
        val factory = ActivityViewModel.Factory(repository)
        viewModel = ViewModelProvider(requireActivity(), factory).get(ActivityViewModel::class.java)
        adapter = null
        binding.activityRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.activityRecyclerView.adapter = adapter

        viewModel.activities.observe(viewLifecycleOwner, Observer { entities ->
            Log.d("MyTabFragment", "LiveData update: ${entities.size}")
            val items = mutableListOf<ActivityListItem>()
            if (entities.isEmpty()) {
                binding.emptyText.visibility = View.VISIBLE
                binding.activityRecyclerView.visibility = View.GONE
            } else {
                binding.emptyText.visibility = View.GONE
                binding.activityRecyclerView.visibility = View.VISIBLE
                // Группировка по дате (например, только по дню)
                val grouped = entities.groupBy { java.text.SimpleDateFormat("dd MMMM yyyy").format(java.util.Date(it.startTime)) }
                for ((date, acts) in grouped) {
                    items.add(ActivityListItem.Section(date))
                    items.addAll(acts.map { entity ->
                        ActivityListItem.Activity(
                            distance = "~${(5..20).random()} км", // случайно, т.к. нет поля
                            time = "~${(10..120).random()} мин", // случайно
                            type = when (entity.type) {
                                ActivityType.BIKE -> "Велосипед"
                                ActivityType.RUN -> "Бег"
                                ActivityType.WALK -> "Шаг"
                            },
                            date = date,
                            startTime = java.text.SimpleDateFormat("HH:mm").format(java.util.Date(entity.startTime)),
                            finishTime = java.text.SimpleDateFormat("HH:mm").format(java.util.Date(entity.finishTime))
                        )
                    })
                }
                // Добавляем footer (панель 'Погнали?')
                items.add(ActivityListItem.Footer)
            }
            adapter = ActivityAdapter(
                items,
                onActivityClick = { activity ->
                    val intent = Intent(requireContext(), ActivityDetailsActivity::class.java)
                    intent.putExtra("distance", activity.distance)
                    intent.putExtra("time", activity.time)
                    intent.putExtra("type", activity.type)
                    intent.putExtra("date", activity.date)
                    intent.putExtra("startTime", activity.startTime)
                    intent.putExtra("finishTime", activity.finishTime)
                    startActivity(intent)
                },
                onDeleteClick = { activity ->
                    // Здесь нужно найти entity по совпадающим данным и удалить по id
                    val entityToDelete = entities.find { e ->
                        java.text.SimpleDateFormat("HH:mm").format(java.util.Date(e.startTime)) == activity.startTime &&
                        java.text.SimpleDateFormat("HH:mm").format(java.util.Date(e.finishTime)) == activity.finishTime &&
                        when (e.type) {
                            ActivityType.BIKE -> "Велосипед"
                            ActivityType.RUN -> "Бег"
                            ActivityType.WALK -> "Шаг"
                        } == activity.type
                    }
                    entityToDelete?.let { viewModel.deleteById(it.id) }
                },
                onFooterAction = null
            )
            binding.activityRecyclerView.adapter = adapter
        })
    }

    fun showNewActivityFragment() {
        Log.d("MyTabFragment", "showNewActivityFragment called")
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

    companion object {
        fun getSharedViewModel(fragment: Fragment): ActivityViewModel {
            val repository = ActivityRepository(fragment.requireContext().applicationContext)
            val factory = ActivityViewModel.Factory(repository)
            return ViewModelProvider(fragment.requireActivity(), factory).get(ActivityViewModel::class.java)
        }
    }
} 