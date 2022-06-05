package dev.robert.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.robert.rickandmorty.databinding.LocationListItemBinding
import dev.robert.rickandmorty.model.location.LocationsResult

class LocationPagingAdapter
    : PagingDataAdapter<LocationsResult, LocationPagingAdapter.LocationsViewHolder>(
    LocationDiffCallback
) {
    inner class LocationsViewHolder(
        private val binding: LocationListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: LocationsResult?) {
            binding.locationName.text = result?.name
            binding.locationType.text = result?.type
            binding.locationDimension.text = result?.dimension
            binding.locationCreationDate.text = result?.created
            binding.locationId.text = result?.id.toString()
        }
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        val locations = getItem(position)
        holder.bind(locations)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        return LocationsViewHolder(
            LocationListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}