package com.sadxlab.movielist.retrofit.responses

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class Search(
    @JsonProperty(value = "Poster")
    @SerializedName("Poster")
    val poster: String,
    @JsonProperty(value = "Title")
    @SerializedName("Title")
    val title: String,
    @JsonProperty(value = "Type")
    @SerializedName("Type")
    val type: String,
    @JsonProperty(value = "Year")
    @SerializedName("Year")
    val year: String,
    @JsonProperty(value = "imdbID")
    @SerializedName("imdbID")
    val imdbID: String

)