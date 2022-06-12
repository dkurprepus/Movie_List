package com.sadxlab.movielist.view.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sadxlab.movielist.R
import com.sadxlab.movielist.view.adapter.PaginationAdapter
import com.sadxlab.movielist.view.adapter.PaginationScrollListener
import com.sadxlab.movielist.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MovieViewModel
    var searchText: String = ""
    private var paginationAdapter: PaginationAdapter? = null

    //--
    private var PAGE_START: Int = 1
    private var isLoading = false
    private var isLastPage = false
    private var currentPage = PAGE_START
    private var TOTAL_PAGES = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        setContentView(R.layout.activity_main)
        val gridLayoutManager = GridLayoutManager(this, 2)

        rvMovieList.layoutManager = gridLayoutManager

        rvMovieList.addOnScrollListener(object : PaginationScrollListener(gridLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                loadNextPage();
            }

            override val isLastPageA: Boolean
                get() = isLastPage
            override val isLoadingA: Boolean
                get() = isLoading


        })




        ivSearch.setOnClickListener {
            it.hideKeyboard()
            if (edtSearch.text.isEmpty()) {
                Toast.makeText(this, "Please Enter Text", Toast.LENGTH_SHORT).show()
            } else {
                searchText = edtSearch.text.toString()
                edtSearch.setText("")
                resetFlags()
                viewModel.getMovieListByPage(searchText, currentPage, this)

            }
        }

        viewModel.observableMovieListRes.observe(this, Observer {
            TOTAL_PAGES = it.totalResults.toInt() / 10
            Log.d(TAG, "onCreate Success: " + it)
            Log.d(TAG, "onCreate Success size is  : " + it.Search)
            if (it.Search.size == 0) {
                //resetFlags()
                llEmptyList.visibility = View.VISIBLE
                rvMovieList.visibility = View.GONE
                tvEmptySearchText.text = getString(R.string.no_result_title)
                tvEmptySearchSubText.text = getString(R.string.try_new_search)
            } else {
                if (currentPage == 1) {
                    paginationAdapter = PaginationAdapter(this)
                    rvMovieList.adapter = paginationAdapter
                    paginationAdapter?.setMovieList(it.Search)
                } else {
                    paginationAdapter?.addAll(it.Search)

                }
                llEmptyList.visibility = View.GONE
                rvMovieList.visibility = View.VISIBLE
                if (currentPage <= TOTAL_PAGES)
                    Log.d(
                        TAG,
                        "onCreate: There is more pages current page is " + currentPage + " and Total page is " + TOTAL_PAGES
                    )
                else isLastPage = true;
            }
        })

        viewModel.observableMovieListError.observe(this, Observer {
            Log.d(TAG, "onCreate Error: " + it)
            if (it) {
                Log.d(TAG, "onCreate: currentPage  " + currentPage)
                if (currentPage == 1) {
                    resetFlags()
                    llEmptyList.visibility = View.VISIBLE
                    rvMovieList.visibility = View.GONE
                    tvEmptySearchText.text = getString(R.string.no_result_title)
                    tvEmptySearchSubText.text = getString(R.string.try_new_search)
                }

            }
        })

    }

    private fun loadNextPage() {
        viewModel.getMovieListByPage(searchText, currentPage, this)
        isLoading = false
        if (currentPage !== TOTAL_PAGES)
            Log.d(
                TAG,
                "onCreate: There is more pages current page is " + currentPage + " and Total page is " + TOTAL_PAGES
            )
        else isLastPage =
            true
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun resetFlags() {
        PAGE_START = 1
        isLoading = false
        isLastPage = false
        TOTAL_PAGES = 0
        currentPage = PAGE_START
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}