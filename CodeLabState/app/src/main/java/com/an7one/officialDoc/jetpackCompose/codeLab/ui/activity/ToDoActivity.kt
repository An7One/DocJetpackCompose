package com.an7one.officialDoc.jetpackCompose.codeLab.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.an7one.officialDoc.jetpackCompose.codeLab.ui.StateCodeLabTheme
import com.an7one.officialDoc.jetpackCompose.codeLab.ui.toDo.ScreenToDoItem
import com.an7one.officialDoc.jetpackCompose.codeLab.viewModel.ViewModelToDoItem

class ToDoActivity : AppCompatActivity() {
    val toDoViewModel by viewModels<ViewModelToDoItem>()

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateCodeLabTheme {
                Surface {
                    ScreenToDoItemActivity(viewModelToDoItem = toDoViewModel)
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
private fun ScreenToDoItemActivity(viewModelToDoItem: ViewModelToDoItem) {
    /**
     * `observeState` observes a `LiveData`,
     * and returns a `State` object that is updated whenever the `LiveData` changes.
     *
     * `by` is the property delegate syntax in Kotlin,
     * which lets one automatically unwrap the `State<List<ToDoItem>>` from `observeAsState` into a regular `List<ToDoItem>`
     */
    ScreenToDoItem(
        items = viewModelToDoItem.toDoItems,
        itemCurEditing = viewModelToDoItem.curEditItem,
        onAddingItem = viewModelToDoItem::addItem,
        onRemovingItem = viewModelToDoItem::removeItem,
        onStartEditing = viewModelToDoItem::onEditItemSelected,
        onEditItemChange = viewModelToDoItem::onEditItemChange,
        onEditDone = viewModelToDoItem::onEditDone
    )
}