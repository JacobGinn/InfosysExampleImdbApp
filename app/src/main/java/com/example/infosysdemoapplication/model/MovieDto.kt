package com.example.infosysdemoapplication.model

import java.security.cert.Certificate

data class MovieDto(
    val title : MovieTitle,
    val rating : MovieRating,
    val releaseDate: String,
    val certificate: String
)

data class MovieTitle(
    val id : String,
    val img : MovieImageDto,
    val runningTime : Int,
    val title : String
)

data class MovieImageDto(val url : String)

data class MovieRating(
    val rating : Double,
    val ratingCount : Int
)


