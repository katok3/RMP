package ru.fefu.helloworld

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.helloworld.databinding.ItemActivityCardBinding

class ActivityAdapter(private val items: List<ActivityItem>) : RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val binding = ItemActivityCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ActivityViewHolder(private val binding: ItemActivityCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ActivityItem) {
            binding.distanceText.text = item.distance
            binding.timeText.text = item.time
            binding.typeText.text = item.type
            binding.dateText.text = item.date
        }
    }
}

data class ActivityItem(
    val distance: String,
    val time: String,
    val type: String,
    val date: String
) 