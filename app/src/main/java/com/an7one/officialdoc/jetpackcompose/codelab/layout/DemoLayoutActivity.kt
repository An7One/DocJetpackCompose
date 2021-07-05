package com.an7one.officialdoc.jetpackcompose.codelab.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.an7one.officialdoc.jetpackcompose.codelab.layout.ui.LayoutsCodelabTheme
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.launch

class DemoLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 03. Modifiers
            // PhotographerCard()

            /*
            // 05. Material Components
            LayoutsCodelabTheme {
                LayoutsCodelab()
            }*/

            /*
            // 06. Working with Lists
            // SimpleList()
            // LazyList()
            // ImageList()
            ScrollingList()
             */

            /*
            // 07.Create One's Own Custom Layout
            BodyContentCustomLayout()
             */

            // 08 Complex Custom Layout
            BodyContentComplexLayout()
        }
    }
}

// <editor-fold desc="03Modifiers">
@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(
        modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = {})
            .padding(16.dp)
    )
    {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {

        }

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text("Alfred Sisley", fontWeight = FontWeight.Bold)
            // LocalContentAlpha is defining opacity level of its children
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Preview
@Composable
fun PhotographerCardPreview() {
    LayoutsCodelabTheme {
        PhotographerCard()
    }
}
// </editor-fold>

// <editor-fold desc="05MaterialComponents">
@Composable
fun LayoutsCodelab() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutsCodelab")
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "Hi there!")
        Text(text = "Thanks for going through the Layouts codelab")
    }
}

@Preview
@Composable
fun LayoutsCodelabPreview() {
    LayoutsCodelabTheme {
        LayoutsCodelab()
    }
}
// </editor-fold>

// <editor-fold desc="06WorkingWithLists">
@Composable
fun SimpleList() {
    // to save the scrolling position with this state that can also
    // be used to programmatically scroll the list
    val scrollState = rememberScrollState()

    Column(Modifier.verticalScroll(scrollState)) {
        repeat(100) {
            Text("Item #$it")
        }
    }
}

@Composable
fun LazyList() {
    // to save the scrolling position with this state that can also
    // be used to programmatically scroll the list
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(100) {
            Text("Item #$it")
        }
    }
}

@Composable
fun ScrollingList() {
    val listSize = 100
    // to save the scrolling position with this state
    val scrollState = rememberLazyListState()
    // to save the coroutine scope where the animated scroll will be executed
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row {
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text(text = "Scroll to the top")
            }

            Button(onClick = {
                coroutineScope.launch {
                    // listSize - 1 is the last index of the list
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text(text = "Scroll to the end")
            }
        }

        LazyColumn(state = scrollState) {
            items(listSize) {
                ImageListItem(index = it)
            }
        }
    }
}

@Composable
fun ImageList() {
    // to save the scrolling position with this state
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(100) {
            ImageListItem(index = it)
        }
    }
}

@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberCoilPainter(
                request = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text("Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}
// </editor-fold>

// <editor-fold desc="07. Create One's Own Layout">
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
// </editor-fold>

// <editor-fold desc="08. Complex Custom Layout">
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
// </editor-fold>