package com.daivers.covinapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.daivers.covinapp.BuildConfig
import com.daivers.covinapp.data.News
import com.daivers.covinapp.model.asDataModel
import com.daivers.covinapp.network.NewsNetwork
import com.daivers.covinapp.utils.ApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val apiKey = BuildConfig.NEWS_API_KEY

    private val _topNews = MutableLiveData<List<News>>()
    val topNews: LiveData<List<News>>
        get() = _topNews

    private val _statusTopNews = MutableLiveData<ApiStatus>()
    val statusTopNews: LiveData<ApiStatus>
        get() = _statusTopNews

    private val _statusTodayNews = MutableLiveData<ApiStatus>()
    val statusTodayNews: LiveData<ApiStatus>
        get() = _statusTodayNews

    private val _allNews = MutableLiveData<List<News>>()
    val allNews: LiveData<List<News>>
        get() = _allNews

    private val _selectedTopNews = MutableLiveData<News>()
    val selectedTopNews: LiveData<News>
        get() = _selectedTopNews

    private val _selectedNews = MutableLiveData<News>()
    val selectedNews: LiveData<News>
        get() = _selectedNews

    init {
        getTopNews()
        getAllNews()
    }

    fun displaySelectedTopNews(data: News) {
        _selectedTopNews.value = data
    }

    fun displaySelectedTodayNews(data: News) {
        _selectedNews.value = data
    }

    private fun getTopNews() {
        coroutineScope.launch {
            _statusTopNews.value = ApiStatus.LOADING
            val getTopNews = NewsNetwork.NEWS.getTopHeadlineNewsAsync("id", apiKey, "health")
            try {
                val news = getTopNews.await()
                val result = news.asDataModel()
                _statusTopNews.value = ApiStatus.DONE
                _topNews.value = result
            } catch (e: Exception) {
                _topNews.value = ArrayList()
                _statusTopNews.value = ApiStatus.ERROR
                Log.i("ErrorNetwork", "${e.message}")
            }
        }
    }

    private fun getAllNews() {
        coroutineScope.launch {
            _statusTodayNews.value = ApiStatus.LOADING
            val getTopNews = NewsNetwork.NEWS.getAllNewsAsync("id", apiKey, "health")
            try {
                val news = getTopNews.await()
                val result = news.asDataModel()
                _statusTodayNews.value = ApiStatus.DONE
                _allNews.value = result
            } catch (e: Exception) {
                _allNews.value = ArrayList()
                _statusTodayNews.value = ApiStatus.ERROR
                Log.i("ErrorNetwork", "${e.message}")
            }
        }
    }
}