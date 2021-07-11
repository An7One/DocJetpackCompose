package com.an7one.officialDoc.jetpackCompose.codeLab.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.an7one.officialDoc.jetpackCompose.codeLab.R
import java.util.*

data class ToDoItem(
    val task: String,
    val icon: ToDoIcon = ToDoIcon.Default,
    // since the user mange generate identical tasks, to give them each a unique ID
    val id: UUID = UUID.randomUUID()
)

enum class ToDoIcon(
    val imageVector: ImageVector,
    @StringRes val contentDescription: Int
) {
    Square(Icons.Default.CropSquare, R.string.cd_expand),
    Done(Icons.Default.Done, R.string.cd_done),
    Event(Icons.Default.Event, R.string.cd_event),
    Privacy(Icons.Default.PrivacyTip, R.string.cd_privacy),
    Trash(Icons.Default.RestoreFromTrash, R.string.cd_restore);

    companion object {
        val Default = Square
    }
}