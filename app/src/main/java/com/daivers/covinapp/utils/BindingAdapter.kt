package com.daivers.covinapp.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.daivers.covinapp.R
import com.daivers.covinapp.adapter.TodayNewsAdapter
import com.daivers.covinapp.adapter.TopNewsAdapter
import com.daivers.covinapp.data.CovidCase
import com.daivers.covinapp.data.News

@BindingAdapter("listItem")
fun setListItem(recyclerView: RecyclerView, data: List<News>?) {
    val adapter = recyclerView.adapter as TopNewsAdapter
    adapter.submitList(data)
}

@BindingAdapter("setTextCase")
fun setTextCase(tv: TextView, data: String?) {
    tv.text = data ?: "----"
}

@BindingAdapter("listData")
fun setListData(recyclerView: RecyclerView, data: List<News>?) {
    val adapter = recyclerView.adapter as TodayNewsAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions().override(80, 80)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("progressBarStatus")
fun setStatusProgressBar(progressBar: ProgressBar, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> progressBar.visibility = View.VISIBLE
        else -> progressBar.visibility = View.GONE
    }
}

@BindingAdapter("imgViewStatus")
fun setImageViewStatus(imgView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.ERROR -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.ic_connection_error)
        }
        else -> imgView.visibility = View.GONE
    }
}