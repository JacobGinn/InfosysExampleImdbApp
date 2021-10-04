package com.example.infosysdemoapplication.model

import com.squareup.moshi.Json
import kotlin.random.Random


data class Movie(
    val id : String,
    val image : MovieImage,
    @Json(name = "runningTimeInMinutes")
    val runningTime : Int,
    val title : String,
    val year : Int,
    val rating : String = "${Random.nextInt(1,9)}.${Random.nextInt(0,9)}"
    )

data class MovieImage(val url : String)


