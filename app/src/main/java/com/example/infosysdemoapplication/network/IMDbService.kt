package com.example.infosysdemoapplication.network

import com.example.infosysdemoapplication.model.Movie
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val apiKey = "x-rapidapi-key: 21f006d651mshc817acd37fce51dp162c16jsnf6fbb6e9869c"
private const val BASE_URL = "https://imdb8.p.rapidapi.com"
private val moshi = MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())
private val retrofit = Retrofit.Builder().addConverterFactory(moshi).baseUrl(BASE_URL).build()

interface IMDbService {

    @Headers(
        apiKey,
        "x-rapidapi-host: imdb8.p.rapidapi.com"

    )
    @GET("/title/get-popular-movies-by-genre")
    suspend fun getMovieByGenre(
        @Query("genre") genre : String
    ) : Array<String>

    @Headers(
        apiKey,
        "x-rapidapi-host: imdb8.p.rapidapi.com"

    )
    @GET("/title/get-details")
    suspend fun getMovieDetails(
        @Query("tconst") titleId : String
    ) : Movie
}

object IMDbApi{

    val retrofitService : IMDbService by lazy {
        retrofit.create(IMDbService::class.java)
    }
}