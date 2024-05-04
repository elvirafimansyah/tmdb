package com.example.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.model.MovieResponse
import com.example.movie.R

class MovieAdapter(
  val context: Context,
  var movies: ArrayList<MovieResponse.Result>,
  val listener: OnAdapterListener
): RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {
  val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342/"

  class MyViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
    val ivMovie = view.findViewById<ImageView>(R.id.iv_movie)
    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
    val tvPopularity = view.findViewById<TextView>(R.id.tv_popularity)
    val tvLanguage = view.findViewById<TextView>(R.id.tv_popularity)
    val tvRelease = view.findViewById<TextView>(R.id.tv_release)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_card, parent, false)
    return MyViewHolder(view)
  }

  override fun getItemCount(): Int = movies.size

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val results = movies[position]
    Glide.with(context).load(POSTER_BASE_URL + "${results.poster_path}")
      .override(342, 400)
      .error(R.drawable.ic_launcher_background)
      .into(holder.ivMovie)

    holder.tvTitle.text = results.title
    holder.tvLanguage.text = results.original_language
    holder.tvPopularity.text = results.popularity.toString()
    holder.tvRelease.text = results.release_date
    holder.itemView.setOnClickListener {
      listener.onDetail(results)
    }
  }

  public fun setData(result: List<MovieResponse.Result>) {
    movies.clear()
    movies.addAll(result)
    notifyDataSetChanged()
  }

  public fun setFilteredList(result: ArrayList<MovieResponse.Result>) {
    this.movies = result
    notifyDataSetChanged()
  }


  interface OnAdapterListener {
    fun onDetail(results: MovieResponse.Result)
  }

}