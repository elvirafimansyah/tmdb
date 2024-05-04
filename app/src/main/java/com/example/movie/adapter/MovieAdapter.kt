package com.example.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.model.MovieResponse
import com.example.movie.R

class MovieAdapter(
  val movies: ArrayList<MovieResponse.Result>
): RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {
  class MyViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
    val ivMovie = view.findViewById<ImageView>(R.id.iv_movie)
    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
    val tvPopularity = view.findViewById<TextView>(R.id.tv_popularity)
    val tvPopularity = view.findViewById<TextView>(R.id.tv_popularity)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_card, parent, false)
    return MyViewHolder(view)
  }

  override fun getItemCount(): Int = movies.size

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    TODO("Not yet implemented")
  }
}