package com.example.infosysdemoapplication.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.toolbox.Volley
import com.example.infosysdemoapplication.R
import com.example.infosysdemoapplication.network.VolleyQueueHolder
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.messaging.FirebaseMessaging

private const val NUM_PAGES = 5
private const val TAG = "MoviePagerActivity"
class MovieSlidePagerActivity : FragmentActivity() {

    private lateinit var viewpager : ViewPager2
    private lateinit var tabLayout : TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_slide_pager)

        VolleyQueueHolder.imdbQueue = Volley.newRequestQueue(this)
        viewpager = findViewById(R.id.pager)
        tabLayout = findViewById(R.id.tab_layout)


        val pagerAdapter = MovieSlidePagerAdapter(this)
        viewpager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout,viewpager){ tab, position ->
            tab.text = when(position){
                0-> "Adventure"
                1-> "Horror"
                2 -> "Mystery"
                3 -> "Comedy"
                4 -> "Sci-Fi"
                else -> "Religious"
            }
        }.attach()

    }

    override fun onStart() {
        super.onStart()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener{
            if (!it.isSuccessful){
                Log.w(TAG, "Fetching FCM registration token has failed", it.exception)
                return@OnCompleteListener
            }
            val token = it.result

            val msg = token ?: "Token is not good"
            Log.d(TAG, msg)
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        })
    }

    override fun onBackPressed() {
        if (viewpager.currentItem == 0)
            super.onBackPressed()
        else
            viewpager.currentItem = viewpager.currentItem - 1
    }



    private inner class MovieSlidePagerAdapter(fa : FragmentActivity): FragmentStateAdapter(fa){
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment  = MovieListFragment(position)
    }
}