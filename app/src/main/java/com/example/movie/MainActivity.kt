package com.example.movie

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.adapter.MovieAdapter
import com.example.movie.model.MovieResponse
import com.example.movie.retrofit.RetrofitClient
import com.example.movie.service.MovieApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class MainActivity : AppCompatActivity() {
  private lateinit var rvMovie: RecyclerView
  private lateinit var movieAdapter: MovieAdapter
  private lateinit var svSearch: SearchView
  private lateinit var movieList: ArrayList<MovieResponse.Result>
  private fun initComponents() {
    rvMovie = findViewById(R.id.rv_movie)
    svSearch = findViewById(R.id.sv_search)
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    supportActionBar!!.title = "TMDB"
    supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#0D253F")))
    initComponents()
    setupList()
    setupListener()
    getPopularMovie()
  }

  override fun onStart() {
    super.onStart()
    getPopularMovie()
  }

  private fun setupList() {
    movieAdapter = MovieAdapter(this@MainActivity, arrayListOf(), object: MovieAdapter.OnAdapterListener {
      override fun onDetail(results: MovieResponse.Result) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("movie", results)
        startActivity(intent)
      }

    })
    rvMovie.adapter = movieAdapter
  }

  private fun setupListener() {
    svSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        return false
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        filterList(newText!!)
        return false
      }

    })
  }

  private fun filterList(query: String) {
    if(query != null) {
      var filteredList = ArrayList<MovieResponse.Result>()
      for(i in movieList) {
        if(i.title.lowercase(Locale.ROOT).contains(query)) {
          filteredList.add(i)
        }
      }

      if(filteredList.isEmpty()) {
        Toast.makeText(this@MainActivity, "Data not found", Toast.LENGTH_SHORT).show()
      } else {
        movieAdapter.setFilteredList(filteredList)
      }
    }
  }

  private fun getPopularMovie() {
    val apiService = RetrofitClient.retrofitInstance.create(MovieApi::class.java)
    val call = apiService.getPopularMovie(1)
    call.enqueue(object: Callback<MovieResponse> {
      override fun onResponse(p0: Call<MovieResponse>, response: Response<MovieResponse>) {
        if(response.isSuccessful) {
          val result = response.body()!!.results
          movieList = ArrayList(result)
          movieAdapter.setData(result)
        }else {
          Toast.makeText(this@MainActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
        }
      }

      override fun onFailure(p0: Call<MovieResponse>, p1: Throwable) {
        Toast.makeText(this@MainActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
      }

    })
  }

}