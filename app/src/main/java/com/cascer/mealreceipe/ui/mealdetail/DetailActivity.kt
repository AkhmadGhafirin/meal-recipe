package com.cascer.mealreceipe.ui.mealdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cascer.mealreceipe.R
import com.cascer.mealreceipe.data.Resource
import com.cascer.mealreceipe.databinding.ActivityDetailBinding
import com.cascer.mealreceipe.domain.model.Meal
import com.cascer.mealreceipe.utils.ImageUtils.load
import com.cascer.mealreceipe.utils.gone
import com.cascer.mealreceipe.utils.visible
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private var id = ""
    private var youTubePlayer: YouTubePlayer? = null
    private var isBookmark = false
    private var firstLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id = intent.getStringExtra(EXTRA_ID).orEmpty()
        setupViews()
        requestData()
    }

    private fun setupViews() {
        with(binding) {
            toolbar.setNavigationOnClickListener { finish() }
        }
    }

    private fun setupData(item: Meal) {
        with(binding) {
            ivMeal.load(this@DetailActivity, item.strMealThumb)
            tvMeal.text = item.strMeal
            tvMealInstructions.text = item.strInstructions

            tvIngredient1.text =
                getString(R.string.ingredient, item.strMeasure1, item.strIngredient1)
            tvIngredient2.text =
                getString(R.string.ingredient, item.strMeasure2, item.strIngredient2)
            tvIngredient3.text =
                getString(R.string.ingredient, item.strMeasure3, item.strIngredient3)
            tvIngredient4.text =
                getString(R.string.ingredient, item.strMeasure4, item.strIngredient4)
            tvIngredient5.text =
                getString(R.string.ingredient, item.strMeasure5, item.strIngredient5)
            tvIngredient6.text =
                getString(R.string.ingredient, item.strMeasure6, item.strIngredient6)
            tvIngredient7.text =
                getString(R.string.ingredient, item.strMeasure7, item.strIngredient7)
            tvIngredient8.text =
                getString(R.string.ingredient, item.strMeasure8, item.strIngredient8)
            tvIngredient9.text =
                getString(R.string.ingredient, item.strMeasure9, item.strIngredient9)
            tvIngredient10.text =
                getString(R.string.ingredient, item.strMeasure10, item.strIngredient10)
            tvIngredient11.text =
                getString(R.string.ingredient, item.strMeasure11, item.strIngredient11)
            tvIngredient12.text =
                getString(R.string.ingredient, item.strMeasure12, item.strIngredient12)
            tvIngredient13.text =
                getString(R.string.ingredient, item.strMeasure13, item.strIngredient13)
            tvIngredient14.text =
                getString(R.string.ingredient, item.strMeasure14, item.strIngredient14)
            tvIngredient15.text =
                getString(R.string.ingredient, item.strMeasure15, item.strIngredient15)
            tvIngredient16.text =
                getString(R.string.ingredient, item.strMeasure16, item.strIngredient16)
            tvIngredient17.text =
                getString(R.string.ingredient, item.strMeasure17, item.strIngredient17)
            tvIngredient18.text =
                getString(R.string.ingredient, item.strMeasure18, item.strIngredient18)
            tvIngredient19.text =
                getString(R.string.ingredient, item.strMeasure19, item.strIngredient19)
            tvIngredient20.text =
                getString(R.string.ingredient, item.strMeasure20, item.strIngredient20)

            if (item.strIngredient1.isNotEmpty()) tvIngredient1.visible()
            if (item.strIngredient2.isNotEmpty()) tvIngredient2.visible()
            if (item.strIngredient3.isNotEmpty()) tvIngredient3.visible()
            if (item.strIngredient4.isNotEmpty()) tvIngredient4.visible()
            if (item.strIngredient5.isNotEmpty()) tvIngredient5.visible()
            if (item.strIngredient6.isNotEmpty()) tvIngredient6.visible()
            if (item.strIngredient7.isNotEmpty()) tvIngredient7.visible()
            if (item.strIngredient8.isNotEmpty()) tvIngredient8.visible()
            if (item.strIngredient9.isNotEmpty()) tvIngredient9.visible()
            if (item.strIngredient10.isNotEmpty()) tvIngredient10.visible()
            if (item.strIngredient11.isNotEmpty()) tvIngredient11.visible()
            if (item.strIngredient12.isNotEmpty()) tvIngredient12.visible()
            if (item.strIngredient13.isNotEmpty()) tvIngredient13.visible()
            if (item.strIngredient14.isNotEmpty()) tvIngredient14.visible()
            if (item.strIngredient15.isNotEmpty()) tvIngredient15.visible()
            if (item.strIngredient16.isNotEmpty()) tvIngredient16.visible()
            if (item.strIngredient17.isNotEmpty()) tvIngredient17.visible()
            if (item.strIngredient18.isNotEmpty()) tvIngredient18.visible()
            if (item.strIngredient19.isNotEmpty()) tvIngredient19.visible()
            if (item.strIngredient20.isNotEmpty()) tvIngredient20.visible()

            val videoId = item.strYoutube.replace("https://www.youtube.com/watch?v=", "")
            val youtubePlayerView = youtubePlayerView
            lifecycle.addObserver(youtubePlayerView)
            youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    this@DetailActivity.youTubePlayer = youTubePlayer
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            })

            fabBookmark.setOnClickListener { _ ->
                isBookmark = !isBookmark
                val message = if (isBookmark) getString(R.string.added_to_bookmark)
                else getString(R.string.remove_from_bookmark)
                Toast.makeText(this@DetailActivity, message, Toast.LENGTH_SHORT).show()
                viewModel.addBookmark(item, isBookmark)
                setStatusBookmark()
            }
        }
    }

    private fun requestData() {
        with(viewModel) {
            getDetail(id).observe(this@DetailActivity) {
                if (it != null) {
                    when (it) {
                        is Resource.Success -> {
                            binding.progressbar.gone()
                            it.data?.let { item -> setupData(item) }
                        }

                        is Resource.Loading -> {
                            binding.progressbar.visible()
                        }

                        is Resource.Error -> {
                            binding.progressbar.gone()
                            Toast.makeText(this@DetailActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }

            statusBookmark(id).observe(this@DetailActivity) {
                isBookmark = it.idMeal.isNotEmpty()
                if (firstLoad) {
                    firstLoad = false
                    setStatusBookmark()
                }
            }
        }
    }

    private fun setStatusBookmark() {
        if (isBookmark) {
            binding.fabBookmark.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.baseline_bookmark_white_24dp
                )
            )
        } else {
            binding.fabBookmark.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.baseline_bookmark_border_white_24dp
                )
            )
        }
    }

    companion object {
        const val EXTRA_ID = "id"
    }
}