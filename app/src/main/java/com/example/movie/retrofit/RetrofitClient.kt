package com.example.movie.retrofit

import kotlinx.coroutines.newFixedThreadPoolContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
  private const val BASE_URL = "https://api.themoviedb.org/3/"
  private const val api_key = "d7784053fbd019c7ff26a3a63e0f1176"
  private const val token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNzc4NDA1M2ZiZDAxOWM3ZmYyNmEzYTYzZTBmMTE3NiIsInN1YiI6IjY2MzViOWExNjY1NjVhMDEyYzE1MDUzNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.UXjlJfaHnE1R1A_Tt-VMb41lYFdtWXT1tUw9i_ikXMI"


  val retrofitInstance: Retrofit by lazy {
    val logging = HttpLoggingInterceptor()
    logging.level = (HttpLoggingInterceptor.Level.BODY)

//    val requestInterceptor = Interceptor {chain ->
//      val url = chain.request()
//        .url
//        .newBuilder()
//        .addQueryParameter("api_key", api_key)
//        .build()
//
//      val request = chain.request()
//        .newBuilder()
//        .addHeader("accept", "application/json")
//        .addHeader("Authorization", "Bearer $token")
//        .url(url)
//        .build()
//
//      return@Interceptor chain.proceed(request)
//    }

    val requestInterceptor = Interceptor {chain ->
      val url = chain.request()
        .url
        .newBuilder()
        .addQueryParameter("api_key", api_key)
        .build()

      val request = chain.request()
        .newBuilder()
        .url(url)
        .addHeader("Authorization", "Bearer $token")
        .addHeader("accept", "application/json")
        .build()

      return@Interceptor chain.proceed(request)
    }

    val client = OkHttpClient.Builder()
    client.addInterceptor(logging)
    client.addInterceptor(requestInterceptor)

    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(client.build())
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

}