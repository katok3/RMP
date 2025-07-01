package ru.fefu.helloworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.fefu.helloworld.databinding.ActivityMainScreenBinding
import androidx.fragment.app.Fragment

class MainScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(binding.fragmentContainer.id, ActivityFragment(), "activity_fragment")
                .commit()
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_activity -> switchToFragment(ActivityFragment(), "activity_fragment")
                R.id.nav_profile -> switchToFragment(ProfileFragment(), "profile_fragment")
            }
            true
        }
    }

    private fun switchToFragment(fragment: Fragment, tag: String) {
        val current = supportFragmentManager.findFragmentByTag(tag)
        val transaction = supportFragmentManager.beginTransaction()
        supportFragmentManager.fragments.forEach {
            transaction.hide(it)
        }
        if (current != null) {
            transaction.show(current)
        } else {
            transaction.add(binding.fragmentContainer.id, fragment, tag)
        }
        transaction.commit()
    }
} 