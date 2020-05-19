package com.daivers.covinapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daivers.covinapp.data.News
import com.daivers.covinapp.databinding.ListNewsItemBinding

class TodayNewsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<News, AllNewsViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllNewsViewHolder {
        return AllNewsViewHolder(ListNewsItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AllNewsViewHolder, position: Int) {
        val newsData = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(newsData)
        }
        holder.bind(newsData)
    }
}

class AllNewsViewHolder(private val binding: ListNewsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(news: News) {
        binding.item = news
        binding.executePendingBindings()
    }
}