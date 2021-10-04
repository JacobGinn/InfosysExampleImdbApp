package com.example.infosysdemoapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/**
 *
 * @author Jacob Ginn
 */
class MovieListViewModelFactory(
    private val position : Int) : ViewModelProvider.Factory{

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)){
            return MovieListViewModel(position) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}