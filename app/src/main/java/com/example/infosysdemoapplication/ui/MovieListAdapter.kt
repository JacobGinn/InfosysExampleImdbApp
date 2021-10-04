package com.example.infosysdemoapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.infosysdemoapplication.databinding.MovieListItemBinding
import com.example.infosysdemoapplication.model.MovieDto

class MovieListAdapter : ListAdapter<MovieDto, MovieListAdapter.MovieViewHolder>(DiffCallback) {

    class MovieViewHolder(private val binding : MovieListItemBinding ) : RecyclerView.ViewHolder(binding.root){
        var collapse = true


        fun bind(movie : MovieDto){
            binding.movie  = movie
            binding.executePendingBindings()
        }

        fun showViews(){
            binding.length.visibility = View.VISIBLE
            binding.movieReleaseYear.visibility = View.VISIBLE
            collapse = false
        }

        fun hideViews(){
            binding.length.visibility = View.GONE
            binding.movieReleaseYear.visibility = View.GONE
            collapse = true
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the list of Movies
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<MovieDto>() {
        override fun areItemsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
            return oldItem.title.id == newItem.title.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(MovieListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val movie : MovieDto = getItem(position)
        holder.bind(movie)

        holder.itemView.setOnClickListener{
            if(holder.collapse)
                holder.showViews()
            else
                holder.hideViews()
        }
    }


}