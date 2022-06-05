package dev.robert.rickandmorty.adapter

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dev.robert.rickandmorty.R
import dev.robert.rickandmorty.databinding.CharacterListItemBinding
import dev.robert.rickandmorty.model.CharactersResult

class CharactersPagingAdapter (private val onclickListener: OnclickListener)  :
    PagingDataAdapter<CharactersResult, CharactersPagingAdapter.CharactersViewHolder>(CharactersDiffCallback) {
    var vibrantColor = 0
    var picture = ""
    inner class CharactersViewHolder(
        private val binding: CharacterListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharactersResult?) {
            binding.name.text = character?.name
            binding.tvStatus.text = character?.status
            Glide.with(binding.root.context)
                .load(character?.image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.isVisible = false
                        val drawable: BitmapDrawable = resource as BitmapDrawable
                        val bitmap = drawable.bitmap
                        Palette.Builder(bitmap).generate {
                            it?.let { palette ->
                                vibrantColor = palette.getDominantColor(
                                    ContextCompat.getColor(
                                        binding.root.context,
                                        R.color.white
                                    )
                                )
                                binding.image.setBackgroundColor(vibrantColor)
                            }

                        }
                        return false
                    }
                })
                .into(binding.image)
        }

    }

    override fun onBindViewHolder(
        holder: CharactersPagingAdapter.CharactersViewHolder,
        position: Int
    ) {
        val character = getItem(position)
        holder.itemView.setOnClickListener {
            onclickListener.onClick(character!!, picture, vibrantColor )
        }
        holder.bind(character)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharactersPagingAdapter.CharactersViewHolder {
        return CharactersViewHolder(
            CharacterListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class OnclickListener(val clickListener: (character: CharactersResult, picture : String, color : Int ) -> Unit) {
        fun onClick(character: CharactersResult, picture : String, color : Int) = clickListener(character, picture, color)
    }

}