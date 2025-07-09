package com.example.colorphone.util.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.colorphone.R
import com.example.colorphone.databinding.DetailsGridItemBinding
import com.example.colorphone.model.ShimejiActions
import com.example.colorphone.model.ShimejiDetails

class DetailsRecyclerViewAdapter(
    private val items: List<ShimejiActions>,
    private val onShimejiActionClick: (ShimejiActions) -> Unit
) : RecyclerView.Adapter<DetailsRecyclerViewAdapter.DetailsRecyclerViewHolder>() {

    inner class DetailsRecyclerViewHolder(
        private val binding: DetailsGridItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            action: ShimejiActions,
            onClick: (ShimejiActions) -> Unit
        ) {
            binding.tvAction.text = action.name
            binding.imgbtnAction.setImageResource(getActionDrawableRes(action))
            binding.imgbtnAction.setOnClickListener {
                onClick(action)
            }
        }

        private fun getActionDrawableRes(action: ShimejiActions): Int {
            return when (action) {
                ShimejiActions.JUMP -> R.drawable.tanjiro
                ShimejiActions.DRAG -> R.drawable.tanjiro
                ShimejiActions.SIT -> R.drawable.tanjiro
                ShimejiActions.FALLING -> R.drawable.tanjiro
                ShimejiActions.CLIMB -> R.drawable.tanjiro
                ShimejiActions.HANG -> R.drawable.tanjiro
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailsRecyclerViewHolder {
        // Inflate the item_grid.xml layout using View Binding
        val binding = DetailsGridItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailsRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: DetailsRecyclerViewHolder,
        position: Int
    ) {
        val action = items[position]
        holder.bind(
            action,
            onShimejiActionClick
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }
}