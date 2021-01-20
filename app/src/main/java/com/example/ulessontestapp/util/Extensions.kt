package com.example.ulessontestapp.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_chapter.*

fun ImageView.loadImage(url: String) {
    if (url.isNotEmpty()) {
        Glide.with(context)
            .load(url)
            .into(this)
    }
}
