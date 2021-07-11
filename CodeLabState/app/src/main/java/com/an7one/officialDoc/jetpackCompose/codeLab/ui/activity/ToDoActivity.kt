package com.an7one.officialDoc.jetpackCompose.codeLab.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.an7one.officialDoc.jetpackCompose.codeLab.model.ToDoItem
import com.an7one.officialDoc.jetpackCompose.codeLab.ui.StateCodeLabTheme
import com.an7one.officialDoc.jetpackCompose.codeLab.ui.toDo.ToDoScreen
import com.an7one.officialDoc.jetpackCompose.codeLab.viewModel.ToDoViewModel

class ToDoActivity : AppCompatActivity() {
    val toDoViewModel by viewModels<ToDoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateCodeLabTheme {
                Surface {
                    ToDoActivityScreen(toDoViewModel = toDoViewModel)
                }
            }
        }
    }
}

@Composable
private fun ToDoActivityScreen(toDoViewModel: ToDoViewModel) {
    /**
     * `observeState` observes a `LiveData`,
     * and returns a `State` object that is updated whenever the `LiveData` changes.
     *
     * `by` is the property delegate syntax in Kotlin,
     * which lets one automatically unwrap the `State<List<ToDoItem>>` from `observeAsState` into a regular `List<ToDoItem>`
     */
    val items: List<ToDoItem> by toDoViewModel.toDoItems.observeAsState(listOf())
    ToDoScreen(
        items = items,
        onAddingItem = { toDoViewModel.addItem(it) },
        onRemovingItem = { toDoViewModel.removeItem(it) }
    )
}