package ru.fefu.helloworld

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.fefu.helloworld.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.continueButton.setOnClickListener {
            Toast.makeText(this, "Вход выполнен", Toast.LENGTH_SHORT).show()
        }
    }
} 