package com.hokagelab.gamecatalogapp.ui.detail

import android.graphics.Color
import com.hokagelab.gamecatalogapp.core.data.Resource
import com.hokagelab.gamecatalogapp.core.domain.model.Game
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.hokagelab.gamecatalogapp.R
import com.hokagelab.gamecatalogapp.databinding.ActivityDetailGameBinding
import com.hokagelab.gamecatalogapp.databinding.ContentDetailGameBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailGameActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private val detailGameViewModel: DetailGameViewModel by viewModel()

    private var _binding: ContentDetailGameBinding? = null
    private val binding get() = _binding
    private var _activityDetailGameBinding: ActivityDetailGameBinding? = null
    private val activityDetailGameBinding get() = _activityDetailGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityDetailGameBinding = ActivityDetailGameBinding.inflate(layoutInflater)
        _binding = activityDetailGameBinding?.detailContent
        setContentView(activityDetailGameBinding?.root)

        setSupportActionBar(activityDetailGameBinding?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.mainProgressBar?.visibility = View.VISIBLE

        activityDetailGameBinding?.toolbar?.setNavigationOnClickListener { onBackPressed() }

        val movieDetails = intent.getParcelableExtra<Game>(EXTRA_DATA)
        if (movieDetails != null) {
            val id = movieDetails.id.toString()
            detailGameViewModel.setGameDetail(id)
            detailGameViewModel.getGameDetail()?.observe(this, ::gameDetailObserver)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun gameDetailObserver(detailGame: Resource<Game>) {
        when (detailGame) {
            is Resource.Error -> {
                binding?.mainProgressBar?.visibility = View.GONE
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
            is Resource.Loading -> binding?.mainProgressBar?.visibility = View.VISIBLE
            is Resource.Success -> {
                if (detailGame.data != null) {
                    binding?.mainProgressBar?.visibility = View.GONE
                    populateData(detailGame.data)
                }
            }
            else -> binding?.mainProgressBar?.visibility = View.GONE
        }
    }

    private fun populateData(data: Game?) {
        if (data != null){
            binding?.tvDetailGameName?.text = data.name
            binding?.tvGameGenres?.text = data.genres
            binding?.tvDetailGameRating?.text = data.rating.toString()
            binding?.rbDetailGameVote?.rating = data.rating.toFloat()
            binding?.tvDetailGameRatingCount?.text = data.ratingsCount.toString()
            binding?.tvGameCategoryRating?.text = data.ratings
            binding?.tvDetailGameReleased?.text = data.released.take(4)
            binding?.tvDetailGameDescription?.text = data.descriptionRaw

            binding?.ivDetailMoviePoster?.let {
                Glide.with(this)
                    .asBitmap()
                    .load(data.backgroundImage)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                            .error(R.drawable.ic_image_error)
                    )
                    .into(it)
            }

            when (data.ratingTop) {
                1 -> {
                    binding?.ivCategoryRating?.let {
                        Glide.with(this)
                            .load(R.drawable.ic_skip)
                            .apply(
                                RequestOptions.placeholderOf(R.drawable.ic_category_rating)
                                    .error(R.drawable.ic_category_rating)
                            )
                            .into(it)
                    }
                    binding?.tvCategoryRating?.text = getString(R.string.label_skip)
                }
                3 -> {
                    binding?.ivCategoryRating?.let {
                        Glide.with(this)
                            .load(R.drawable.ic_meh)
                            .apply(
                                RequestOptions.placeholderOf(R.drawable.ic_category_rating)
                                    .error(R.drawable.ic_category_rating)
                            )
                            .into(it)
                    }
                    binding?.tvCategoryRating?.text = getString(R.string.label_meh)
                }
                4 -> {
                    binding?.ivCategoryRating?.let {
                        Glide.with(this)
                            .load(R.drawable.ic_recommended)
                            .apply(
                                RequestOptions.placeholderOf(R.drawable.ic_category_rating)
                                    .error(R.drawable.ic_category_rating)
                            )
                            .into(it)
                    }
                    binding?.tvCategoryRating?.text = getString(R.string.label_recomended)
                }
                5 -> {
                    binding?.ivCategoryRating?.let {
                        Glide.with(this)
                            .load(R.drawable.ic_exceptional)
                            .apply(
                                RequestOptions.placeholderOf(R.drawable.ic_category_rating)
                                    .error(R.drawable.ic_category_rating)
                            )
                            .into(it)
                    }
                    binding?.tvCategoryRating?.text = getString(R.string.label_exceptional)
                }
                else -> {
                    binding?.let {
                        Glide.with(this)
                            .load(R.drawable.ic_category_rating)
                            .into(it.ivCategoryRating)
                    }
                    binding?.tvCategoryRating?.text = getString(R.string.label_none)
                }
            }

            binding?.mainProgressBar?.visibility = View.GONE
            var statusBookmark = data.isBookmark
            setStatusBookmark(statusBookmark)
            activityDetailGameBinding?.btnBookmarkMovie?.setOnClickListener {
                statusBookmark = !statusBookmark
                detailGameViewModel.setBookmarkGame(data, statusBookmark)
                setStatusBookmark(statusBookmark)
                setStatusNotification(data, statusBookmark)
            }
        }
    }

    private fun setStatusBookmark(statusBookmark: Boolean) {
        if (statusBookmark) {
            activityDetailGameBinding?.btnBookmarkMovie?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_bookmark
                )
            )
        } else {
            activityDetailGameBinding?.btnBookmarkMovie?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_bookmark_border
                )
            )
        }
    }

    private fun setStatusNotification(dataGame: Game, statusBookmark: Boolean) {
        if (statusBookmark) {
            val snackBar =
                activityDetailGameBinding?.let {
                    Snackbar.make(
                        it.layoutDetailGame,dataGame.name.plus(" ").plus(getString(R.string.notif_bookmark_add)),
                        Snackbar.LENGTH_SHORT)
                }
            snackBar?.view?.setBackgroundColor(Color.parseColor("#1EB2A6"))
            snackBar?.setTextColor(Color.parseColor("#FFE2FF"))
            snackBar?.show()
        } else {
            val snackBar =
                activityDetailGameBinding?.let { Snackbar.make(it.layoutDetailGame,dataGame.name.plus(" ").plus(getString(R.string.notif_bookmark_delete)),Snackbar.LENGTH_SHORT) }
            snackBar?.view?.setBackgroundColor(Color.parseColor("#FF4081"))
            snackBar?.setTextColor(Color.parseColor("#FFE2FF"))
            snackBar?.show()
        }
    }
}