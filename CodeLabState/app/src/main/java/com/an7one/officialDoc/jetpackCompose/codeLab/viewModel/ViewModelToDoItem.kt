package com.an7one.officialDoc.jetpackCompose.codeLab.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.an7one.officialDoc.jetpackCompose.codeLab.model.ToDoItem

class ViewModelToDoItem : ViewModel() {

    // private state
    private var posCurItem by mutableStateOf(-1)
    val curEditItem: ToDoItem?
        get() = toDoItems.getOrNull(posCurItem)

    // state: toDoItems
    var toDoItems = mutableStateListOf<ToDoItem>()
        private set

    // event: addItem
    fun addItem(item: ToDoItem) {
        toDoItems.add(item)
    }

    // event: removeItem
    fun removeItem(item: ToDoItem) {
        toDoItems.remove(item)
        onEditDone() // do not keep the editor open when one removing items
    }

    // event: onEditItemSelected
    fun onEditItemSelected(item: ToDoItem) {
        posCurItem = toDoItems.indexOf(item)
    }

    // event: onEditDone
    fun onEditDone() {
        posCurItem = -1
    }

    // event: onEditItemChange
    fun onEditItemChange(item: ToDoItem) {
        val curItem = requireNotNull(curEditItem)
        require(curEditItem!!.id == item.id) {
            "One can only change an item with the same id as curEditItem"
        }

        toDoItems[posCurItem] = item
    }
}