package com.example.colorphone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.colorphone.databinding.FragmentAllShimejisBinding
import com.example.colorphone.model.ShimejiActions
import com.example.colorphone.model.ShimejiDetails
import com.example.colorphone.model.ShimejiItem
import com.example.colorphone.model.ShimejiState
import com.example.colorphone.util.custom.ShimejiRecyclerViewAdapter

class AllShimejisFragment : Fragment() {


    private var _binding: FragmentAllShimejisBinding ?= null
    private val binding get() = _binding!!

    private lateinit var shimejiGridAdapter: ShimejiRecyclerViewAdapter
    private val shimejiList = mutableListOf<ShimejiItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllShimejisBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecylerView()
        // TODO: Fake data
        initializeGrid()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecylerView() {
        shimejiGridAdapter = ShimejiRecyclerViewAdapter(
            items = shimejiList,
            showBtnOptions = true,
            onDetailsClick = {
                val action =
                    AllShimejisFragmentDirections.actionAllShimejisFragmentToDetailsShimejiFragment(
                        it
                    )
                findNavController().navigate(action)
            },
            onAllShimejisClick = {
                // Nothing
            },
            onDeleteClick = { },
            onDownloadClick = {
                // TODO:
            },
            onOptionsSelected = { item, boolean ->

            },
        )

        binding.recyclerView2.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = shimejiGridAdapter
        }
    }

    private fun initializeGrid() {
        shimejiList.clear()
        val allShimejiActions = ShimejiActions.entries

        for (i in 0..15) {
            shimejiList.add(ShimejiItem(
                i, ShimejiState.IsNotDownloaded, "Vegeta", R.drawable.vegeta
            ))
        }
        for (i in 0..15) {
            shimejiList.add(ShimejiItem(
                i, ShimejiState.IsNotDownloaded, "Doggo", null
            ))
        }
        shimejiList[1] = ShimejiItem(
            null,
            ShimejiState.IsDownloaded(true,details = ShimejiDetails(allShimejiActions)),
            "Tanjiro",
            R.drawable.tanjiro
        )
        shimejiList[2] = ShimejiItem(
            null,
            ShimejiState.IsNotDownloaded, "Doggo", R.drawable.doggo
        )
        shimejiList[3] = ShimejiItem(
            3,
            ShimejiState.IsDownloaded(false ,ShimejiDetails(allShimejiActions)),
            "Vegeta",
            R.drawable.vegeta
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}