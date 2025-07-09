package com.example.colorphone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.colorphone.databinding.FragmentHomeScreenBinding
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

        val autoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)

        val randomItem = resources.getStringArray(R.array.random).toList()
        val arrayAdapter = CustomDropDownAdapter(requireContext(), R.layout.dropdown_random_item, randomItem, autoCompleteTextView)
        autoCompleteTextView.setAdapter(arrayAdapter)

        setupRecyclerView()
        // TODO: Fake data
        initializeGrid()
    }

    private fun setupRecyclerView() {
        shimejiGridAdapter = ShimejiRecyclerViewAdapter(
            items = shimejiList,
            showBtnOptions = false,
            onClickToDetails = { },
            onClickToAllShimejis = { },
            onDeleteClick = { shimejiItem ->
                Toast.makeText(
                    requireContext(),
                    "A Shimeji has made an ultimate sacrifice",
                    Toast.LENGTH_LONG
                ).show()
                shimejiGridAdapter.removeItem(shimejiItem)
            },
            onDownloadClick = { },
        )

        binding.gridRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = shimejiGridAdapter

            isNestedScrollingEnabled = false
            overScrollMode = View.OVER_SCROLL_NEVER
        }
    }

    private fun initializeGrid() {
        for (i in 0..5) {
            shimejiList.add(ShimejiItem(i, ShimejiState.IsDownloaded(false), "", null))
        }
        shimejiList[1] = ShimejiItem(1, ShimejiState.IsDownloaded(true), "Tanjiro", R.drawable.tanjiro)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}