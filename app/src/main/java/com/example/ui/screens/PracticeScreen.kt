package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.ChapterType
import com.example.data.models.JourneyLevel
import com.example.data.repository.JourneyRepository
import com.example.data.repository.QuestionsRepository
import com.example.ui.theme.AmberBoss
import com.example.ui.theme.DarkCardBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.EmeraldPass
import com.example.ui.theme.GoldPrimary
import com.example.ui.viewmodel.GatViewModel

@Composable
fun PracticeScreen(
    viewModel: GatViewModel
) {
    var selectedSection by remember { mutableStateOf(0) } // 0: 80-Level Journey, 1: 11 Chapters
    var selectedTierFilter by remember { mutableStateOf(0) } // 0: All, 1: Tier 1, 2: Tier 2, 3: Tier 3, 4: Tier 4

    val levelsProgress by viewModel.levelProgressList.collectAsState()
    val progressMap = remember(levelsProgress) { levelsProgress.associateBy { it.levelNumber } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "PRACTICE HUB",
            style = MaterialTheme.typography.labelSmall,
            color = GoldPrimary,
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Targeted Questions & Journey",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Clean Two-Option Tab Switcher
        TabRow(
            selectedTabIndex = selectedSection,
            containerColor = DarkCardBg,
            contentColor = GoldPrimary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedSection]),
                    color = GoldPrimary
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, DarkCardBorder, RoundedCornerShape(12.dp))
        ) {
            Tab(
                selected = selectedSection == 0,
                onClick = { selectedSection = 0 },
                text = {
                    Text(
                        "80-Level Journey",
                        fontWeight = if (selectedSection == 0) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 13.sp
                    )
                }
            )
            Tab(
                selected = selectedSection == 1,
                onClick = { selectedSection = 1 },
                text = {
                    Text(
                        "11 GAT Chapters",
                        fontWeight = if (selectedSection == 1) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 13.sp
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (selectedSection) {
            0 -> {
                // Tier filter chips
                ScrollableTabRow(
                    selectedTabIndex = selectedTierFilter,
                    containerColor = Color.Transparent,
                    edgePadding = 0.dp,
                    divider = {},
                    indicator = {}
                ) {
                    val tierNames = listOf("All (1-80)", "T1: Foundation (1-20)", "T2: Intermediate (21-40)", "T3: Advanced (41-60)", "T4: Grandmaster (61-80)")
                    tierNames.forEachIndexed { idx, name ->
                        val isSelected = selectedTierFilter == idx
                        Surface(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clickable { selectedTierFilter = idx },
                            color = if (isSelected) GoldPrimary else DarkCardBg,
                            shape = RoundedCornerShape(20.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) GoldPrimary else DarkCardBorder)
                        ) {
                            Text(
                                text = name,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSelected) Color(0xFF0F0E17) else MaterialTheme.colorScheme.onSurface,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                val allLevels = remember { JourneyRepository.allLevels }
                val filteredLevels = remember(selectedTierFilter) {
                    when (selectedTierFilter) {
                        1 -> allLevels.filter { it.levelNumber in 1..20 }
                        2 -> allLevels.filter { it.levelNumber in 21..40 }
                        3 -> allLevels.filter { it.levelNumber in 41..60 }
                        4 -> allLevels.filter { it.levelNumber in 61..80 }
                        else -> allLevels
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filteredLevels, key = { it.levelNumber }) { lvl ->
                        val prog = progressMap[lvl.levelNumber]
                        val isUnlocked = prog?.isUnlocked ?: (lvl.levelNumber == 1)
                        val stars = prog?.stars ?: 0

                        JourneyLevelRowItem(
                            level = lvl,
                            isUnlocked = isUnlocked,
                            stars = stars,
                            onClick = {
                                if (isUnlocked) {
                                    viewModel.startLevelQuiz(lvl.levelNumber)
                                }
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }

            1 -> {
                // 11 Chapters Practice View
                val allChapters = remember { ChapterType.values() }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(allChapters, key = { it.id }) { chapter ->
                        val questionsCount = QuestionsRepository.getQuestionsForChapter(chapter.id).size
                        ChapterPracticeCard(
                            chapter = chapter,
                            questionCount = questionsCount,
                            onStart = { viewModel.startChapterDrill(chapter.id) }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun JourneyLevelRowItem(
    level: JourneyLevel,
    isUnlocked: Boolean,
    stars: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isUnlocked) { onClick() }
            .testTag("level_item_${level.levelNumber}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) DarkCardBg else Color(0xFF13121C)
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (level.isBossLevel) AmberBoss.copy(alpha = 0.6f) else if (isUnlocked) DarkCardBorder else Color(0x22FFFFFF)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Level Number Circle
                Surface(
                    color = if (level.isBossLevel) AmberBoss.copy(alpha = 0.2f)
                    else if (isUnlocked) (if (stars > 0) EmeraldPass.copy(alpha = 0.15f) else GoldPrimary.copy(alpha = 0.15f))
                    else Color(0x11FFFFFF),
                    shape = CircleShape,
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (!isUnlocked) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Locked",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                                modifier = Modifier.size(18.dp)
                            )
                        } else if (stars > 0) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Completed",
                                tint = EmeraldPass,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Text(
                                text = "${level.levelNumber}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (level.isBossLevel) AmberBoss else GoldPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = level.title,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isUnlocked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                        )
                        if (level.isBossLevel) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = AmberBoss.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "BOSS",
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AmberBoss,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Text(
                        text = "${level.tierTitle} • ${level.questionIds.size} Questions",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    )
                }
            }

            // Status or Play action
            if (isUnlocked) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (stars > 0) {
                        Surface(
                            color = EmeraldPass.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "Completed",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = EmeraldPass,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Start",
                        tint = GoldPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChapterPracticeCard(
    chapter: ChapterType,
    questionCount: Int,
    onStart: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onStart() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = GoldPrimary.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.size(42.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "CH\n${chapter.id}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            textAlign = TextAlign.Center,
                            lineHeight = 12.sp,
                            fontSize = 10.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = chapter.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${chapter.subtitle} • $questionCount Questions",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    )
                }
            }

            Button(
                onClick = onStart,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldPrimary,
                    contentColor = Color(0xFF0F0E17)
                ),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Drill",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
