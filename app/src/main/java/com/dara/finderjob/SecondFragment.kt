package com.dara.finderjob

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.dara.finderjob.databinding.FragmentSecondBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using View Binding
        val binding: FragmentSecondBinding = FragmentSecondBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set up the next button navigation
        binding.nextButton.setOnClickListener {
            it.findNavController().navigate(com.dara.finderjob.R.id.action_secondFragment_to_thirdFragment)
        }

        // Create a SpannableString for the heading text
        val heading = "The Faster Way to Find Your Dream Job"
        val spannableString = SpannableString(heading)

        val foregroundColorSpanRed = ForegroundColorSpan(Color.BLUE)
        // Set the spannable string to the heading TextView
        binding.heading.text = spannableString
 
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}