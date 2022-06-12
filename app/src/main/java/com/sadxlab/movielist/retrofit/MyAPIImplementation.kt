package com.sadxlab.movielist.retrofit

import com.sadxlab.movielist.retrofit.responses.MovieResponse
import com.sadxlab.movielist.utils.Constants.OMDB_API_KEY
import com.sadxlab.movielist.utils.Constants.SEARCH_TYPE
import java.lang.Exception

class MyAPIImplementation {

    private val request = RetrofitManager.retrofit.create(MyAPI::class.java)

    suspend fun getMovieListByPage(searchText :String,pageNumber:Int): Resource<MovieResponse> {
        return try {
            ResourceHandler.handleSuccess(
                request.getMovieListByPage(OMDB_API_KEY,searchText,SEARCH_TYPE,pageNumber)
            )
        } catch (ex: Exception) {
            ResourceHandler.handleException(ex)
        }
    }

}
