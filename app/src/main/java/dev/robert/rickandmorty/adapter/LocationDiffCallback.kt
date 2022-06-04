package dev.robert.rickandmorty.adapter

import androidx.recyclerview.widget.DiffUtil
import dev.robert.rickandmorty.model.location.LocationsResult

object LocationDiffCallback : DiffUtil.ItemCallback<LocationsResult>() {
    override fun areItemsTheSame(oldItem: LocationsResult, newItem: LocationsResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LocationsResult, newItem: LocationsResult): Boolean {
        return oldItem == newItem
    }
}
