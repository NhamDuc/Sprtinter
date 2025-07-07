package com.example.colorphone.util.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.colorphone.databinding.ShimejiGridItemBinding
import com.example.colorphone.model.ShimejiItem

class ShimejiRecyclerViewAdapter(
    private val shimejiItems: MutableList<ShimejiItem>,
    private val onItemClick: (ShimejiItem) -> Unit,
    private val onDeleteClick: (ShimejiItem) -> Unit
) : RecyclerView.Adapter<ShimejiRecyclerViewAdapter.ShimejiRecyclerViewHolder>() {

    inner class ShimejiRecyclerViewHolder(
        private val binding: ShimejiGridItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ShimejiItem,
            onItemClick: (ShimejiItem) -> Unit,
            onDeleteClick: (ShimejiItem) -> Unit
        ) {
            if (item.isSelected) {
                binding.dotTopRight.visibility = View.INVISIBLE
                binding.unselectedStateContainer.visibility = View.INVISIBLE
                binding.selectedStateText.visibility = View.VISIBLE
                binding.selectedStateDeleteBtn.visibility = View.VISIBLE

                item.iconResId?.let { resID ->
                    binding.selectedStateImage.setImageResource(resID)
                    binding.selectedStateImage.visibility = View.VISIBLE
                } ?: run {
                    binding.selectedStateImage.visibility = View.INVISIBLE
                }

                binding.apply {
                    selectedStateText.text = item.name
                }

                binding.selectedStateDeleteBtn.setOnClickListener {
                    onDeleteClick(item)
                }

            } else {
                binding.unselectedStateContainer.visibility = View.VISIBLE
                binding.dotTopRight.visibility = View.VISIBLE
                binding.selectedStateImage.visibility = View.INVISIBLE
                binding.selectedStateText.visibility = View.INVISIBLE
                binding.selectedStateDeleteBtn.visibility = View.INVISIBLE

                binding.selectedStateImage.setOnClickListener {
                    onDeleteClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShimejiRecyclerViewHolder {
        val binding = ShimejiGridItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ShimejiRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ShimejiRecyclerViewHolder,
        position: Int
    ) {
        val item = shimejiItems[position]
        holder.bind(item, onItemClick, onDeleteClick)
    }

    override fun getItemCount(): Int {
        return shimejiItems.size
    }

    fun updateItem(item: ShimejiItem) {

    }

    fun removeItem(item: ShimejiItem) {
        val index = shimejiItems.indexOfFirst { it.id == item.id }
        if (index != -1) {
            shimejiItems[index].copy(
                isSelected = false,
                name = "",
                iconResId = null
            )
            notifyItemRemoved(index)
        }
    }


}