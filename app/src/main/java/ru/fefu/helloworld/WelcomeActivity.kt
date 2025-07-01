package ru.fefu.helloworld

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.fefu.helloworld.databinding.ActivityWelcomeBinding
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        val loginText = "Уже есть аккаунт?"
        val spannable = SpannableString(loginText)
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
            }
        }, 0, loginText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.loginText.text = spannable
        binding.loginText.movementMethod = LinkMovementMethod.getInstance()
    }
} 