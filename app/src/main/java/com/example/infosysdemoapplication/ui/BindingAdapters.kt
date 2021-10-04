package com.example.infosysdemoapplication.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("bindTime")
fun bindTime(tv : TextView, runningTime : Int?) {

    runningTime?.let {
        val hour = if(runningTime/60 == 0) "" else "${runningTime/60}h "
        val minute  = "${ runningTime % 60 }m"
        tv.text = hour + minute
    }

}

@BindingAdapter("bindImage")
fun bindImage(imageView: ImageView , url : String?){
//    url?.let {
//        val imageUri = it.toUri().buildUpon().scheme("https").build()
//
//        Glide.with(imageView.context).load(imageUri)
//            .into(imageView)
//    }
    url?.let {
        Picasso.get()
            .load(it)
            .resize(100,150)
            .centerCrop()
            .into(imageView)
    }
}

@BindingAdapter("bindText")
fun bindText(tv : TextView, text : String?){
    text?.let{
        tv.text = it
    }
}

@BindingAdapter("bindNumber")
fun bindNumber(tv : TextView, rating : Double){
    rating?.let{
        tv.text = it.toString()
    }
}