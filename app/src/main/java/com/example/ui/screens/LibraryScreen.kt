package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.AppConstants
import com.example.data.models.CheatSheetItem
import com.example.data.models.GatTrapWord
import com.example.data.models.VocabTier
import com.example.data.models.VocabWord
import com.example.data.repository.CheatSheetsData
import com.example.data.repository.ExamOverviewData
import com.example.data.repository.ExamSection
import com.example.data.repository.GatTrapsData
import com.example.data.repository.VocabClustersData
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.DarkCardBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.EmeraldPass
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PrimaryAccent
import com.example.ui.theme.RoseAlert
import com.example.ui.viewmodel.GatViewModel

@Composable
fun LibraryScreen(
    viewModel: GatViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    val allVocab = remember { VocabClustersData.completeVocabularyLexicon }
    val allTraps = remember { GatTrapsData.allTraps }
    val cheatSheets = remember { CheatSheetsData.items }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "KNOWLEDGE VAULT",
            style = MaterialTheme.typography.labelSmall,
            color = GoldPrimary,
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "GAT Lexicon & Strategy Guides",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Three Main Tabs
        TabRow(
            selectedTabIndex = uiState.selectedLibrarySubTab,
            containerColor = DarkCardBg,
            contentColor = GoldPrimary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[uiState.selectedLibrarySubTab]),
                    color = GoldPrimary
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, DarkCardBorder, RoundedCornerShape(12.dp))
        ) {
            Tab(
                selected = uiState.selectedLibrarySubTab == 0,
                onClick = { viewModel.setLibrarySubTab(0) },
                text = {
                    Text(
                        "Vocab (3,059)",
                        fontWeight = if (uiState.selectedLibrarySubTab == 0) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 12.sp
                    )
                }
            )
            Tab(
                selected = uiState.selectedLibrarySubTab == 1,
                onClick = { viewModel.setLibrarySubTab(1) },
                text = {
                    Text(
                        "25 Traps",
                        fontWeight = if (uiState.selectedLibrarySubTab == 1) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 12.sp
                    )
                }
            )
            Tab(
                selected = uiState.selectedLibrarySubTab == 2,
                onClick = { viewModel.setLibrarySubTab(2) },
                text = {
                    Text(
                        "Cheat Sheets",
                        fontWeight = if (uiState.selectedLibrarySubTab == 2) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 11.sp
                    )
                }
            )
            Tab(
                selected = uiState.selectedLibrarySubTab == 3,
                onClick = { viewModel.setLibrarySubTab(3) },
                text = {
                    Text(
                        "Overview",
                        fontWeight = if (uiState.selectedLibrarySubTab == 3) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 11.sp
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (uiState.selectedLibrarySubTab) {
            0 -> {
                // 3,059 Vocab Lexicon View
                // Search Input
                OutlinedTextField(
                    value = uiState.vocabSearchQuery,
                    onValueChange = { viewModel.setVocabSearchQuery(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("vocab_search_input"),
                    placeholder = {
                        Text(
                            "Search 3,059 words, definitions, etymologies...",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 13.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    trailingIcon = {
                        if (uiState.vocabSearchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.setVocabSearchQuery("") }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = DarkCardBg,
                        unfocusedContainerColor = DarkCardBg,
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = DarkCardBorder
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Tier Filter Pills
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val tierOptions: List<Pair<VocabTier?, String>> = listOf(
                        null to "All Tiers",
                        VocabTier.ESSENTIAL to "Tier 1",
                        VocabTier.HIGH_YIELD to "Tier 2",
                        VocabTier.EXPERT to "Tier 3"
                    )

                    tierOptions.forEach { pair ->
                        val tier = pair.first
                        val label = pair.second
                        val isSelected = uiState.vocabTierFilter == tier
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { viewModel.setVocabTierFilter(tier) },
                            color = if (isSelected) GoldPrimary else DarkCardBg,
                            shape = RoundedCornerShape(16.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) GoldPrimary else DarkCardBorder)
                        ) {
                            Box(
                                modifier = Modifier.padding(vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSelected) Color(0xFF0F0E17) else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                val filteredVocab = remember(uiState.vocabSearchQuery, uiState.vocabTierFilter) {
                    allVocab.filter { word ->
                        val matchesTier = uiState.vocabTierFilter == null || word.tier == uiState.vocabTierFilter
                        val matchesQuery = uiState.vocabSearchQuery.isBlank() ||
                                word.word.contains(uiState.vocabSearchQuery, ignoreCase = true) ||
                                word.definition.contains(uiState.vocabSearchQuery, ignoreCase = true) ||
                                word.etymology.contains(uiState.vocabSearchQuery, ignoreCase = true) ||
                                word.sampleSentence.contains(uiState.vocabSearchQuery, ignoreCase = true)
                        matchesTier && matchesQuery
                    }
                }

                Text(
                    text = "Showing ${filteredVocab.size} Words",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(6.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredVocab.take(150), key = { it.id }) { word ->
                        VocabItemRow(word = word)
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }

            1 -> {
                // 25 Decoy Traps View
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Text(
                            text = "25 high-frequency decoy patterns tested on the Ethiopian GAT.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    items(allTraps, key = { it.id }) { trap ->
                        TrapCardItem(trap = trap)
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }

            2 -> {
                // Cheat Sheets View
                var selectedCategory by remember { mutableStateOf("All") }
                val categories = listOf("All", "Quantitative", "Verbal", "Analytical")

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { cat ->
                        val isSelected = selectedCategory == cat
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedCategory = cat },
                            color = if (isSelected) GoldPrimary else DarkCardBg,
                            shape = RoundedCornerShape(16.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) GoldPrimary else DarkCardBorder)
                        ) {
                            Box(
                                modifier = Modifier.padding(vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = cat,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSelected) Color(0xFF0F0E17) else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                val filteredSheets = remember(selectedCategory) {
                    if (selectedCategory == "All") cheatSheets
                    else cheatSheets.filter { it.category.equals(selectedCategory, ignoreCase = true) }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filteredSheets, key = { it.id }) { sheet ->
                        CheatSheetCard(sheet = sheet)
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }

            3 -> {
                // Official Exam Overview & Syllabus Guide
                ExamOverviewView()
            }
        }
    }
}

@Composable
private fun VocabItemRow(word: VocabWord) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = word.word,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "(${word.partOfSpeech})",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    )
                }

                Surface(
                    color = Color(0x22FFFFFF),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "T${word.tier.level}",
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = PrimaryAccent,
                        fontSize = 10.sp
                    )
                }
            }

            Text(
                text = word.definition,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier.padding(top = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (word.phonetics.isNotEmpty()) {
                        Text(
                            text = "Pronunciation: ${word.phonetics}",
                            style = MaterialTheme.typography.bodySmall,
                            color = PrimaryAccent
                        )
                    }

                    if (word.sampleSentence.isNotEmpty()) {
                        Text(
                            text = "\"${word.sampleSentence}\"",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (word.etymology.isNotEmpty()) {
                        Text(
                            text = "🏛️ Etymology: ${word.etymology}",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyanAccent
                        )
                    }

                    if (word.synonyms.isNotEmpty()) {
                        Text(
                            text = "Synonyms: ${word.synonyms.take(4).joinToString(", ")}",
                            style = MaterialTheme.typography.labelSmall,
                            color = EmeraldPass
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TrapCardItem(trap: GatTrapWord) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = RoseAlert,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "TRAP ${trap.id}: ${trap.word}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = RoseAlert
                    )
                }

                Surface(
                    color = RoseAlert.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "Decoy: ${trap.decoyOption}",
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = RoseAlert,
                        fontSize = 10.sp
                    )
                }
            }

            Text(
                text = trap.whyDeceptive,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier.padding(top = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        color = EmeraldPass.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "Correct Meaning & Strategy:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldPass
                            )
                            Text(
                                text = trap.realDefinition,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Text(
                        text = "Mnemonic: ${trap.mnemonic}",
                        style = MaterialTheme.typography.labelSmall,
                        color = CyanAccent
                    )

                    Text(
                        text = "Etymology Anchor: ${trap.etymologyAnchor}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun CheatSheetCard(sheet: CheatSheetItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = sheet.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = GoldPrimary
                )

                Surface(
                    color = Color(0x22FFFFFF),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = sheet.category,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = PrimaryAccent,
                        fontSize = 10.sp
                    )
                }
            }

            Surface(
                color = Color(0x1800D2FF),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = sheet.formulaOrConcept,
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = CyanAccent
                )
            }

            Text(
                text = sheet.explanation,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (sheet.sampleApplication.isNotEmpty()) {
                Text(
                    text = "Example: ${sheet.sampleApplication}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun ExamOverviewView() {
    val sections = remember { ExamOverviewData.sections }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            // Header Banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCardBg),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "OFFICIAL SPECIFICATION",
                            style = MaterialTheme.typography.labelSmall,
                            color = GoldPrimary,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )
                        Surface(
                            color = GoldPrimary.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "12 Sections",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = GoldPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Text(
                        text = "National Graduate Admission Test (NGAT / GAT)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = "Complete guide to exam structure, syllabus weights, eligibility, scoring thresholds, and strategic roadmaps for Ethiopian universities.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            // Prominent Note on Question Count
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0x1800D2FF)),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyanAccent.copy(alpha = 0.4f))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "📌",
                        fontSize = 18.sp
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Note on Structure & Question Count",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = CyanAccent
                        )
                        Text(
                            text = ExamOverviewData.noteOnQuestionCount,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        items(sections, key = { it.number }) { section ->
            ExamSectionCard(section = section)
        }

        item {
            // About Developer & App Card
            AboutAppCard()
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ExamSectionCard(section: ExamSection) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        color = PrimaryAccent.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "Part ${section.number}",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryAccent,
                            fontSize = 11.sp
                        )
                    }
                    Text(
                        text = section.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Text(
                    text = if (isExpanded) "▲" else "▼",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }

            Text(
                text = section.summary,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    section.content.forEach { paragraph ->
                        Text(
                            text = paragraph,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 18.sp
                        )
                    }

                    if (section.keyPoints.isNotEmpty()) {
                        Surface(
                            color = Color(0x1800E599),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "Key Takeaways:",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldPass
                                )
                                section.keyPoints.forEach { point ->
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text(text = "•", color = EmeraldPass, fontSize = 12.sp)
                                        Text(
                                            text = point,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AboutAppCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ABOUT THE APPLICATION",
                    style = MaterialTheme.typography.labelSmall,
                    color = GoldPrimary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = "v2.0 Full Suite",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = AppConstants.APP_NAME,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = AppConstants.DEVELOPER_SUBTITLE,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = CyanAccent
            )

            Text(
                text = "Built to provide Ethiopian graduate school aspirants with a comprehensive, rigorous, and offline-first preparation platform featuring an 80-level curriculum, 950 practice questions across 11 core chapters, 3,362 categorized vocabulary entries across 244 semantic clusters, 25 GAT trap patterns, and real-time timed mock exam simulations.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp
            )
        }
    }
}
