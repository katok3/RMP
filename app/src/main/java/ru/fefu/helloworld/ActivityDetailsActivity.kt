package ru.fefu.helloworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.fefu.helloworld.databinding.ActivityDetailsBinding
import android.view.MenuItem
import android.content.Intent
import android.view.View

class ActivityDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_share) {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Моя активность: ${binding.activityType.text}, ${binding.distanceText.text}, ${binding.timeText.text}")
                startActivity(Intent.createChooser(shareIntent, "Поделиться активностью"))
                true
            } else false
        }

        val distance = intent.getStringExtra("distance") ?: ""
        val time = intent.getStringExtra("time") ?: ""
        val type = intent.getStringExtra("type") ?: ""
        val date = intent.getStringExtra("date") ?: ""
        val user = intent.getStringExtra("user")
        val startTime = intent.getStringExtra("startTime")
        val finishTime = intent.getStringExtra("finishTime")

        binding.distanceText.text = distance
        binding.timeText.text = time
        binding.activityType.text = type
        binding.dateText.text = date
        if (!startTime.isNullOrEmpty() && !finishTime.isNullOrEmpty()) {
            binding.startFinishLabel.text = "Старт $startTime    Финиш $finishTime"
            binding.startFinishLabel.visibility = View.VISIBLE
        } else {
            binding.startFinishLabel.visibility = View.GONE
        }
        // Можно добавить отображение user, если нужно
    }
} 