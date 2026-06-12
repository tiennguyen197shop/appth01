package com.example.babybillyvn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.babybillyvn.data.model.Article
import com.example.babybillyvn.data.model.BabyWeek
import com.example.babybillyvn.data.model.CommunityPost
import com.example.babybillyvn.data.repository.BabyBillyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: BabyBillyRepository) : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    private val _babyWeeks = MutableStateFlow<List<BabyWeek>>(emptyList())
    val babyWeeks: StateFlow<List<BabyWeek>> = _babyWeeks

    private val _communityPosts = MutableStateFlow<List<CommunityPost>>(emptyList())
    val communityPosts: StateFlow<List<CommunityPost>> = _communityPosts

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _articles.value = repository.getArticles()
            _babyWeeks.value = repository.getBabyWeeks()
            _communityPosts.value = repository.getCommunityPosts()
        }
    }
}
