package com.example.tiptime

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiptime.ui.theme.ThemeOption
import com.example.tiptime.ui.theme.TipTimeTheme
import com.example.tiptime.ui.theme.tipTimeIconButtonColors
import com.example.tiptime.ui.theme.tipTimeSwitchColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    darkMode: Boolean,
    onDarkModeChanged: (Boolean) -> Unit,
    themeOption: ThemeOption,
    onThemeOptionChanged: (ThemeOption) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings)) },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        colors = tipTimeIconButtonColors()
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .statusBarsPadding()
                .padding(horizontal = 40.dp)
                .verticalScroll(rememberScrollState())
                .safeDrawingPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Dark Mode Switch
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.dark_mode))
                Switch(
                    checked = darkMode,
                    onCheckedChange = onDarkModeChanged,
                    colors = tipTimeSwitchColors()
                )
            }
            // Theme Selection
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.customize_theme),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                // 2x2 Grid layout for themes
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // First row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ThemeOption.entries.toTypedArray().take(2).forEach { option ->
                            ThemeOptionItem(
                                option = option,
                                isSelected = themeOption == option,
                                onSelect = onThemeOptionChanged,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    // Second row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ThemeOption.entries.drop(2).forEach { option ->
                            ThemeOptionItem(
                                option = option,
                                isSelected = themeOption == option,
                                onSelect = onThemeOptionChanged,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeColorRow(
    themeOption: ThemeOption,
    modifier: Modifier = Modifier
) {
    val primaryColor = when (themeOption) {
        ThemeOption.BLUE -> Color(0xFF0061A4)
        ThemeOption.PURPLE -> Color(0xFF6750A4)
        ThemeOption.GREEN -> Color(0xFF2E7D32)
        ThemeOption.YELLOW -> Color(0xFFF57F17)
    }
    
    val secondaryColor = when (themeOption) {
        ThemeOption.BLUE -> Color(0xFFD1E4FF)
        ThemeOption.PURPLE -> Color(0xFFEADDFF)
        ThemeOption.GREEN -> Color(0xFFB1F8B1)
        ThemeOption.YELLOW -> Color(0xFFFFE0B2)
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(primaryColor)
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp))
        )
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(secondaryColor)
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp))
        )
    }
}

@Composable
fun ThemeOptionItem(
    option: ThemeOption,
    isSelected: Boolean,
    onSelect: (ThemeOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ThemeColorRow(option, Modifier)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = { onSelect(option) }
            )
            Text(
                text = option.name.lowercase().replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 2.dp)
            )
        }
    }
}

@Preview(name = "Settings Screen - Dark Mode", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsScreenDarkPreview() {
    TipTimeTheme {
        SettingsScreen(
            darkMode = true,
            onDarkModeChanged = {},
            themeOption = ThemeOption.BLUE,
            onThemeOptionChanged = {},
            onBackClick = {},
            modifier = Modifier
        )
    }
}

@Preview(name = "Settings Screen - All Themes", showBackground = true)
@Composable
fun SettingsScreenAllThemesPreview() {
    Column {
        ThemeOption.entries.forEach { theme ->
            TipTimeTheme(themeOption = theme) {
                SettingsScreen(
                    darkMode = false,
                    onDarkModeChanged = {},
                    themeOption = theme,
                    onThemeOptionChanged = {},
                    onBackClick = {},
                    modifier = Modifier
                )
            }
        }
    }
}