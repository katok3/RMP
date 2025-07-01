package ru.fefu.helloworld

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import ru.fefu.helloworld.databinding.ItemActivityCardBinding
import android.view.View

sealed class ActivityListItem {
    data class Section(val title: String) : ActivityListItem()
    data class Activity(
        val distance: String,
        val time: String,
        val type: String,
        val date: String,
        val user: String? = null, // для вкладки пользователей
        val startTime: String? = null,
        val finishTime: String? = null
    ) : ActivityListItem()
}

class ActivityAdapter(
    private val items: List<ActivityListItem>,
    private val onActivityClick: ((ActivityListItem.Activity) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_SECTION = 0
        private const val VIEW_TYPE_ACTIVITY = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ActivityListItem.Section -> VIEW_TYPE_SECTION
            is ActivityListItem.Activity -> VIEW_TYPE_ACTIVITY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SECTION -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_activity_section, parent, false)
                SectionViewHolder(view)
            }
            else -> {
                val binding = ItemActivityCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ActivityViewHolder(binding, onActivityClick)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ActivityListItem.Section -> (holder as SectionViewHolder).bind(item)
            is ActivityListItem.Activity -> (holder as ActivityViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    class SectionViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ActivityListItem.Section) {
            (view as TextView).text = item.title
        }
    }

    class ActivityViewHolder(
        private val binding: ItemActivityCardBinding,
        private val onClick: ((ActivityListItem.Activity) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentItem: ActivityListItem.Activity? = null
        fun bind(item: ActivityListItem.Activity) {
            currentItem = item
            binding.distanceText.text = item.distance
            binding.timeText.text = item.time
            binding.typeText.text = item.type
            binding.dateText.text = item.date
            if (item.user != null) {
                binding.typeText.text = "${item.type}  @${item.user}"
            }
        }
        init {
            binding.root.setOnClickListener {
                currentItem?.let { onClick?.invoke(it) }
            }
        }
    }
} 