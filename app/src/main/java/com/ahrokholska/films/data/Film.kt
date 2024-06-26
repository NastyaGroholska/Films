package com.ahrokholska.films.data

import com.google.gson.annotations.SerializedName

data class Film(
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    val overview: String
)