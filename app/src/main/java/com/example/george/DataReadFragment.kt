package com.example.george

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.george.databinding.FragmentDataDetailBinding
import com.example.george.databinding.FragmentDataReadBinding


/**
 * A simple [Fragment] subclass.
 * Use the [DataReadFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DataReadFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentDataReadBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataReadBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonNavigateDetail.setOnClickListener {
            Log.d("TEST", "NAPPI TOIMII!")

            val action = DataReadFragmentDirections.actionDataReadFragmentToDataDetailFragment(3157)
            it.findNavController().navigate(action)
        }

        // the binding -object allows you to access views in the layout, textviews etc.

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}