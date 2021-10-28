package com.example.foodRecipes.util

import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("android:imageURL")
fun setImageURL(imageView: ImageView, URL: String?) = try {
    imageView.load(URL)
} catch (ignored: Exception) {
}

@BindingAdapter("android:trim")
fun setTrim(textView: TextView?, text: String?) = try {
    textView?.text = text?.trim { it <= ' ' }
} catch (ignored: Exception) {
}

@BindingAdapter("android:buttonURL")
fun initClickListeners(button: AppCompatButton, link: String?) {
    button.setOnClickListener {
        if (link == null)
            Toast.makeText(button.context, "Link is NULL", Toast.LENGTH_SHORT).show()
        else button.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
    }
}