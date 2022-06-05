package dev.robert.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.robert.rickandmorty.databinding.EpisodesListItemBinding
import dev.robert.rickandmorty.model.CharactersResult

class EpisodesAdapter (private val episodes : List<String>) :
    ListAdapter<CharactersResult, EpisodesAdapter.EpisodesViewHolder>(EpisodesDiffCallback) {
    inner class EpisodesViewHolder(val binding: EpisodesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CharactersResult) {
            binding.episodeAppeared.text = item.episode.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        return EpisodesViewHolder(
            EpisodesListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

