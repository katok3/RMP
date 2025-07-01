package ru.fefu.helloworld

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.fefu.helloworld.databinding.ActivityRegistrationBinding
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.continueButton.setOnClickListener {
            Toast.makeText(this, "Регистрация завершена", Toast.LENGTH_SHORT).show()
        }

        val loginText = "Уже есть аккаунт?"
        val spannable = SpannableString(loginText)
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
            }
        }, 0, loginText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.loginText.text = spannable
        binding.loginText.movementMethod = LinkMovementMethod.getInstance()
    }
} 