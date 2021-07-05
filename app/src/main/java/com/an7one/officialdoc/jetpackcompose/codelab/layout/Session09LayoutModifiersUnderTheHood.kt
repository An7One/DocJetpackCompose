/**
 * https://developer.android.com/codelabs/jetpack-compose-layouts?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fcompose%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-layouts#8
 */
package com.an7one.officialdoc.jetpackcompose.codelab.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.*

// how to create a modifier
@Stable
fun Modifier.padding(all: Dp) =
    this then PaddingModifier(start = all, top = all, end = all, bottom = all, rtlAware = true)

// implementation detail
private class PaddingModifier(
    private val start: Dp = 0.dp,
    private val top: Dp = 0.dp,
    private val end: Dp = 0.dp,
    private val bottom: Dp = 0.dp,
    private val rtlAware: Boolean
) : LayoutModifier {

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val horizontal = start.roundToPx() + end.roundToPx()
        val vertical = top.roundToPx() + bottom.roundToPx()

        val placeable = measurable.measure(constraints.offset(-horizontal, -vertical))

        val width = constraints.constrainWidth(placeable.width + horizontal)
        val height = constraints.constrainHeight(placeable.height + vertical)

        return layout(width, height) {
            if (rtlAware)
                placeable.placeRelative(start.roundToPx(), top.roundToPx())
            else
                placeable.place(start.roundToPx(), top.roundToPx())
        }
    }
}

@Composable
fun BodyContentLayoutModifier(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Color.LightGray)
            .size(200.dp)
            .padding(16.dp)
            .horizontalScroll(
                rememberScrollState()
            )
    ) {
        StaggeredGrid {
            for (topic in topics) {
                Chip(text = topic, modifier = Modifier.padding(8.dp))
            }
        }
    }
}