package com.example.colorphone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import com.example.colorphone.databinding.FragmentHomeScreenBinding
import com.example.colorphone.util.custom.CustomDropDownAdapter


class HomeScreenFragment : Fragment() {

    private var _binding: FragmentHomeScreenBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val autoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)

        val randomItem = resources.getStringArray(R.array.random).toList()
        val arrayAdapter = CustomDropDownAdapter(requireContext(), R.layout.dropdown_random_item, randomItem, autoCompleteTextView)

        autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}