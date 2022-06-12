package com.sadxlab.movielist.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sadxlab.movielist.retrofit.MyAPIImplementation
import com.sadxlab.movielist.retrofit.responses.MovieResponse
import com.sadxlab.movielist.retrofit.responses.Search
import com.sadxlab.movielist.utils.pdialog
import com.sadxlab.movielist.utils.pdialog_dismiss
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MovieViewModel() : ViewModel() {
    private val apiImplementation = MyAPIImplementation()
    val observableMovieListRes: MutableLiveData<MovieResponse> = MutableLiveData()
    val observableMovieListError: MutableLiveData<Boolean> = MutableLiveData()


    fun getMovieListByPage(searchText: String,pageNumber:Int,context: Context) {
        pdialog(context)
        GlobalScope.launch {
            apiImplementation.getMovieListByPage(searchText,pageNumber).onSuccess {
                observableMovieListRes.postValue(it)
                Handler(Looper.getMainLooper()).postDelayed({
                    //Do something after 100ms
                    pdialog_dismiss()
                }, 1000)
            }
                .onError {
                    Handler(Looper.getMainLooper()).postDelayed({
                        //Do something after 100ms
                        pdialog_dismiss()
                    }, 1000)
                    observableMovieListError.postValue(true)
                }
        }
    }



}