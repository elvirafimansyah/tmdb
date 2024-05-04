package com.example.movie.service

import com.example.movie.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
  @GET("movie/popular")
  fun getPopularMovie(
    @Query("page") page: Int,
  ) : Call<MovieResponse>

}