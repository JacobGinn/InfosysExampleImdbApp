package com.example.infosysdemoapplication.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.infosysdemoapplication.databinding.MovieListFragmentBinding

class MovieListFragment(private val position : Int) : Fragment() {

    private var _binding : MovieListFragmentBinding? = null
    private val binding : MovieListFragmentBinding
        get () = _binding!!
    private lateinit var viewModel: MovieListViewModel
    private lateinit var movieAdapter : MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieListFragmentBinding.inflate(layoutInflater)
        logMessage("onCreateView called")
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logMessage("onViewCreated called")

        val viewModelFactory = MovieListViewModelFactory(position)
        viewModel =ViewModelProvider(this, viewModelFactory).get(MovieListViewModel::class.java)
        movieAdapter = MovieListAdapter()
        binding.movieRecycler.adapter = movieAdapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.movies.observe(viewLifecycleOwner,{
            movieAdapter.submitList(it)
        })

        val button : Button = Button(context)
        binding.relativeRecycler.addView(button)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        logMessage("onAttach called")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logMessage("onCreate called")
    }

    override fun onStart() {
        super.onStart()
        logMessage("onStart called")
    }

    override fun onPause() {
        super.onPause()
        logMessage("onPause called")
    }

    override fun onStop() {
        super.onStop()
        logMessage("onStop called")
    }

    override fun onDetach() {
        super.onDetach()
        logMessage("onDetach called")
    }

    override fun onDestroy() {
        super.onDestroy()
        logMessage("onDestroy called")
    }
    private fun logMessage(message : String) = Log.d("MovieListFragment", message)


}