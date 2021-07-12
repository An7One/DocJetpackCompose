package com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.bill

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.R
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.data.Bill
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.component.BillRow
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.component.StatementBody

/**
 * the screen for bills
 */
@Composable
fun BillsBody(bills: List<Bill>) {
    StatementBody(
        modifier = Modifier.clearAndSetSemantics { contentDescription = "Bills" },
        items = bills,
        colors = { bill -> bill.color },
        amounts = { it.amount },
        amountsTotal = bills.map { bill -> bill.amount }.sum(),
        circleLabel = stringResource(id = R.string.due),
        rows = { bill ->
            BillRow(
                name = bill.name,
                due = bill.due,
                amount = bill.amount,
                color = bill.color
            )
        }
    )
}

