package com.an7one.officialDoc.jetpackCompose.codeLab

import com.an7one.officialDoc.jetpackCompose.codeLab.util.generateRandomToDoItem
import com.an7one.officialDoc.jetpackCompose.codeLab.viewModel.ViewModelToDoItem
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TestViewModelToDoItem {

    @Test
    fun whenRemovingItem_updatesList() {
        // before
        val viewModel = ViewModelToDoItem()
        val item1 = generateRandomToDoItem()
        val item2 = generateRandomToDoItem()
        viewModel.addItem(item1)
        viewModel.addItem(item2)

        // during
        viewModel.removeItem(item1)

        // after
        assertThat(viewModel.toDoItems).isEqualTo(listOf(item2))
    }
}