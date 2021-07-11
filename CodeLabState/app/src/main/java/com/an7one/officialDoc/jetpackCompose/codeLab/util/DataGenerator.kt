package com.an7one.officialDoc.jetpackCompose.codeLab.util

import com.an7one.officialDoc.jetpackCompose.codeLab.model.ToDoIcon
import com.an7one.officialDoc.jetpackCompose.codeLab.model.ToDoItem

fun generateRandomToDoItem(): ToDoItem {
    val message = listOf(
        "Learn compose",
        "Learn state",
        "Build dynamic UIs",
        "Learn Unidirectional Data Flow",
        "Integrate LiveData",
        "Integrate ViewModel",
        "Remember to savedState!",
        "Build stateless composables",
        "Use state from stateless composables"
    ).random()

    val icon = ToDoIcon.values().random()
    return ToDoItem(message, icon)
}

fun main() {
    val cnts = listOf(1, 2, 3)
    var cntCur = 0
    for (cnt in cnts) {
        cntCur += cnt
        println(cntCur)
    }
}