package com.an7one.officialDoc.jetpackCompose.codeLabNavigation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.data.UserData
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.RallyScreen
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.account.AccountsBody
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.account.SingleAccountBody
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.bill.BillsBody
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.component.RallyTabRow
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.overview.OverviewBody
import com.an7one.officialDoc.jetpackCompose.codeLabNavigation.ui.theme.RallyTheme

private val navRouteAccounts = RallyScreen.Accounts.name
private val navRouteBills = RallyScreen.Bills.name
private val navRouteOverview = RallyScreen.Overview.name

private const val ID_ARG_NAME = "name"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyApp()
        }
    }
}


@Composable
private fun RallyApp() {
    RallyTheme {
        val allScreens = RallyScreen.values().toList()
        val navController = rememberNavController()
        val backstackEntry = navController.currentBackStackEntryAsState()
        var curScreen = RallyScreen.fromRoute(
            backstackEntry.value?.destination?.route
        )

        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = allScreens,
                    onTabSelected = { screen ->
                        navController.navigate(screen.name)
                    },
                    curScreen = curScreen
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = navRouteOverview,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(navRouteOverview) {
                    OverviewBody(
                        onClickSeeAllAccounts = { navController.navigate(navRouteAccounts) },
                        onClickSeeAllBills = { navController.navigate(navRouteBills) },
                        onAccountClick = { name -> navigateToSingleAccount(navController, name) },
                    )
                }

                composable(navRouteAccounts) {
                    AccountsBody(accounts = UserData.accounts) { name ->
                        navigateToSingleAccount(
                            navController = navController,
                            accountName = name
                        )
                    }
                }

                composable(
                    "$navRouteAccounts/{name}",
                    arguments = listOf(
                        navArgument(ID_ARG_NAME) {
                            // to make argument type-safe
                            type = NavType.StringType
                        }
                    )
                ) { entry ->
                    // to look up `name` in the arguments of `NavBackStackEntry`
                    val accountName = entry.arguments?.getString(ID_ARG_NAME)
                    // to find the first name match in `UserData`
                    val account = UserData.getAccount(accountName)
                    // to pass `account` to the `SingleAccountBody`
                    SingleAccountBody(account = account)
                }

                composable(navRouteBills) {
                    BillsBody(bills = UserData.bills)
                }
            }
        }
    }
}

private fun navigateToSingleAccount(
    navController: NavHostController,
    accountName: String
) {
    navController.navigate("$navRouteAccounts/$accountName")
}