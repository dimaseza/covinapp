package com.daivers.covinapp.model

import com.daivers.covinapp.data.News

data class NewsItemNetwork(
    val articles: List<NewsItem>
)

data class NewsItem(
    val source: SourceName,
    val author: String?,
    val title: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val description: String,
    val content: String?
)

data class SourceName(
    val name: String
)

fun NewsItemNetwork.asDataModel(): List<News> {
    return articles.map {
        News(
            sourceName = it.source.name,
            author = it.author ?: "Tidak diketahui",
            content = it.content ?: "Kosong",
            datePublished = it.publishedAt,
            imgUrl = it.urlToImage,
            title = it.title,
            webUrl = it.url,
            desc = it.description
        )
    }
}