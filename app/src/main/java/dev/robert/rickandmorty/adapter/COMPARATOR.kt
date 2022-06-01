package dev.robert.rickandmorty.adapter

import androidx.recyclerview.widget.DiffUtil
import dev.robert.rickandmorty.model.CharactersResult

object COMPARATOR : DiffUtil.ItemCallback<CharactersResult>() {
    override fun areItemsTheSame(
        oldItem: CharactersResult,
        newItem: CharactersResult
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: CharactersResult,
        newItem: CharactersResult
    ): Boolean {
        return oldItem == newItem
    }
}
