package com.example.carparking.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carparking.R
import com.example.carparking.databinding.CardItemParkingBinding
import com.example.carparking.model.FloorAndClusterModel
import com.example.carparking.ui.parking.ParkingLayoutActivity


class FloorAndClusterAdapter :
    ListAdapter<FloorAndClusterModel, FloorAndClusterAdapter.FloorAndClusterViewHolder>(
        mDiffCallback
    ) {

    // Inflate the layout for items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloorAndClusterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardItemParkingBinding.inflate(layoutInflater, parent, false)
        return FloorAndClusterViewHolder(binding)
    }

    // Access the item on List
    override fun onBindViewHolder(holder: FloorAndClusterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // Binding data item to view layout
    class FloorAndClusterViewHolder(val binding: CardItemParkingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FloorAndClusterModel) {
            binding.apply {
                tvNameItemParking.text = itemView.resources.getString(
                    R.string.carkir_detail_floor,
                    item.floor,
                    item.rangeClusters
                )
                tvEmptySpaceItemParking.text = itemView.resources.getString(
                    R.string.carkir_detail_space_floor,
                    item.parkingSpace
                )

                // Listener to occupancy and floor parking place with args Parcelable
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, ParkingLayoutActivity::class.java)
                    intent.putExtra(ParkingLayoutActivity.EXTRA_ITEM, item)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    /**
     * Anonymous class for compare the oldList and newList in ListAdapter
     * */
    companion object {
        private val mDiffCallback = object : DiffUtil.ItemCallback<FloorAndClusterModel>() {
            override fun areItemsTheSame(
                oldItem: FloorAndClusterModel,
                newItem: FloorAndClusterModel
            ): Boolean {
                return oldItem.floor == newItem.floor
            }

            override fun areContentsTheSame(
                oldItem: FloorAndClusterModel,
                newItem: FloorAndClusterModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}