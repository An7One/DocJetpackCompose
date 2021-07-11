package com.an7one.officialDoc.jetpackCompose.codeLab.ui.toDo

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.an7one.officialDoc.jetpackCompose.codeLab.model.ToDoIcon
import com.an7one.officialDoc.jetpackCompose.codeLab.model.ToDoItem
import com.an7one.officialDoc.jetpackCompose.codeLab.ui.*
import com.an7one.officialDoc.jetpackCompose.codeLab.util.generateRandomToDoItem
import kotlin.random.Random

/**
 * stateless component that is responsible for the entire todo screen
 *
 * @param items (state) list of [ToDoItems] to display
 * @param onAddingItem (event) request an item be added
 * @param onRemovingItem (event) request an item be removed
 */
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ToDoScreen(
    items: List<ToDoItem>,
    onAddingItem: (ToDoItem) -> Unit,
    onRemovingItem: (ToDoItem) -> Unit
) {
    Column {
        // add ToDoItemInputBackground and ToDoItem at the top of the ToDoScreen
        BackgroundToDoItemInput(elevate = true, modifier = Modifier.fillMaxWidth()) {
            ToDoItemInput(onItemComplete = onAddingItem)
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items = items) {
                ToDoRow(
                    toDoItem = it,
                    onItemClicked = { onRemovingItem(it) },
                    modifier = Modifier.fillParentMaxWidth()
                )
            }
        }

        // for quick testing, a random item generator button
        Button(
            onClick = { onAddingItem(generateRandomToDoItem()) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(text = "Add Random Items")
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ToDoItemInput(onItemComplete: (ToDoItem) -> Unit) {
    val (text, setText) = remember { mutableStateOf("") }
    val (icon, setIcon) = remember { mutableStateOf(ToDoIcon.Default) }
    val iconsVisible = text.isNotBlank()
    val submit = {
        onItemComplete(ToDoItem(text, icon)) // to send onItemComplete event
        setIcon(ToDoIcon.Default)
        setText("")  // to clear the internal text
    }
    // onItemComplete is an event that will be fired when an item is completed by the user
    Column {
        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            TextFieldToDoInput(
                text = text,
                onTextChange = setText,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                onImeAction = submit // to pass the submit callback to `TextFieldToDoInput`
            )

            ButtonAddToDoItem(
                onClick = submit, // to pass the submit callback to `ButtonToDoEditText`
                text = "Add",
                modifier = Modifier.align(Alignment.CenterVertically),
                enabled = text.isNotBlank() // to enable if the text is not blank
            )
        }

        if (iconsVisible)
            AnimatedIconRow(icon, setIcon, Modifier.padding(top = 8.dp))
        else
            Spacer(modifier = Modifier.height(16.dp))
    }
}

/**
 * stateless component that displays a full-width [ToDoItem]
 *
 * @param toDoItem to show
 * @param onItemClicked (event) notify caller that the row was clicked
 * @param modifier modifier for this element
 */
@Composable
private fun ToDoRow(
    toDoItem: ToDoItem,
    onItemClicked: (ToDoItem) -> Unit,
    modifier: Modifier = Modifier,
    iconAlpha: Float = remember(toDoItem.id) { randomTint() }
) {
    Row(
        modifier = modifier
            .clickable { onItemClicked(toDoItem) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = toDoItem.task)
        Icon(
            imageVector = toDoItem.icon.imageVector,
            tint = LocalContentColor.current.copy(alpha = iconAlpha),
            contentDescription = stringResource(id = toDoItem.icon.contentDescription)
        )
    }
}

private fun randomTint(): Float = Random.nextFloat().coerceIn(0.3f, 0.9f)

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Preview
@Composable
fun PreviewToDoScreen() {
    val items = listOf(
        ToDoItem("Learn Compose", ToDoIcon.Event),
        ToDoItem("Take the CodLab"),
        ToDoItem("Apply State", ToDoIcon.Done),
        ToDoItem("Build Dynamic UIs", ToDoIcon.Square)
    )

    ToDoScreen(items, {}, {})
}

@Preview
@Composable
fun PreviewToDoRow() {
    val toDoItem = remember { generateRandomToDoItem() }
    ToDoRow(
        toDoItem = toDoItem,
        onItemClicked = {},
        modifier = Modifier.fillMaxSize()
    )
}