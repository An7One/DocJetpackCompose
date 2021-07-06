/**
 * https://developer.android.com/codelabs/jetpack-compose-layouts?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fcompose%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-layouts#6
 */
package com.an7one.officialdoc.jetpackcompose.codelab.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.an7one.officialdoc.jetpackcompose.codelab.layout.ui.LayoutsCodelabTheme

@Composable
fun BodyContentCustomLayout(modifier: Modifier = Modifier) {
    MyOwnColumn(modifier.padding(8.dp)) {
        Text(text = "MyOwnColumn")
        Text(text = "places items")
        Text(text = "vertically")
        Text(text = "We've done it by hand!")
    }
}

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    // custom layout attributes
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // measure and position children given constraints logic here
        // do not constrain child views further, to measure them with given constraints
        // list of measured children
        val placeable = measurables.map { measurable ->
            // to measure each child
            measurable.measure(constraints = constraints)
        }

        // to track the y coordinate that children have been up to
        var yPosition = 0

        // to set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // to place children in the parent layout
            placeable.forEach { placeable ->
                // the position item on the screen
                placeable.placeRelative(x = 0, y = yPosition)

                // to record the y coordinate placed up to
                yPosition += placeable.height
            }
        }
    }
}

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        // to check whether the composable has a first baseline
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        // the height of the composable with padding - first baseline
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            // where the composable gets placed
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    LayoutsCodelabTheme {
        Text(text = "Hi there!", Modifier.firstBaselineToTop(32.dp))
    }
}

@Preview
@Composable
fun TextWithNormalPaddingPreview() {
    LayoutsCodelabTheme {
        Text(text = "Hi there!", Modifier.padding(top = 32.dp))
    }
}