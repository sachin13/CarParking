package com.example.carparking.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carparking.R
import com.example.carparking.data.local.entity.PlacesEntity
import com.example.carparking.databinding.CardItemHistoriesBinding
import com.example.carparking.ui.history.HistoryFragmentDirections
import com.example.carparking.utils.decodeBase64ToBitmap
import com.example.carparking.utils.loadImage


class HistoryAdapter(val onItemClicked: (PlacesEntity) -> Unit) :
    ListAdapter<PlacesEntity, HistoryAdapter.HistoryViewHolder>(mDiffCallback) {

    // Inflate the layout for items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardItemHistoriesBinding.inflate(layoutInflater, parent, false)
        return HistoryViewHolder(binding)
    }

    // Access the item on List
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val place = getItem(position)
        holder.bind(place)
        holder.binding.ivIconItemRecent.setOnClickListener { onItemClicked(place) }
    }

    // Binding data item to view layout
    class HistoryViewHolder(val binding: CardItemHistoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(place: PlacesEntity) {
            binding.apply {
                place.image?.let {
                    val bitmap = it.decodeBase64ToBitmap()
                    ivThumbnailItemRecent.loadImage(itemView.context, bitmap)
                }
                tvNameItemRecent.text = place.name
                tvTimeItemLocation.text = itemView.resources.getString(
                    R.string.carkir_card_see_at,
                    place.insertAt
                )
                tvSpaceItemRecent.text = itemView.resources.getString(
                    R.string.carkir_card_slot_parking,
                    place.totalSpace
                )
                ivIconItemRecent.isVisible = true
            }

            // Listener to detail parking place with args name place
            itemView.setOnClickListener {
                val action =
                    HistoryFragmentDirections.actionNavigationHistoryToDetailPlaceActivity(place.name)
                it.findNavController().navigate(action)
            }
        }

    }

    /**
     * Anonymous class for compare the oldList and newList in ListAdapter
     * */
    companion object {
        private val mDiffCallback = object : DiffUtil.ItemCallback<PlacesEntity>() {
            override fun areItemsTheSame(
                oldItem: PlacesEntity,
                newItem: PlacesEntity
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: PlacesEntity,
                newItem: PlacesEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}