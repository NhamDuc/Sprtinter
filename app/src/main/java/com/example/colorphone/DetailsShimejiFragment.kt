package com.example.colorphone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.colorphone.databinding.FragmentDetailsShimejiBinding
import com.example.colorphone.databinding.FragmentHomeScreenBinding
import com.example.colorphone.model.ShimejiActions
import com.example.colorphone.model.ShimejiDetails
import com.example.colorphone.model.ShimejiItem
import com.example.colorphone.model.ShimejiState
import com.example.colorphone.util.custom.DetailsRecyclerViewAdapter
import kotlin.random.Random

class DetailsShimejiFragment : Fragment() {
    private var _binding: FragmentDetailsShimejiBinding ?= null
    private val binding get() = _binding!!

    private val args: DetailsShimejiFragmentArgs by navArgs()
    private lateinit var detailsGridAdapter: DetailsRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsShimejiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedShimejiItem: ShimejiItem = args.shimejiItem

        binding.tvName.text = selectedShimejiItem.name
        selectedShimejiItem.iconResId?.let {
            binding.imgvShimeji.setImageResource(it)
        } ?: binding.imgvShimeji.setImageResource(R.drawable.loading)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        if (selectedShimejiItem.state is ShimejiState.IsDownloaded) {
            val downloadedState = selectedShimejiItem.state
            val actionsList = downloadedState.details.shimejiActions

            detailsGridAdapter = DetailsRecyclerViewAdapter(
                items = actionsList,
                onClick = {

                },
            )
        }

        binding.detailsRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = detailsGridAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}