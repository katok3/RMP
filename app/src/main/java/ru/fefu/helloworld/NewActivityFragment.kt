package ru.fefu.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import ru.fefu.helloworld.databinding.FragmentNewActivityBinding
import ru.fefu.helloworld.ActivityEntity
import ru.fefu.helloworld.ActivityType
import androidx.lifecycle.ViewModelProvider
import android.util.Log
import android.widget.Toast

class NewActivityFragment : Fragment() {
    private var _binding: FragmentNewActivityBinding? = null
    private val binding get() = _binding!!

    private val activityTypes = listOf(
        ActivityType.BIKE,
        ActivityType.RUN,
        ActivityType.WALK
    )
    private var selectedType = 0

    private lateinit var viewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(), "NewActivityFragment onViewCreated", Toast.LENGTH_SHORT).show()
        Log.d("NewActivityFragment", "onViewCreated")
        val repository = ActivityRepository(requireContext().applicationContext)
        val factory = ActivityViewModel.Factory(repository)
        viewModel = ViewModelProvider(requireActivity(), factory).get(ActivityViewModel::class.java)
        val adapter = ActivityTypeAdapter(activityTypes) { pos ->
            val now = System.currentTimeMillis()
            val coords = listOf(Pair(43.1, 131.9), Pair(43.2, 132.0))
            val entity = ActivityEntity(
                type = when (pos) {
                    0 -> ActivityType.BIKE
                    1 -> ActivityType.RUN
                    else -> ActivityType.WALK
                },
                startTime = now,
                finishTime = now + (10..120).random() * 60 * 1000L,
                coordinates = ru.fefu.helloworld.Converters().fromCoordinates(coords)
            )
            Log.d("NewActivityFragment", "addActivity: $entity")
            viewModel.addActivity(entity)
            (parentFragment as? MyTabFragment)?.showActiveActivityFragment()
        }
        binding.activityTypeRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.activityTypeRecyclerView.adapter = adapter

        // Закрытие по нажатию вне карточки или по кнопке назад
        binding.mapView.setOnClickListener {
            (parentFragment as? MyTabFragment)?.hideNewActivityFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// Адаптер для выбора типа активности
class ActivityTypeAdapter(
    private val items: List<ActivityType>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<ActivityTypeAdapter.ViewHolder>() {
    private var selected = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activity_type, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.icon.setImageResource(item.iconRes)
        holder.name.text = when (item) {
            ActivityType.BIKE -> "Велосипед"
            ActivityType.RUN -> "Бег"
            ActivityType.WALK -> "Шаг"
        }
        holder.card.isChecked = position == selected
        holder.card.setOnClickListener {
            val prev = selected
            selected = position
            notifyItemChanged(prev)
            notifyItemChanged(selected)
            onClick(position)
        }
    }

    override fun getItemCount() = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.typeIcon)
        val name: TextView = view.findViewById(R.id.typeName)
        val card: MaterialCardView = view as MaterialCardView
    }
} 