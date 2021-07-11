package com.an7one.officialDoc.jetpackCompose.codeLab.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.an7one.officialDoc.jetpackCompose.codeLab.model.ToDoItem

class ToDoViewModel : ViewModel() {
    private val _toDoItems = MutableLiveData(listOf<ToDoItem>())
    val toDoItems: LiveData<List<ToDoItem>> = _toDoItems

    fun addItem(item: ToDoItem) {
        _toDoItems.value = _toDoItems.value!! + listOf(item)
    }

    fun removeItem(item: ToDoItem) {
        _toDoItems.value = _toDoItems.value!!.toMutableList().also {
            it.remove(item)
        }
    }
}