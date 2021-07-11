package com.an7one.officialDoc.jetpackCompose.codeLab.ui

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.an7one.officialDoc.jetpackCompose.codeLab.model.ToDoIcon

/**
 * to draw a row of [ToDoIcon] with visibility changes animated
 *
 * when not visible, will collapse to 16.dp high by default.
 * one can enlarge this with the passed modifier
 *
 * @param icon (state) the current selected icon
 * @param onIconChange (event) request the selected icon change
 * @param modifier modifier for this element
 * @param visible (state) if the icon should be shown
 */
@ExperimentalAnimationApi
@Composable
fun AnimatedIconRow(
    icon: ToDoIcon,
    onIconChange: (ToDoIcon) -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = true,
) {
    // to remember these specs so they do not restart if recomposing during the animation,
    // which is required since TweenSpec restarts on interruption
    val enter = remember { fadeIn(animationSpec = TweenSpec(300, easing = FastOutLinearInEasing)) }
    val exit = remember { fadeOut(animationSpec = TweenSpec(100, easing = FastOutSlowInEasing)) }
    Box(modifier = modifier.defaultMinSize(minHeight = 16.dp)) {
        AnimatedVisibility(
            visible = visible,
            enter = enter,
            exit = exit
        ) {
            IconRow(icon, onIconChange)
        }
    }
}

/**
 * to display a row of selectable [ToDoIcon]
 *
 * @param icon (state) the current selected icon
 * @param onIconChange (event) request the selected icon change
 * @param modifier modifier for this element
 */
@Composable
fun IconRow(
    icon: ToDoIcon,
    onIconChange: (ToDoIcon) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        for (toDoIcon in ToDoIcon.values()) {
            SelectableIconButton(
                icon = toDoIcon.imageVector,
                iconContentDescription = toDoIcon.contentDescription,
                onIconSelected = { onIconChange(toDoIcon) },
                isSelected = toDoIcon == icon
            )
        }
    }
}

/**
 * to draw a background based on [MaterialTheme.colors.onSurface] that animates resizing and elevation changes
 *
 * @param elevate draw a shadow, changes to this will be anmiated
 * @param modifier modifier for this element
 * @param content (slot) content to draw in the background
 */
@Composable
private fun SelectableIconButton(
    icon: ImageVector,
    @StringRes iconContentDescription: Int,
    onIconSelected: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val tint = if (isSelected)
        MaterialTheme.colors.primary
    else
        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)

    TextButton(
        onClick = { onIconSelected() },
        shape = CircleShape,
        modifier = modifier
    ) {
        Column {
            Icon(
                imageVector = icon,
                tint = tint,
                contentDescription = stringResource(id = iconContentDescription)
            )

            if (isSelected)
                Box(
                    modifier = Modifier
                        .padding(top = 3.dp)
                        .width(icon.defaultWidth)
                        .height(1.dp)
                        .background(tint)
                )
            else
                Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

/**
 * Styled [TextField] for inputting a [ToDoItem]
 *
 * @param text (state) current text to display
 * @param onTextChange (event) requrest the text change status
 * @param modifier the modifier for this element
 * @param onImeAction (event) notify caller of [ImeAction.Done] event
 */
@ExperimentalComposeUiApi
@Composable
fun BackgroundToDoItemInput(
    elevate: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    val animatedElevation by animateDpAsState(if (elevate) 1.dp else 0.dp, TweenSpec(500))

    Surface(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
        elevation = animatedElevation,
        shape = RectangleShape,
    ) {
        Row(
            modifier = modifier.animateContentSize(animationSpec = TweenSpec(300)),
            content = content
        )
    }
}

/**
 * Styled [TextField] for inputting a [ToDoItem]
 *
 * @param text (state) current text to display
 * @param onTextChange (event) request the text change state
 * @param modifier the modifier for this element
 * @param onImeAction (event) notify caller of [ImeAction.Done] events
 */
@ExperimentalComposeUiApi
@Composable
fun TextFieldToDoInput(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onImeAction: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }),
        modifier = modifier
    )
}

/**
 * Styled button for [ToDoScreen]
 *
 * @param onClick (event) notify caller of click events
 * @param text button text
 * @param modifier modifier for button
 * @param enabled enable or disable the button
 */
@Composable
fun ButtonAddToDoItem(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    TextButton(
        onClick = onClick,
        shape = CircleShape,
        enabled = enabled,
        modifier = modifier
    ) {
        Text(text)
    }
}

@Preview
@Composable
fun PreviewIconRow() = IconRow(icon = ToDoIcon.Square, onIconChange = {})