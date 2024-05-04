package com.example.movie

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.movie.model.MovieResponse
import okhttp3.internal.parseCookie

class DetailActivity : AppCompatActivity() {
  private lateinit var ivMovie: ImageView
  private lateinit var tvTitle: TextView
  private lateinit var tvPopularity: TextView
  private lateinit var tvVote: TextView
  private lateinit var tvRelease: TextView
  private lateinit var tvOverview: TextView
  private lateinit var tvLanguage: TextView
  private lateinit var tvVoteAverage: TextView
  private val movies by lazy { intent.getSerializableExtra("movie") as? MovieResponse.Result }

  private fun initComponents() {
    ivMovie = findViewById(R.id.iv_detail_movie)
    tvTitle = findViewById(R.id.tv_detail_title)
    tvPopularity = findViewById(R.id.tv_detail_popularity)
    tvVote = findViewById(R.id.tv_vote)
    tvRelease = findViewById(R.id.tv_detail_release)
    tvOverview = findViewById(R.id.tv_overview)
    tvLanguage = findViewById(R.id.tv_detail_language)
    tvVoteAverage = findViewById(R.id.tv_vote_average)
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail)
    supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#0D253F")))
    initComponents()
    showDetailMovie()
  }

  private fun showDetailMovie() {
    val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342/"
    movies?. let {
      supportActionBar!!.title = "${it.title}"

      Glide.with(this@DetailActivity).load(POSTER_BASE_URL + it.poster_path)
        .override(400, 450)
        .error(R.drawable.ic_launcher_background)
        .into(ivMovie)

      tvTitle.text = it.title
      tvLanguage.text = it.original_language
      tvOverview.text = it.overview
      tvVote.text = it.vote_count.toString()
      tvPopularity.text = it.popularity.toString()
      tvRelease.text = it.release_date
      tvVoteAverage.text = it.vote_average.toString()


    } ?: run {
      Toast.makeText(this@DetailActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
    }
  }
}