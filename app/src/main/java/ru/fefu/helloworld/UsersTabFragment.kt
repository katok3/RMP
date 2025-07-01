package ru.fefu.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.fefu.helloworld.databinding.FragmentUsersTabBinding

class UsersTabFragment : Fragment() {
    private var _binding: FragmentUsersTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsersTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activities = getUserActivities()
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.usersRecyclerView.adapter = ActivityAdapter(activities)
    }

    private fun getUserActivities(): List<ActivityItem> {
        // Пока возвращаем список-заглушку
        return listOf(
            ActivityItem("14.32 км", "2 часа 46 минут", "Сёрфинг 🏄‍♂️", "14 часов назад"),
            ActivityItem("228 м", "14 часов 48 минут", "Качели", "14 часов назад"),
            ActivityItem("10 км", "1 час 10 минут", "Езда на каяках", "14 часов назад")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 