package com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.R
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.data.UserData
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.component.AccountRow
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.component.BillRow
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.component.RallyAlertDialog
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.component.RallyDivider
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.util.formatAmount
import java.util.*

@Composable
fun OverviewBody(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onAccountClick: (String) -> Unit = {},
) {
    Column() {
        AlertCard()
        Spacer(modifier = Modifier.height(RallyDefaultPadding))
        AccountsCard(onClickSeeAll = onClickSeeAllAccounts, onAccountClick = onAccountClick)
        Spacer(modifier = Modifier.height(RallyDefaultPadding))
        BillsCard(onClickSeeAllBills)
    }
}

@Composable
private fun AlertCard() {
    var showDialog by remember { mutableStateOf(false) }
    val alertMessage = "Heads up, you have used up 90% of your shopping budget for this month."

    if (showDialog)
        RallyAlertDialog(
            onDismiss = {
                showDialog = false
            },
            textBody = alertMessage,
            textBtn = "Dismiss".uppercase(Locale.getDefault())
        )

    Card {
        Column {
            AlertHeader {
                showDialog = true
            }
            RallyDivider(
                modifier = Modifier.padding(start = RallyDefaultPadding, end = RallyDefaultPadding)
            )
            AlertItem(message = alertMessage)
        }
    }
}

@Composable
private fun AlertHeader(onClickSeeAll: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(RallyDefaultPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Alerts",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        TextButton(
            onClick = onClickSeeAll,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = "SEE ALL",
                style = MaterialTheme.typography.button
            )
        }
    }
}


@Composable
private fun AlertItem(message: String) {
    Row(
        modifier = Modifier
            .padding(RallyDefaultPadding)
            /**
             * to regard the whole row as one semantic node.
             * this way each row will receive focus as a whole,
             * and the focus bounds will be around the whole row content.
             *
             * the semantics properties of the descendants will be merged.
             *
             * if one would use `clearAndSetSemantics` instead,
             * one would have to define the semantics properties explicitly.
             */
            .semantics(mergeDescendants = true) {},
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = { },
            modifier = Modifier
                .align(Alignment.Top)
                .clearAndSetSemantics { }) {
            Icon(
                Icons.Filled.Sort,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun AccountsCard(
    onClickSeeAll: () -> Unit,
    onAccountClick: (String) -> Unit
) {
    val amount = UserData.accounts.map { account -> account.balance }.sum()
    OverviewScreenCard(
        title = stringResource(id = R.string.accounts),
        amount = amount,
        onClickSeeAll = onClickSeeAll,
        values = { it.balance },
        colors = { it.color },
        data = UserData.accounts
    ) { account ->
        AccountRow(
            modifier = Modifier.clickable { onAccountClick(account.name) },
            name = account.name,
            number = account.number,
            amount = account.balance,
            color = account.color
        )
    }
}

@Composable
private fun BillsCard(
    onClickSeeAll: () -> Unit
) {
    val amount = UserData.bills.map { bill -> bill.amount }.sum()
    OverviewScreenCard(
        title = stringResource(id = R.string.bills),
        amount = amount,
        onClickSeeAll = onClickSeeAll,
        values = { it.amount },
        colors = { it.color },
        data = UserData.bills
    ) { bill ->
        BillRow(
            name = bill.name,
            due = bill.due,
            amount = bill.amount,
            color = bill.color
        )
    }
}

@Composable
private fun SeeAlLButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .height(44.dp)
            .fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.see_all))
    }
}

@Composable
private fun <T> OverviewScreenCard(
    title: String,
    amount: Float,
    onClickSeeAll: () -> Unit,
    values: (T) -> Float,
    colors: (T) -> Color,
    data: List<T>,
    row: @Composable (T) -> Unit
) {
    Card {
        Column {
            Column(Modifier.padding(RallyDefaultPadding)) {
                Text(text = title, style = MaterialTheme.typography.subtitle2)
                val amountText = "$${formatAmount(amount)}"
                Text(text = amountText, style = MaterialTheme.typography.h2)
            }
            OverviewDivider(data = data, values = values, colors)
            Column(Modifier.padding(start = 16.dp, top = 4.dp, end = 8.dp)) {
                data.take(SHOW_ITEMS).forEach { row(it) }
                SeeAlLButton(
                    modifier = Modifier.clearAndSetSemantics {
                        contentDescription = "All $title"
                    },
                    onClick = onClickSeeAll
                )
            }
        }
    }
}

@Composable
private fun <T> OverviewDivider(
    data: List<T>,
    values: (T) -> Float,
    colors: (T) -> Color
) {
    Row(Modifier.fillMaxWidth()) {
        data.forEach { item: T ->
            Spacer(
                modifier = Modifier
                    .weight(values(item))
                    .height(1.dp)
                    .background(colors(item))
            )
        }
    }
}

private val RallyDefaultPadding = 12.dp

private const val SHOW_ITEMS = 3