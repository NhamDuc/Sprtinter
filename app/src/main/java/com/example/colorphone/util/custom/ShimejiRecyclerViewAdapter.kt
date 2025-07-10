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
    private val onOptionsSelected: (ShimejiItem, Boolean) -> Unit
) : RecyclerView.Adapter<ShimejiRecyclerViewAdapter.ShimejiRecyclerViewHolder>() {

    // Inner class to hold references to the views for each item
    // View Binding generates ItemGridBinding based on item_grid.xml
    inner class ShimejiRecyclerViewHolder(
        private val binding: ShimejiGridItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ShimejiItem,
        ) {
            val cardHeight: Int
            when (item.state) {
                // TODO: Item Details is downloaded
                is ShimejiState.IsDownloaded -> {
                    val downloadState = item.state

                    binding.selectedStateImage.visibility = View.VISIBLE
                    binding.selectedStateText.visibility = View.VISIBLE
                    item.iconResId?.let {
                        binding.selectedStateImage.setImageResource(it)
                    } ?: binding.selectedStateImage.setImageResource(R.drawable.loading)


                    if (downloadState.isSelected) {
                        binding.unselectedStateContainer.visibility = View.GONE
                        if (showBtnOptions) { // ALL SCREEN
                            cardHeight = binding.root.context.resources.getDimensionPixelSize(R.dimen.higher_card)

                            binding.btnOptions.visibility = View.VISIBLE
                            binding.selectedStateDeleteBtn.visibility = View.GONE
                            binding.dotTopRight.visibility = View.VISIBLE
                            binding.btnOptions.apply {
                                this.setText(R.string.remove)
                                this.setStrokeColorResource(R.color.black)
                                this.setBackgroundColor(Color.TRANSPARENT)
                                this.setStrokeWidthResource(R.dimen.button_stroke_width)
                            }
                            val layoutParams = binding.shimejiItemCardView.layoutParams
                            layoutParams.height = R.dimen.button_stroke_width
                        } else { // HOME SCREEN
                            cardHeight = binding.root.context.resources.getDimensionPixelSize(R.dimen.standard_card)

                            binding.btnOptions.visibility = View.GONE
                            binding.selectedStateDeleteBtn.visibility = View.VISIBLE
                            binding.dotTopRight.visibility = View.GONE

                            binding.selectedStateDeleteBtn.setOnClickListener {
                                onDeleteClick(item)
                            }
                        }

                        binding.selectedStateImage.setOnClickListener {
                            onDetailsClick(item)
                        }

                        binding.selectedStateText.text = item.name
                        binding.btnOptions.setOnClickListener {
                            onOptionsSelected(item, false)
                        }
                    } else {
                        if (showBtnOptions) { // All Screen
                            cardHeight = binding.root.context.resources.getDimensionPixelSize(R.dimen.higher_card)

                            binding.unselectedStateContainer.visibility = View.GONE
                            binding.selectedStateDeleteBtn.visibility = View.GONE
                            binding.btnOptions.visibility = View.VISIBLE
                            binding.dotTopRight.visibility = View.VISIBLE
                            binding.btnOptions.apply {
                                this.setText(R.string.select)
                                this.setBackgroundColor(getResources().getColor(R. color. white))
                            }
                        } else { // HOME SCREEN
                            cardHeight = binding.root.context.resources.getDimensionPixelSize(R.dimen.standard_card)

                            binding.selectedStateImage.visibility = View.GONE
                            binding.selectedStateText.visibility = View.GONE
                            binding.unselectedStateContainer.visibility = View.VISIBLE
                            binding.selectedStateDeleteBtn.visibility = View.GONE
                            binding.btnOptions.visibility = View.GONE
                            binding.dotTopRight.visibility = View.VISIBLE
                        }
                        binding.selectedStateText.text = item.name
                        binding.unselectedStateContainer.setOnClickListener {
                            onAllShimejisClick()
                        }
                        binding.selectedStateImage.setOnClickListener {
                            onDetailsClick(item)
                        }
                        binding.btnOptions.setOnClickListener {
                            onOptionsSelected(item, true)
                        }
                    }
                }

                // TODO: Item Details is not downloaded
                is ShimejiState.IsNotDownloaded -> {
                    cardHeight = binding.root.context.resources.getDimensionPixelSize(R.dimen.higher_card)

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

            val layoutParams = binding.shimejiItemCardView.layoutParams
            layoutParams.height = cardHeight
            binding.shimejiItemCardView.layoutParams = layoutParams
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
            item
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }
}