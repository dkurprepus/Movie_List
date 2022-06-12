package com.sadxlab.movielist.retrofit

import com.sadxlab.movielist.retrofit.responses.MovieResponse
import com.sadxlab.movielist.utils.Constants.OMDB_API_KEY
import com.sadxlab.movielist.utils.Constants.SEARCH_TYPE
import retrofit2.http.*


interface MyAPI {
//  viewModel.getMovieList("https://www.omdbapi.com/?apikey=b9bd48a6&s=" + searchText + "&type=movie&page=1")

    @GET("/")
    suspend fun getMovieListByPage(
        @Query("apikey") apiKey: String=OMDB_API_KEY,
        @Query("s") searchtext: String,
        @Query("type") movie: String=SEARCH_TYPE,
        @Query("page") pageNumber:Int,
    ): MovieResponse

}