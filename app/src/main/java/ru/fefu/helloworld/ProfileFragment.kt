package ru.fefu.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.TextView

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val textView = TextView(requireContext())
        textView.text = "Профиль"
        textView.textSize = 24f
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        return textView
    }
} 