package com.example.foodbookapplication.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.foodbookapplication.R

fun ImageView.downloadImage(url: String?, placeholder: CircularProgressDrawable) {

    // placeholder inene kadar gösterilicek şey
    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)

    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
    // into nereye yükleyeceğiz onu gösteriyor
}

// Dönen şeyi bu ayarlıyor
fun doPlaceholder(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}