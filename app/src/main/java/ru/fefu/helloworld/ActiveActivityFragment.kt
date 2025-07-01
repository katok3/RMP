package ru.fefu.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.fefu.helloworld.databinding.FragmentActiveActivityBinding

class ActiveActivityFragment : Fragment() {
    private var _binding: FragmentActiveActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActiveActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(), "ActiveActivityFragment onViewCreated", Toast.LENGTH_SHORT).show()
        Log.d("ActiveActivityFragment", "onViewCreated")
        binding.pauseButton.setOnClickListener {
            Snackbar.make(view, "Пауза (заглушка)", Snackbar.LENGTH_SHORT).show()
        }
        binding.stopButton.setOnClickListener {
            Snackbar.make(view, "Стоп (заглушка)", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 