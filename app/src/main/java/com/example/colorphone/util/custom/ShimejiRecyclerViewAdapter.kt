package com.example.colorphone.util.custom

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.colorphone.R
import com.example.colorphone.databinding.ShimejiGridItemBinding
import com.example.colorphone.model.ShimejiItem
import com.example.colorphone.model.ShimejiState
import kotlin.random.Random

class ShimejiRecyclerViewAdapter(
    private val items: MutableList<ShimejiItem>,
    private val showBtnOptions: Boolean,
    private val onDetailsClick: (ShimejiItem) -> Unit,
    private val onAllShimejisClick: () -> Unit,
    private val onDeleteClick: (ShimejiItem) -> Unit,
    private val onDownloadClick: (ShimejiItem) -> Unit,
) : RecyclerView.Adapter<ShimejiRecyclerViewAdapter.ShimejiRecyclerViewHolder>() {

    // Inner class to hold references to the views for each item
    // View Binding generates ItemGridBinding based on item_grid.xml
    inner class ShimejiRecyclerViewHolder(
        private val binding: ShimejiGridItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ShimejiItem,
            showBtnOptions: Boolean,
            onToDetailsClick: (ShimejiItem) -> Unit,
            onToAllShimejisClick: () -> Unit,
            onDeleteClick: (ShimejiItem) -> Unit,
            onDownloadClick: (ShimejiItem) -> Unit
        ) {
            when (item.state) {
                // TODO: Item Details is downloaded
                is ShimejiState.IsDownloaded -> {
                    val downloadState = item.state

                    binding.selectedStateImage.visibility = View.VISIBLE
                    binding.selectedStateText.visibility = View.VISIBLE
                    item.iconResId?.let {
                        binding.selectedStateImage.setImageResource(it)
                    } ?: binding.selectedStateImage.setImageResource(R.drawable.loading)

                    binding.root.setOnClickListener {
                        onDetailsClick(item)
                    }

                    if (downloadState.isSelected) {
                        binding.unselectedStateContainer.visibility = View.GONE
                        if (showBtnOptions) { // ALL SCREEN
                            binding.btnOptions.visibility = View.VISIBLE
                            binding.selectedStateDeleteBtn.visibility = View.GONE
                            binding.dotTopRight.visibility = View.VISIBLE
                            binding.btnOptions.apply {
                                this.setText(R.string.remove)
                                this.setStrokeColorResource(R.color.black)
                                this.setBackgroundColor(Color.TRANSPARENT)
                                this.setStrokeWidthResource(R.dimen.button_stroke_width)
                            }
                        } else { // HOME SCREEN
                            binding.btnOptions.visibility = View.GONE
                            binding.selectedStateDeleteBtn.visibility = View.VISIBLE
                            binding.dotTopRight.visibility = View.GONE

                            binding.selectedStateDeleteBtn.setOnClickListener {
                                onDeleteClick(item)
                            }
                        }

                        binding.selectedStateText.text = item.name
                        binding.btnOptions.setOnClickListener {
                            downloadState.isSelected = false
                        }
                    } else {
                        if (showBtnOptions) { // All Screen
                            binding.unselectedStateContainer.visibility = View.GONE
                            binding.selectedStateDeleteBtn.visibility = View.GONE
                            binding.btnOptions.visibility = View.VISIBLE
                            binding.dotTopRight.visibility = View.VISIBLE
                            binding.btnOptions.apply {
                                this.setText(R.string.select)
                                this.setBackgroundColor(getResources().getColor(R. color. white))
                            }
                        } else { // HOME SCREEN
                            binding.selectedStateImage.visibility = View.GONE
                            binding.selectedStateText.visibility = View.GONE
                            binding.unselectedStateContainer.visibility = View.VISIBLE
                            binding.selectedStateDeleteBtn.visibility = View.GONE
                            binding.btnOptions.visibility = View.GONE
                            binding.dotTopRight.visibility = View.VISIBLE
                        }
                        binding.selectedStateText.text = item.name
                        binding.unselectedStateContainer.setOnClickListener {
                            onToAllShimejisClick()
                        }
                        binding.btnOptions.setOnClickListener {
                            downloadState.isSelected = true
                        }
                    }
                }

                // TODO: Item Details is not downloaded
                is ShimejiState.IsNotDownloaded -> {
                    binding.selectedStateImage.visibility = View.VISIBLE
                    binding.unselectedStateContainer.visibility = View.GONE
                    binding.selectedStateDeleteBtn.visibility = View.GONE
                    binding.btnOptions.visibility = View.VISIBLE
                    binding.dotTopRight.visibility = View.VISIBLE
                    binding.btnOptions.apply {
                        this.setText(R.string.download)
                        this.setBackgroundColor(getResources().getColor(R. color. csk_500))
                        this.setStrokeWidthResource(R.dimen.button_stroke_width_none)
                    }
                    item.iconResId?.let { resId ->
                        binding.selectedStateImage.setImageResource(resId)
                    } ?: run {
                        binding.selectedStateImage.setImageResource(R.drawable.loading)
                    }
                    binding.selectedStateText.visibility = View.VISIBLE
                    binding.selectedStateText.text = item.name
                    binding.btnOptions.setOnClickListener {
                        onDownloadClick(item)
                    }
                }
            }
        }
    }

    // Called when RecyclerView needs a new ViewHolder to represent an item
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShimejiRecyclerViewHolder {
        // Inflate the item_grid.xml layout using View Binding
        val binding = ShimejiGridItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ShimejiRecyclerViewHolder(binding)
    }


    // Called by RecyclerView to display the data at the specified position
    override fun onBindViewHolder(
        holder: ShimejiRecyclerViewHolder,
        position: Int
    ) {
        val item = items[position]
        holder.bind(
            item,
            showBtnOptions,
            onDetailsClick,
            onAllShimejisClick,
            onDeleteClick,
            onDownloadClick
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItem(item: ShimejiItem) {

    }

    fun removeItem(item: ShimejiItem) {
        val index = items.indexOfFirst { it.id == item.id }
        if (index != -1) {
            items[index].copy(
                name = "",
                iconResId = null
            )
            notifyItemRemoved(index)
        }
    }


}