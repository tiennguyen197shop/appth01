package com.example.babybillyvn.data.repository

import android.content.Context
import com.example.babybillyvn.data.model.Article
import com.example.babybillyvn.data.model.BabyWeek
import com.example.babybillyvn.data.model.CommunityPost
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BabyBillyRepository(private val context: Context) {
    private val gson = Gson()

    fun getArticles(): List<Article> {
        val jsonString = context.assets.open("articles.json").bufferedReader().use { it.readText() }
        val listType = object : TypeToken<List<Article>>() {}.type
        return gson.fromJson(jsonString, listType)
    }

    fun getBabyWeeks(): List<BabyWeek> {
        val jsonString = context.assets.open("baby_weeks.json").bufferedReader().use { it.readText() }
        val listType = object : TypeToken<List<BabyWeek>>() {}.type
        return gson.fromJson(jsonString, listType)
    }

    fun getCommunityPosts(): List<CommunityPost> {
        val jsonString = context.assets.open("community_posts.json").bufferedReader().use { it.readText() }
        val listType = object : TypeToken<List<CommunityPost>>() {}.type
        return gson.fromJson(jsonString, listType)
    }
}
