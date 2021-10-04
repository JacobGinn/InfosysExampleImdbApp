package com.example.infosysdemoapplication.ui


import android.util.Log
import androidx.lifecycle.*
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.example.infosysdemoapplication.model.*
import com.example.infosysdemoapplication.network.IMDbApi
import com.example.infosysdemoapplication.network.VolleyQueueHolder
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception
import java.util.*

private const val LOG = "MovieViewModel"

class MovieListViewModel(private val position: Int) : ViewModel() {

    private val search: String
        get() = when (position) {
            0 -> "Adventure"
            1 -> "Horror"
            2 -> "Mystery"
            3 -> "Comedy"
            4 -> "sci_fi"
            else -> "Religious"
        }.lowercase(Locale.getDefault())

    private val _movies = MutableLiveData<List<MovieDto>>()
    val movies: LiveData<List<MovieDto>>
        get() = _movies

    init {
        //loadMovies()

        loadMoviesVolley()

    }

    private fun loadMovies() {
        viewModelScope.launch {
            try {
                Log.d(LOG, "We are retrieving the ids of the horror movie titles")
                val ids = IMDbApi.retrofitService.getMovieByGenre("/chart/popular/genre/$search")
                val temp = mutableListOf<Movie>()
                for (i in 0 until 5) {
                    val id = ids[i].split("/")[2]
                    temp.add(IMDbApi.retrofitService.getMovieDetails(id))
                }
                //_movies.value = temp
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun sendRequest(idList: List<String>) {
        //Volley Practice
        val queryParams = idList.joinToString ("&") {
            "ids=$it"
        }

        val url = "https://imdb8.p.rapidapi.com/title/get-meta-data?$queryParams&region=US"

        val stringRequest = object : StringRequest(Request.Method.GET, url,
            { response ->
                //Log.d(LOG, response)
                val jsonObject = JSONObject(response)
                _movies.value = idList.map { id ->
                    jsonObject.getJSONObject(id).let { movieID ->
                        val titleObj = movieID.getJSONObject("title")
                        val imageObj = titleObj.getJSONObject("image")
                        val ratingObj = movieID.getJSONObject("ratings")
                        val releaseDate = movieID.getString("releaseDate")
                        val certificate = movieID.getString("certificate")
                        val image = MovieImageDto(imageObj.getString("url"))
                        val title = MovieTitle(
                            titleObj.getString("id"), image,
                            try{titleObj.getInt("runningTimeInMinutes")}catch(e:Exception){-1},
                            titleObj.getString("title")
                        )
                        val rating =
                            MovieRating(
                                try{ratingObj.getDouble("rating")}catch (e:Exception){-1.0},
                                try{ratingObj.getInt("ratingCount")}catch (e:Exception){0}
                            )
                        MovieDto(title, rating, releaseDate, certificate)

                    }
                }
            },
            { error -> Log.d(LOG, error.stackTraceToString()) }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf(
                    "x-rapidapi-key" to "21f006d651mshc817acd37fce51dp162c16jsnf6fbb6e9869c",
                    "x-rapidapi-host" to "imdb8.p.rapidapi.com")
            }
        }
        println(stringRequest.headers)


        VolleyQueueHolder.imdbQueue.add(stringRequest)
    }

    private fun loadMoviesVolley() {
        val url = "https://imdb8.p.rapidapi.com/title/get-popular-movies-by-genre?genre=/chart/popular/genre/$search"
        val request = object : JsonArrayRequest(url,
            { array ->
                val idList = mutableListOf<String>()
                for (i in 0 until 5) {
                    idList.add(array.getString(i))
                }
                sendRequest(idList.map {it.split("/")[2] })

            },
            { error -> Log.d(LOG, error.stackTraceToString()) })
        {
            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf(
                    "x-rapidapi-key" to "21f006d651mshc817acd37fce51dp162c16jsnf6fbb6e9869c",
                        "x-rapidapi-host" to "imdb8.p.rapidapi.com")
            }
        }

        VolleyQueueHolder.imdbQueue.add(request)
    }

}

