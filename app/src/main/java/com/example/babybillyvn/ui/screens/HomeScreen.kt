package com.example.babybillyvn.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.babybillyvn.data.model.Article
import com.example.babybillyvn.ui.theme.AccentBlue
import com.example.babybillyvn.ui.theme.PrimaryPink
import com.example.babybillyvn.ui.theme.TextDark
import com.example.babybillyvn.ui.theme.TextLight
import com.example.babybillyvn.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val articles by viewModel.articles.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Baby Billy",
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            item { PregnancyInfoSection() }
            item { BabyCardSection() }
            item {
                Text(
                    "Chuyên mục giới thiệu hôm nay",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
            items(articles) { article ->
                ArticleCard(article)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun PregnancyInfoSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("JUN 09", color = TextLight, fontWeight = FontWeight.Bold)
            Text("Em bé", style = MaterialTheme.typography.headlineMedium)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text("40 Tuần 6 Ngày", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            Surface(
                color = AccentBlue.copy(alpha = 0.3f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    "Ngày dự sinh D+6",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun BabyCardSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(PrimaryPink.copy(alpha = 0.5f))
            .padding(20.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://cdn-icons-png.flaticon.com/512/3069/3069172.png"),
            contentDescription = "Baby",
            modifier = Modifier.align(Alignment.Center).size(150.dp)
        )
        Surface(
            modifier = Modifier.align(Alignment.TopStart),
            color = Color.White,
            shape = RoundedCornerShape(15.dp),
            shadowElevation = 2.dp
        ) {
            Text(
                "Con cảm thấy rất run vì\nsắp nhìn thấy bố mẹ.",
                modifier = Modifier.padding(12.dp),
                fontSize = 13.sp,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun ArticleCard(article: Article) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(article.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Cover
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                if (article.isNew) {
                    Surface(
                        color = Color.Red,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            "NEW",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Text(
                    article.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1
                )
                Text(
                    article.description,
                    color = TextLight,
                    fontSize = 12.sp,
                    maxLines = 2
                )
            }
        }
    }
}
