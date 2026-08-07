package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.ChapterType
import com.example.data.repository.QuestionsRepository
import com.example.ui.theme.AmberBoss
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.DarkCardBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.EmeraldPass
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.IndigoSurface
import com.example.ui.theme.PrimaryAccent
import com.example.ui.theme.RoseAlert
import com.example.ui.viewmodel.GatViewModel

@Composable
fun ProgressScreen(
    viewModel: GatViewModel
) {
    val readiness by viewModel.examReadiness.collectAsState()
    val levels by viewModel.levelProgressList.collectAsState()
    val stats by viewModel.userStats.collectAsState()
    val attempts by viewModel.allAttempts.collectAsState()

    val completedLevels = levels.count { it.stars > 0 }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "PERFORMANCE ANALYTICS",
                style = MaterialTheme.typography.labelSmall,
                color = GoldPrimary,
                letterSpacing = 2.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Exam Readiness & Stats",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Hero Estimated Percentile Card
        item {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("readiness_hero_card"),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = IndigoSurface),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFF24224A), Color(0xFF141328))
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            color = Color(0x33D4AF37),
                            shape = RoundedCornerShape(20.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = "ESTIMATED GAT PERCENTILE",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = GoldPrimary,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }

                        Text(
                            text = "${readiness.estimatedGatPercentile}th",
                            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 44.sp),
                            fontWeight = FontWeight.Black,
                            color = GoldPrimary
                        )

                        Text(
                            text = if (readiness.estimatedGatPercentile >= 80) "Competitive Range for All Graduate Programs"
                            else "On Track • Practice Decoy Traps & Timed Drills",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // 4 Core Metrics (2x2 Grid)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricStatCard(
                        modifier = Modifier.weight(1f),
                        title = "Accuracy",
                        value = "${(readiness.accuracyRate * 100).toInt()}%",
                        icon = Icons.Default.CheckCircle,
                        color = EmeraldPass
                    )
                    MetricStatCard(
                        modifier = Modifier.weight(1f),
                        title = "Solved",
                        value = "${readiness.totalQuestionsAttempted} Qs",
                        icon = Icons.Default.Timeline,
                        color = PrimaryAccent
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricStatCard(
                        modifier = Modifier.weight(1f),
                        title = "Levels Cleared",
                        value = "$completedLevels / 80",
                        icon = Icons.Default.School,
                        color = GoldPrimary
                    )
                    MetricStatCard(
                        modifier = Modifier.weight(1f),
                        title = "Vocab Mastered",
                        value = "${readiness.vocabMastered} Words",
                        icon = Icons.Default.Psychology,
                        color = CyanAccent
                    )
                }
            }
        }

        // Section Breakdown (Quant, Verbal, Analytical)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "SECTION PERFORMANCE",
                        style = MaterialTheme.typography.labelSmall,
                        color = GoldPrimary,
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.Bold
                    )

                    SectionProgressRow(
                        section = "Quantitative Reasoning",
                        score = readiness.quantScore,
                        color = CyanAccent
                    )

                    SectionProgressRow(
                        section = "Verbal Reasoning & Vocab",
                        score = readiness.verbalScore,
                        color = PrimaryAccent
                    )

                    SectionProgressRow(
                        section = "Analytical & Critical Thinking",
                        score = readiness.analyticalScore,
                        color = AmberBoss
                    )
                }
            }
        }

        // Chapter Breakdown
        item {
            Text(
                text = "CHAPTER-BY-CHAPTER ACCURACY",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 1.5.sp,
                fontWeight = FontWeight.Bold
            )
        }

        items(ChapterType.values()) { chapter ->
            val chapterAttempts = remember(attempts) {
                attempts.filter { att ->
                    val q = QuestionsRepository.getQuestionById(att.questionId)
                    q?.chapterId == chapter.id
                }
            }
            val acc = if (chapterAttempts.isEmpty()) 75 else (chapterAttempts.count { it.isCorrect } * 100 / chapterAttempts.size)

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "CH ${chapter.id}: ${chapter.title}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { acc / 100f },
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(5.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = if (acc >= 75) EmeraldPass else RoseAlert,
                            trackColor = Color(0x22FFFFFF)
                        )
                    }

                    Text(
                        text = "$acc%",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (acc >= 75) EmeraldPass else RoseAlert
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun MetricStatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = color.copy(alpha = 0.15f),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun SectionProgressRow(
    section: String,
    score: Int,
    color: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = section,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$score%",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }

        LinearProgressIndicator(
            progress = { score / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = Color(0x22FFFFFF)
        )
    }
}
