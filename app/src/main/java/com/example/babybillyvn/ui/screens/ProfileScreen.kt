package com.example.babybillyvn.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleAvatar
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.babybillyvn.ui.theme.AccentBlue
import com.example.babybillyvn.ui.theme.PrimaryPink
import com.example.babybillyvn.ui.theme.TextDark
import com.example.babybillyvn.ui.theme.TextLight

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ProfileHeader()
        ProfileStats()
        ProfileMenu()
    }
}

@Composable
fun ProfileHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryPink, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
            .padding(top = 60.dp, bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=500"),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleAvatar),
            contentScale = ContentScale.Cover
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Nguyễn Thị Mai", fontWeight = FontWeight.Bold, fontSize = 22.sp)
        Text("Ngày dự sinh: 15/06/2024", color = TextDark.copy(alpha = 0.7f), fontSize = 14.sp)
    }
}

@Composable
fun ProfileStats() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatItem("40 Tuần", "Tuần thai")
        Divider(modifier = Modifier.height(40.dp).width(1.dp), color = Color.LightGray)
        StatItem("6 Ngày", "Ngày còn lại")
    }
}

@Composable
fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(label, color = TextLight, fontSize = 12.sp)
    }
}

@Composable
fun ProfileMenu() {
    val menuItems = listOf(
        Pair(Icons.Outlined.Person, "Hồ sơ"),
        Pair(Icons.Outlined.Book, "Nhật ký thai kỳ"),
        Pair(Icons.Outlined.Article, "Bài viết của tôi"),
        Pair(Icons.Outlined.Settings, "Cài đặt"),
        Pair(Icons.Outlined.Notifications, "Thông báo")
    )

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        menuItems.forEach { (icon, title) ->
            MenuItem(icon, title)
            Divider(modifier = Modifier.padding(start = 56.dp), color = Color.LightGray.copy(alpha = 0.3f))
        }
    }
}

@Composable
fun MenuItem(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(10.dp),
            color = AccentBlue.copy(alpha = 0.2f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = Color.DarkGray, modifier = Modifier.size(20.dp))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextLight)
    }
}
