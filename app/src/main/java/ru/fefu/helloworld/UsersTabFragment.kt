package ru.fefu.helloworld

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.fefu.helloworld.databinding.FragmentUsersTabBinding
import ru.fefu.helloworld.ActivityListItem

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
        val activities = listOf(
            ActivityListItem.Section("Вчера"),
            ActivityListItem.Activity("14.32 км", "2 часа 46 минут", "Сёрфинг 🏄‍♂️", "14 часов назад", user = "van_darkholme", startTime = "14:49", finishTime = "16:31"),
            ActivityListItem.Activity("228 м", "14 часов 48 минут", "Качели", "14 часов назад", user = "techniquepasha"),
            ActivityListItem.Activity("10 км", "1 час 10 минут", "Езда на каяках", "14 часов назад", user = "morgen_shtern"),
            ActivityListItem.Section("Май 2022 года"),
            ActivityListItem.Activity("1000 м", "60 минут", "Велосипед 🚴‍♂️", "29.05.2022", user = "van_darkholme")
        )
        val adapter = ActivityAdapter(
            activities,
            onActivityClick = { activity ->
                val intent = Intent(requireContext(), ActivityDetailsActivity::class.java)
                intent.putExtra("distance", activity.distance)
                intent.putExtra("time", activity.time)
                intent.putExtra("type", activity.type)
                intent.putExtra("date", activity.date)
                intent.putExtra("user", activity.user)
                intent.putExtra("startTime", activity.startTime)
                intent.putExtra("finishTime", activity.finishTime)
                startActivity(intent)
            },
            onDeleteClick = null,
            onFooterAction = null
        )
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.usersRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 