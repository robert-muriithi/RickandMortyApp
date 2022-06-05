package dev.robert.rickandmorty.adapter

import androidx.recyclerview.widget.DiffUtil
import dev.robert.rickandmorty.model.CharactersResult

object EpisodesDiffCallback
    : DiffUtil.ItemCallback<CharactersResult>() {
    override fun areItemsTheSame(
        oldItem: CharactersResult,
        newItem: CharactersResult
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CharactersResult,
        newItem: CharactersResult
    ): Boolean {
        return oldItem == newItem
    }
}
