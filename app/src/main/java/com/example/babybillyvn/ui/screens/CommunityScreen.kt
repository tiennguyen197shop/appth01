package com.example.babybillyvn.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleAvatar
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.babybillyvn.data.model.CommunityPost
import com.example.babybillyvn.ui.theme.AccentBlue
import com.example.babybillyvn.ui.theme.PrimaryPink
import com.example.babybillyvn.ui.theme.TextDark
import com.example.babybillyvn.ui.theme.TextLight
import com.example.babybillyvn.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(viewModel: MainViewModel) {
    val posts by viewModel.communityPosts.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Góc chia sẻ Baby Billy", fontWeight = FontWeight.Bold) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = PrimaryPink,
                contentColor = TextDark
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Post")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = TextDark,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = PrimaryPink
                    )
                }
            ) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                    Text("Toàn bộ", modifier = Modifier.padding(16.dp))
                }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Text("Hot", modifier = Modifier.padding(16.dp))
                }
            }

            val filteredPosts = if (selectedTab == 1) posts.filter { it.isHot } else posts

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredPosts) { post ->
                    CommunityPostCard(post)
                }
            }
        }
    }
}

@Composable
fun CommunityPostCard(post: CommunityPost) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(post.thumbnail),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Cover
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(post.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(24.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = AccentBlue
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(post.author.take(1), color = Color.White, fontSize = 10.sp)
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(post.author, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(post.stage, color = Color.Blue.copy(alpha = 0.6f), fontSize = 12.sp)
                }
                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.5f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.RemoveRedEye, contentDescription = null, modifier = Modifier.size(16.dp), tint = TextLight)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${post.views}", color = TextLight, fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = null, modifier = Modifier.size(16.dp), tint = TextLight)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${post.comments}", color = TextLight, fontSize = 12.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(post.date, color = TextLight, fontSize = 12.sp)
                }
            }
        }
    }
}
