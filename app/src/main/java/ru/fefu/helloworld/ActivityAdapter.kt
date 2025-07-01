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
    object Footer : ActivityListItem()
}

class ActivityAdapter(
    private val items: List<ActivityListItem>,
    private val onActivityClick: ((ActivityListItem.Activity) -> Unit)? = null,
    private val onDeleteClick: ((ActivityListItem.Activity) -> Unit)? = null,
    private val onFooterAction: (() -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_SECTION = 0
        private const val VIEW_TYPE_ACTIVITY = 1
        private const val VIEW_TYPE_FOOTER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ActivityListItem.Section -> VIEW_TYPE_SECTION
            is ActivityListItem.Activity -> VIEW_TYPE_ACTIVITY
            is ActivityListItem.Footer -> VIEW_TYPE_FOOTER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SECTION -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_activity_section, parent, false)
                SectionViewHolder(view)
            }
            VIEW_TYPE_FOOTER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_new_activity, parent, false)
                FooterViewHolder(view, onFooterAction)
            }
            else -> {
                val binding = ItemActivityCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ActivityViewHolder(binding, onActivityClick, onDeleteClick)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ActivityListItem.Section -> (holder as SectionViewHolder).bind(item)
            is ActivityListItem.Activity -> (holder as ActivityViewHolder).bind(item)
            is ActivityListItem.Footer -> (holder as FooterViewHolder).bind()
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
        private val onClick: ((ActivityListItem.Activity) -> Unit)?,
        private val onDelete: ((ActivityListItem.Activity) -> Unit)?
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
            binding.deleteButton.setOnClickListener {
                currentItem?.let { onDelete?.invoke(it) }
            }
        }
        init {
            binding.root.setOnClickListener {
                currentItem?.let { onClick?.invoke(it) }
            }
        }
    }

    class FooterViewHolder(
        view: View,
        private val onFooterAction: (() -> Unit)?
    ) : RecyclerView.ViewHolder(view) {
        fun bind() {
            // Можно добавить обработку кнопок панели 'Погнали?' через onFooterAction
        }
    }
} 