package com.example.colorphone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        detailsGridAdapter = ShimejiItem(
            1,
            ShimejiState.IsDownloaded(true, ShimejiDetails(ShimejiActions.entries)),
            "tanjiro",
            R.drawable.tanjiro
        ).run {
            val downloadedState = this.state as ShimejiState.IsDownloaded
            val actionsList = downloadedState.details.shimejiActions
            return@run DetailsRecyclerViewAdapter(
                items = actionsList,
                onShimejiActionClick = {}
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