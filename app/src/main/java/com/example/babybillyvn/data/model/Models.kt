package com.example.babybillyvn.data.model

data class Article(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val isNew: Boolean = false
)

data class BabyWeek(
    val week: Int,
    val size: String,
    val description: String,
    val babyImage: String,
    val momInfo: String,
    val dadInfo: String,
    val afterBirth: String
)

data class CommunityPost(
    val id: String,
    val title: String,
    val author: String,
    val stage: String,
    val date: String,
    val views: Int,
    val comments: Int,
    val thumbnail: String,
    val isHot: Boolean = false
)
