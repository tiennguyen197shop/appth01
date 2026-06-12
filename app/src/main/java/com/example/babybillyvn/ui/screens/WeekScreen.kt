package com.example.babybillyvn.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.babybillyvn.data.model.BabyWeek
import com.example.babybillyvn.ui.theme.AccentBlue
import com.example.babybillyvn.ui.theme.TextDark
import com.example.babybillyvn.ui.theme.TextLight
import com.example.babybillyvn.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekScreen(viewModel: MainViewModel) {
    val babyWeeks by viewModel.babyWeeks.collectAsState()
    var selectedWeek by remember { mutableIntStateOf(40) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var activeFilter by remember { mutableStateOf("Em bé") }

    val currentWeekData = babyWeeks.find { it.week == selectedWeek } ?: babyWeeks.firstOrNull()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Tuần $selectedWeek của bé", fontWeight = FontWeight.Bold)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
            )
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
                        color = AccentBlue
                    )
                }
            ) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                    Text("Bây giờ chúng ta", modifier = Modifier.padding(16.dp))
                }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Text("Xét nghiệm & Triệu chứng", modifier = Modifier.padding(16.dp))
                }
            }

            if (selectedTab == 0 && currentWeekData != null) {
                NowContent(currentWeekData, activeFilter) { activeFilter = it }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nội dung đang cập nhật")
                }
            }
        }
    }
}

@Composable
fun NowContent(data: BabyWeek, activeFilter: String, onFilterChange: (String) -> Unit) {
    val filters = listOf("Em bé", "Mẹ", "Bố", "Sau khi sinh")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filters) { filter ->
                FilterChip(
                    selected = activeFilter == filter,
                    onClick = { onFilterChange(filter) },
                    label = { Text(filter) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AccentBlue,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Image(
            painter = rememberAsyncImagePainter(data.babyImage),
            contentDescription = null,
            modifier = Modifier.size(180.dp)
        )
        
        Text(
            data.size,
            color = Color(0xFFFFA500),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        val content = when (activeFilter) {
            "Em bé" -> data.description
            "Mẹ" -> data.momInfo
            "Bố" -> data.dadInfo
            "Sau khi sinh" -> data.afterBirth
            else -> data.description
        }

        Text(
            content,
            modifier = Modifier.padding(20.dp),
            lineHeight = 24.sp,
            fontSize = 16.sp
        )
    }
}
