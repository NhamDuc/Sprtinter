package com.example.colorphone

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.colorphone.databinding.FragmentHomeScreenBinding
import com.example.colorphone.model.ShimejiActions
import com.example.colorphone.model.ShimejiDetails
import com.example.colorphone.model.ShimejiItem
import com.example.colorphone.model.ShimejiState
import com.example.colorphone.util.custom.CustomDropDownAdapter
import com.example.colorphone.util.custom.ShimejiRecyclerViewAdapter


class HomeScreenFragment : Fragment() {

    private var _binding: FragmentHomeScreenBinding ?= null
    private val binding get() = _binding!!

    private lateinit var shimejiGridAdapter: ShimejiRecyclerViewAdapter
    private val shimejiList = mutableListOf<ShimejiItem>()

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

        setupRecyclerView()
        // TODO: Fake data
        initializeGrid()

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreenFragment_to_settingsScreenFragment)
        }
        binding.btnAdjust.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreenFragment_to_adjustScreenFragment)
        }
        binding.btnViewAllShimeji.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreenFragment_to_allShimejisFragment)
        }
    }

    override fun onResume() {
        super.onResume()

        val autoCompleteTextView = binding.autoCompleteTextView
        val randomItem = resources.getStringArray(R.array.random)
        val arrayAdapter = CustomDropDownAdapter(requireContext(), R.layout.dropdown_random_item, randomItem, autoCompleteTextView)
        autoCompleteTextView.setAdapter(arrayAdapter)
    }

    private fun setupRecyclerView() {
        shimejiGridAdapter = ShimejiRecyclerViewAdapter(
            items = shimejiList,
            showBtnOptions = false,
            onDetailsClick = {
                val action =
                    HomeScreenFragmentDirections.actionHomeScreenFragmentToDetailsShimejiFragment(it)
                findNavController().navigate(action)
            },
            onAllShimejisClick = {
                findNavController().navigate(R.id.action_homeScreenFragment_to_allShimejisFragment)
            },
            onDeleteClick = { shimejiItem ->
                Toast.makeText(
                    requireContext(),
                    "A Shimeji has made an ultimate sacrifice",
                    Toast.LENGTH_LONG
                ).show()
                val pos = shimejiList.indexOf(shimejiItem)
                val allShimejiActions = ShimejiActions.entries
                if (pos != -1) {
                    shimejiList.removeAt(pos)
                    shimejiList.add(
                        ShimejiItem(
                            null,
                            ShimejiState.IsDownloaded(false, ShimejiDetails(allShimejiActions)),
                            "",
                            null
                        )
                    )
                    shimejiGridAdapter.notifyItemChanged(pos)
                }
            },
            onDownloadClick = {

            },
            onOptionsSelected = { item, boolean ->

            },
        )

        binding.gridRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = shimejiGridAdapter

            isNestedScrollingEnabled = false
            overScrollMode = View.OVER_SCROLL_NEVER
        }
    }

    private fun initializeGrid() {
        shimejiList.clear()
        val allShimejiActions = ShimejiActions.entries

        for (i in 0..5) {
            shimejiList.add(ShimejiItem(
                null,
                ShimejiState.IsDownloaded(false, ShimejiDetails(allShimejiActions)),
                "",
                null
            ))
        }
        shimejiList[1] = ShimejiItem(
            1,
            ShimejiState.IsDownloaded(true,  ShimejiDetails(allShimejiActions)),
            "Tanjiro",
            R.drawable.tanjiro
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}