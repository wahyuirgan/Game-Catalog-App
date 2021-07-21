package com.hokagelab.gamecatalogapp.core.ui

import com.hokagelab.gamecatalogapp.core.domain.model.Game
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hokagelab.gamecatalogapp.core.R
import com.hokagelab.gamecatalogapp.core.databinding.ItemRowGameBinding

class GameAdapter : RecyclerView.Adapter<GameAdapter.ListViewHolder>() {

    private var listData = ArrayList<Game>()
    var onItemClick: ((Game) -> Unit)? = null

    fun setData(newListData: List<Game>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowGameBinding.bind(itemView)
        fun bind(data: Game) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.backgroundImage)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                            .error(R.drawable.ic_image_error)
                    )
                    .into(ivPoster)

                tvTitle.text = data.name
                rbVote.rating = data.rating.toFloat()

                when(data.ratingTop){
                    1 -> Glide.with(itemView.context)
                        .load(R.drawable.ic_skip)
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                                .error(R.drawable.ic_image_error)
                        )
                        .into(ivCategoryRating)
                    3 -> Glide.with(itemView.context)
                        .load(R.drawable.ic_meh)
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                                .error(R.drawable.ic_image_error)
                        )
                        .into(ivCategoryRating)
                    4 -> Glide.with(itemView.context)
                        .load(R.drawable.ic_recommended)
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                                .error(R.drawable.ic_image_error)
                        )
                        .into(ivCategoryRating)
                    5 -> Glide.with(itemView.context)
                        .load(R.drawable.ic_exceptional)
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                                .error(R.drawable.ic_image_error)
                        )
                        .into(ivCategoryRating)
                    else -> Glide.with(itemView.context)
                        .load(R.drawable.ic_category_rating)
                        .into(ivCategoryRating)
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row_game, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size
}