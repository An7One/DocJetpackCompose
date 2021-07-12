package com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

/**
 * a [MaterialTTheme] for Rally
 */
@Composable
fun RallyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = ColorPalette,
        typography = Typography,
        content = content
    )
}

/**
 * a theme overlay used for dialogs
 */
@Composable
fun RallyDialogThemeOverlay(content: @Composable () -> Unit) {
    // Rally always is of dark theme
    val colorsDialog = darkColors(
        primary = Color.White,
        surface = Color.White.copy(alpha = 0.12f).compositeOver(Color.Black),
        onSurface = Color.White
    )

    // to copy the current [Typography] and replace some text styles with this theme
    val typographyCur = MaterialTheme.typography
    val typographyDialog = typographyCur.copy(
        body2 = typographyCur.body1.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            letterSpacing = 1.sp
        ),
        button = typographyCur.button.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.2.em
        )
    )

    MaterialTheme(
        colors = colorsDialog,
        typography = typographyDialog,
        content = content
    )
}