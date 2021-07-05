/**
 * https://developer.android.com/codelabs/jetpack-compose-layouts?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fcompose%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-layouts#7
 */
package com.an7one.officialdoc.jetpackcompose.codelab.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.an7one.officialdoc.jetpackcompose.codelab.layout.ui.LayoutsCodelabTheme

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

@Composable
fun BodyContentComplexLayout(modifier: Modifier = Modifier) {
    StaggeredGrid(modifier = modifier) {
        for (topic in topics) {
            Chip(text = topic, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    nRows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // to measure and position children given constraints logic here
        // to keep track of the width of each row
        val rowWidths = IntArray(nRows) { 0 }

        // to keep track of the max height of each row
        val rowHeights = IntArray(nRows) { 0 }

        // do not constrain child views further
        // to measure them with given constraints
        // list of measured children
        val placeable = measurables.mapIndexed { index, measurable ->
            // to measure each child
            val placeable = measurable.measure(constraints = constraints)

            // to track the width and max height of each row
            val row = index % nRows
            rowWidths[row] += placeable.width
            rowHeights[row] = maxOf(rowHeights[row], placeable.height)

            placeable
        }

        // grid's width is the widest row
        val width =
            rowWidths.maxOrNull()?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))
                ?: constraints.minWidth

        // grid's height is the sum of the tallest element of each row coerced to the height constraints
        val height =
            rowHeights.sumOf { it }.coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // Y of each row, based on the height accumulation of previous rows
        val rowY = IntArray(nRows) { 0 }
        for (row in 1 until nRows) {
            rowY[row] = rowY[row - 1] + rowHeights[row - 1]
        }

        layout(width, height) {
            // x coordinate one has placed up to, per row
            val rowX = IntArray(nRows) { 0 }

            placeable.forEachIndexed { index, placeable ->
                val row = index % nRows

                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row],
                )
                rowX[row] += placeable.width
            }
        }
    }
}

@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun ChipPreview() {
    LayoutsCodelabTheme {
        Chip(text = "Hi there!")
    }
}

@Preview
@Composable
fun ComplexLayoutsCodelabPreview() {
    LayoutsCodelabTheme {
        LayoutsCodelab()
    }
}
