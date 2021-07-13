package com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.data.UserData
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.account.AccountsBody
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.bill.BillsBody
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.overview.OverviewBody
import java.lang.IllegalArgumentException


enum class RallyScreen(
    val icon: ImageVector
) {
    Overview(
        icon = Icons.Filled.PieChart,
    ),
    Accounts(
        icon = Icons.Filled.AttachMoney,
    ),
    Bills(
        icon = Icons.Filled.MoneyOff,
    );

    companion object {
        fun fromRoute(route: String?): RallyScreen =
            when (route?.substringBefore("/")) {
                Accounts.name -> Accounts
                Bills.name -> Bills
                Overview.name -> Overview
                null -> Overview
                else -> throw IllegalArgumentException("Route $route is not recognized")
            }
    }
}

enum class RallyScreen1(
    val icon: ImageVector,
    val body: @Composable ((String) -> Unit) -> Unit
) {
    Overview(
        icon = Icons.Filled.PieChart,
        body = { OverviewBody() }
    ),
    Accounts(
        icon = Icons.Filled.AttachMoney,
        body = { AccountsBody(UserData.accounts) }
    ),
    Bills(
        icon = Icons.Filled.MoneyOff,
        body = { BillsBody(UserData.bills) }
    );

    @Composable
    fun content(onScreenChange: (String) -> Unit) {
        body(onScreenChange)
    }

    companion object {
        fun fromRoute(route: String?): RallyScreen1 =
            when (route?.substringBefore("/")) {
                Accounts.name -> Accounts
                Bills.name -> Bills
                Overview.name -> Overview
                null -> Overview
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}
