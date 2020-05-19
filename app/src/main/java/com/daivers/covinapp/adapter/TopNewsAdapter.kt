package com.daivers.covinapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daivers.covinapp.data.News
import com.daivers.covinapp.databinding.NewsTopItemBinding

class TopNewsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<News, NewsViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(NewsTopItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsData = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(newsData)
        }
        holder.bind(newsData)
    }
}

class NewsViewHolder(private val binding: NewsTopItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(news: News) {
        binding.item = news
        binding.executePendingBindings()
    }
}


class OnClickListener(val clickListener: (news: News) -> Unit) {
    fun onClick(news: News) = clickListener(news)
}