package com.dara.finderjob

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dara.finderjob.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var handler: Handler
    private val navigateRunnable = Runnable {
        // Load animations
        val fadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)

        // Apply fade out animation to the root view
        binding.root.startAnimation(fadeOut)

        // Navigate after animation completes
        fadeOut.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
            override fun onAnimationStart(animation: android.view.animation.Animation?) {}

            override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                // Create navigation action with fade in animation
                findNavController().navigate(
                    R.id.action_firstFragment_to_feature1Fragment,
                    null,
                    androidx.navigation.NavOptions.Builder()
                        .setEnterAnim(R.anim.fade_in)
                        .build()
                )
            }

            override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler = Handler(Looper.getMainLooper())

        // Set click listener (optional - allows users to skip the wait)
        binding.btnLogo.setOnClickListener {
            handler.removeCallbacks(navigateRunnable)
            navigateRunnable.run() // Use the same animation sequence
        }

        // Schedule automatic navigation after 3 seconds
        handler.postDelayed(navigateRunnable, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(navigateRunnable)
        _binding = null
    }
}