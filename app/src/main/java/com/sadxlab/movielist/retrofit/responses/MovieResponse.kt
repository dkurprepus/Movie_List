package com.sadxlab.movielist.retrofit.responses

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @JsonProperty(value = "Response")
    @SerializedName("Response")
    val Response: String,
    @JsonProperty(value = "Search")
    @SerializedName("Search")
    val Search: List<Search>,
    @JsonProperty(value = "totalResults")
    @SerializedName("totalResults")
    val totalResults: String
)