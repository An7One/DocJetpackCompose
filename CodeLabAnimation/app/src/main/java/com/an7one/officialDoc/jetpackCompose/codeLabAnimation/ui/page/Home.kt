package com.an7one.officialDoc.jetpackCompose.codeLabAnimation.ui.page

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.an7one.officialDoc.jetpackCompose.codeLabAnimation.R
import com.an7one.officialDoc.jetpackCompose.codeLabAnimation.ui.theme.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

private enum class TabPage {
    Home,
    Work,
}

@Composable
fun Home() {
    val allTasks = stringArrayResource(id = R.array.tasks)
    val allTopics = stringArrayResource(id = R.array.topics).toList()

    var tabPage by remember { mutableStateOf(TabPage.Home) }

    var weatherLoading by remember { mutableStateOf(false) }

    val tasks = remember { mutableStateListOf(*allTasks) }

    var expandedTopic by remember { mutableStateOf<String?>(null) }

    var editMessageShown by remember { mutableStateOf(false) }

    suspend fun loadWeather() {
        if (!weatherLoading) {
            weatherLoading = true
            delay(3000L)
            weatherLoading = false
        }
    }

    suspend fun showEditMessage() {
        if (!editMessageShown) {
            editMessageShown = true
            delay(3000L)
            editMessageShown = false
        }
    }

    LaunchedEffect(Unit) {
        loadWeather()
    }

    val lazyListState = rememberLazyListState()

    val backgroundColor by animateColorAsState(if (tabPage == TabPage.Home) Purple100 else Green300)

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TabBarHome(
                backgroundColor = backgroundColor,
                tabPage = tabPage,
                onTabSelected = { tabPage = it }
            )
        },
        backgroundColor = backgroundColor,
        floatingActionButton = {
            FABHome(
                extended = lazyListState.isScrollingUp(),
                onClick = {
                    coroutineScope.launch {
                        showEditMessage()
                    }
                }
            )
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
            state = lazyListState
        ) {

            item { Header(title = stringResource(id = R.string.weather)) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 2.dp
                ) {
                    if (weatherLoading) {
                        RowLoading()
                    } else {
                        RowWeather(
                            onRefresh = {
                                coroutineScope.launch {
                                    loadWeather()
                                }
                            }
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }

            // topics
            item { Header(title = stringResource(id = R.string.topics)) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            items(allTopics) { topic ->
                RowTopic(
                    topic = topic,
                    expanded = expandedTopic == topic,
                    onClick = {
                        expandedTopic = if (expandedTopic == topic) null else topic
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }

            // tasks
            item { Header(title = stringResource(id = R.string.tasks)) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            if (tasks.isEmpty()) {
                item {
                    TextButton(
                        onClick = {
                            tasks.clear();
                            tasks.addAll(allTasks)
                        }) {
                        Text(stringResource(id = R.string.add_tasks))
                    }
                }
            }
            items(count = tasks.size) { i ->
                tasks.getOrNull(i)?.let { task ->
                    key(task) {
                        RowTask(task = task,
                            onRemove = {
                                tasks.remove(task)
                            }
                        )
                    }
                }
            }
        }
        EditMessage(shown = editMessageShown)
    }
}

/**
 * Shows the floating action button.
 *
 * @param extended Whether the tab should be shown in its expanded state.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun FABHome(
    extended: Boolean,
    onClick: () -> Unit
) {
    // Use `FloatingActionButton` rather than `ExtendedFloatingActionButton` for full control on
    // how it should animate.
    FloatingActionButton(
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
            )

            // Toggle the visibility of the content with animation.
            AnimatedVisibility(extended) {
                Text(
                    text = stringResource(id = R.string.edit),
                    modifier = Modifier
                        .padding(start = 8.dp, top = 3.dp)
                )
            }
        }
    }
}

/**
 * to show a message that the edit feature is not available
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun EditMessage(shown: Boolean) {
    AnimatedVisibility(
        visible = shown,
        enter = slideInVertically(
            // to enter by sliding down from offset -fullHeight to 0
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            // to exit by sliding up from offset 0 to -fullHeight
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.secondary,
            elevation = 4.dp
        ) {
            Text(
                text = stringResource(id = R.string.edit_message),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

/**
 * to return whether the lazy list is currently scrolling up.
 */
@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }

    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

/**
 * to show the header label
 *
 * @param title the title to be shown
 */
@Composable
private fun Header(
    title: String
) {
    Text(
        text = title,
        modifier = Modifier.semantics { heading() },
        style = MaterialTheme.typography.h5
    )
}

/**
 * Shows a row for one topic.
 *
 * @param topic The topic title.
 * @param expanded Whether the row should be shown expanded with the topic body.
 * @param onClick Called when the row is clicked.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RowTopic(
    topic: String,
    expanded: Boolean,
    onClick: () -> Unit
) {

    SpacerRowTopic(visible = expanded)

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 2.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize()
        ) {
            Row {
                Icon(imageVector = Icons.Default.Info, contentDescription = null)

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = topic,
                    style = MaterialTheme.typography.body1
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(id = R.string.lorem_ipsum),
                    textAlign = TextAlign.Justify,
                )
            }
        }
    }
    SpacerRowTopic(visible = expanded)
}

/**
 * to show a separator for topics
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SpacerRowTopic(visible: Boolean) {
    AnimatedVisibility(visible = visible) {
        Spacer(modifier = Modifier.height(8.dp))
    }
}

/**
 * to show the bar that holds 2 tabs
 *
 * @param backgroundColor The background color for the bar.
 * @param tabPage The [TabPage] that is currently selected.
 * @param onTabSelected Called when the tab is switched.
 */
@Composable
private fun TabBarHome(
    backgroundColor: Color,
    tabPage: TabPage,
    onTabSelected: (tabPage: TabPage) -> Unit
) {
    TabRow(
        selectedTabIndex = tabPage.ordinal,
        backgroundColor = backgroundColor,
        indicator = { tabPositions ->
            IndicatorTabHome(
                tabPositions = tabPositions,
                tabPage = tabPage
            )
        }
    )
    {
        TabHome(
            icon = Icons.Default.Home,
            title = stringResource(R.string.home),
            onClick = {
                onTabSelected(TabPage.Home)
            })

        TabHome(
            icon = Icons.Default.AccountBox,
            title = stringResource(R.string.work),
            onClick = {
                onTabSelected(TabPage.Work)
            }
        )
    }
}

/**
 * to show an indicator for the tab
 *
 * @param tabPositions the list of [TabPosition]s from a [TabRow]
 * @param tabPage the [TabPage] that is currently selected
 */
@Composable
private fun IndicatorTabHome(
    tabPositions: List<TabPosition>,
    tabPage: TabPage
) {
    val transition = updateTransition(
        tabPage,
        label = "Tab Indicator"
    )

    val indicatorLeft by transition.animateDp(
        transitionSpec = {
            if (TabPage.Home isTransitioningTo TabPage.Work) {
                spring(stiffness = Spring.StiffnessVeryLow)
            } else {
                spring(stiffness = Spring.StiffnessMedium)
            }
        },
        label = "Indicator Left"
    ) { page ->
        tabPositions[page.ordinal].left
    }

    val indicatorRight by transition.animateDp(
        transitionSpec = {
            if (TabPage.Home isTransitioningTo TabPage.Work) {
                spring(stiffness = Spring.StiffnessMedium)
            } else {
                spring(stiffness = Spring.StiffnessVeryLow)
            }

        },
        label = "Indicator right"
    ) { page ->
        tabPositions[page.ordinal].right
    }

    val color by transition.animateColor(
        label = "Border color"
    ) { page ->
        if (page == TabPage.Home)
            Purple700
        else
            Green800
    }

    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(4.dp)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, color),
                RoundedCornerShape(4.dp)
            )
    )
}

/**
 * to show a tab
 *
 * @param icon the icon to be shown on this tab
 * @param title the title to be shown on this tab
 * @param onClick called when this tab is clicked
 * @param modifier the [Modifier]
 */
@Composable
private fun TabHome(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = title)
    }
}

/**
 * Shows the weather.
 *
 * @param onRefresh Called when the refresh icon button is clicked.
 */
@Composable
private fun RowWeather(
    onRefresh: () -> Unit
) {
    Row(
        modifier = Modifier
            .heightIn(min = 64.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Amber600)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = stringResource(R.string.temperature), fontSize = 24.sp)

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onRefresh) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(R.string.refresh)
            )
        }
    }
}

/**
 * to show the loading state of the weather
 */
@Composable
private fun RowLoading() {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.7f at 500
            },
            repeatMode = RepeatMode.Reverse
        )
    )
    Row(
        modifier = Modifier
            .heightIn(min = 64.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = alpha))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(Color.LightGray.copy(alpha = alpha))
        )
    }
}

/**
 * to show a row for one task
 *
 * @param task the description of the task
 * @param onRemove called when the task is swiped away and removed
 */
@Composable
private fun RowTask(
    task: String,
    onRemove: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .swipeToDismiss(onRemove),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = task,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

/**
 * the modified element that can be horizontally swiped away
 *
 * @param onDismissed Called when the element is swiped to the edge of the screen
 */
private fun Modifier.swipeToDismiss(
    onDismissed: () -> Unit
): Modifier = composed {

    val offsetX = remember { Animatable(0f) }

    pointerInput(Unit) {

        // to calculate a settling position of a fling animation
        val decay = splineBasedDecay<Float>(this)

        // to wrap in a coroutine scope to use suspend functions,
        // for touch events and animation
        coroutineScope {
            while (true) {
                // to wait for a touch down event
                val pointerId = awaitPointerEventScope { awaitFirstDown().id }
                offsetX.stop()

                // to prepare for drag events and record velocity of a fling
                val velocityTracker = VelocityTracker()

                // to wait for drag events
                awaitPointerEventScope {
                    horizontalDrag(pointerId = pointerId) { change ->
                        val horizontalDragOffset = offsetX.value + change.positionChange().x
                        launch {
                            offsetX.snapTo(horizontalDragOffset)
                        }
                        // to record the velocity of the drag
                        velocityTracker.addPosition(change.uptimeMillis, change.position)

                        // to consume the gesture event,
                        // not passed to external
                        change.consumePositionChange()
                    }
                }

                // to calculate the velocity of the fling, after dragging finished
                val velocity = velocityTracker.calculateVelocity().x

                val targetOffsetX = decay.calculateTargetValue(offsetX.value, velocity)

                offsetX.updateBounds(
                    lowerBound = -size.width.toFloat(),
                    upperBound = size.width.toFloat()
                )

                launch {
                    if (targetOffsetX.absoluteValue <= size.width) {
                        // without enough velocity,
                        // to slide back
                        offsetX.animateTo(targetValue = 0f, initialVelocity = velocity)
                    } else {
                        // with enough velocity,
                        // to slide away the element to the edge
                        offsetX.animateDecay(velocity, decay)
                        // the element was swiped away
                        onDismissed()
                    }
                }
            }
        }
    }.offset {
        IntOffset(offsetX.value.roundToInt(), 0)
    }
}

@Preview
@Composable
private fun PreviewTabBarHome() {
    TabBarHome(
        backgroundColor = Purple100,
        tabPage = TabPage.Home,
        onTabSelected = { }
    )
}

@Preview
@Composable
private fun PreviewHome() {
    ThemeCodeLabAnimation {
        Home()
    }
}
