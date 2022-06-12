package com.sadxlab.movielist.view.adapter

import android.content.Context
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sadxlab.movielist.R
import com.sadxlab.movielist.retrofit.responses.Search
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.IndexOutOfBoundsException
import java.util.*


class PaginationAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val context: Context
    private var movieList: MutableList<Search>?
    fun setMovieList(movieList: List<Search>?) {
        this.movieList = movieList as MutableList<Search>?
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        val viewItem: View = inflater.inflate(R.layout.item_movie, parent, false)
        viewHolder = MovieViewHolder(viewItem)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = movieList!![position]
        val movieViewHolder = holder as MovieViewHolder
        movieViewHolder.tvTitle.setText(movie.title)
        movieViewHolder.tvYear.setText(movie.year)
        if (!movie.poster.isEmpty()) {
            Picasso.get()
                .load(movie.poster)
                .into(movieViewHolder.ivThumbnail, object : Callback {
                    override fun onSuccess() {

                    }

                    override fun onError(e: Exception?) {
                        movieViewHolder.ivThumbnail.setImageResource(R.drawable.image_error)
                    }
                })
        }
    }

    override fun getItemCount(): Int {
        return if (movieList == null) 0 else movieList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    fun add(movie: Search) {
        movieList!!.add(movie)
        notifyItemInserted(movieList!!.size - 1)
    }

    fun addAll(moveResults: List<Search>) {
        for (result in moveResults) {
            add(result)
        }
    }

    fun clear() {
        movieList?.clear()
    }

    fun getItem(position: Int): Search {
        return movieList!![position]
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivThumbnail = itemView.findViewById<ImageView>(com.sadxlab.movielist.R.id.ivThumbnail)
        val tvTitle = itemView.findViewById<TextView>(com.sadxlab.movielist.R.id.tvTitle)
        val tvYear = itemView.findViewById<TextView>(com.sadxlab.movielist.R.id.tvYear)

        init {
            // movieTitle = itemView.findViewById(R.id.movie_title)
            //  movieImage = itemView.findViewById(R.id.movie_poster) as ImageView
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById(R.id.loadmore_progress)

        }
    }

    companion object {
    }

    init {
        this.context = context
        movieList = LinkedList()
    }
}