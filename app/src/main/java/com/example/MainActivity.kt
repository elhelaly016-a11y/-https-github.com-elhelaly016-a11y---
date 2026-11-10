package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AppScreen
import com.example.ui.CateringViewModel
import com.example.ui.admin.*
import com.example.ui.components.AppHeader
import com.example.ui.components.GeminiAiDialog
import com.example.ui.components.RoleSwitcherDialog
import com.example.ui.employee.EmployeePortalScreen
import com.example.ui.theme.CateringEgyptTheme
import com.example.util.configureAppCheck

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configure Firebase App Check with launch intent before UI & Firebase operations
        configureAppCheck(this, intent)

        enableEdgeToEdge()
        setContent {
            val viewModel: CateringViewModel = viewModel()
            val isDarkMode by viewModel.isDarkMode.collectAsState()
            val isArabic by viewModel.isArabic.collectAsState()
            val currentUser by viewModel.currentUser.collectAsState()
            val currentScreen by viewModel.currentScreen.collectAsState()
            val allUsers by viewModel.users.collectAsState()

            var showRoleSwitcherDialog by remember { mutableStateOf(false) }
            var showGeminiAiDialog by remember { mutableStateOf(false) }

            // Handle back navigation for secondary admin screens
            BackHandler(enabled = currentUser?.role?.isAdminPortal == true && currentScreen != AppScreen.ADMIN_DASHBOARD) {
                viewModel.navigateTo(AppScreen.ADMIN_DASHBOARD)
            }

            CateringEgyptTheme(darkTheme = isDarkMode) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AppHeader(
                            isArabic = isArabic,
                            isDarkMode = isDarkMode,
                            currentUser = currentUser,
                            onToggleLanguage = { viewModel.toggleLanguage() },
                            onToggleDarkMode = { viewModel.toggleDarkMode() },
                            onOpenRoleSwitcher = { showRoleSwitcherDialog = true },
                            onOpenGeminiAi = { showGeminiAiDialog = true }
                        )
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        val user = currentUser
                        if (user != null) {
                            if (user.role.isAdminPortal) {
                                when (currentScreen) {
                                    AppScreen.ADMIN_DASHBOARD -> AdminDashboardScreen(viewModel, isArabic)
                                    AppScreen.ADMIN_KITCHENS -> KitchensScreen(viewModel, isArabic)
                                    AppScreen.ADMIN_SITES -> SitesScreen(viewModel, isArabic)
                                    AppScreen.ADMIN_PENDING -> PendingContractsScreen(viewModel, isArabic)
                                    AppScreen.ADMIN_MENUS -> MenuManagementScreen(viewModel, isArabic)
                                    AppScreen.ADMIN_ORDERS -> OrdersScreen(viewModel, isArabic)
                                    AppScreen.ADMIN_CANTEEN -> CanteenAdminScreen(viewModel, isArabic)
                                    AppScreen.ADMIN_INVENTORY,
                                    AppScreen.ADMIN_FINANCE,
                                    AppScreen.ADMIN_QUOTATIONS -> InventoryFinanceScreen(viewModel, isArabic)
                                    AppScreen.ADMIN_QUALITY_HYGIENE -> QualityHygieneScreen(viewModel, isArabic)
                                    AppScreen.ADMIN_COMPLAINTS -> AdminComplaintsScreen(viewModel, isArabic)
                                    AppScreen.ADMIN_COUPONS,
                                    AppScreen.ADMIN_SEASONAL,
                                    AppScreen.ADMIN_CSR -> MarketingAdminScreen(viewModel, isArabic)
                                    AppScreen.ADMIN_REPORTS -> ReportsScreen(viewModel, isArabic)
                                    else -> AdminDashboardScreen(viewModel, isArabic)
                                }
                            } else {
                                EmployeePortalScreen(
                                    viewModel = viewModel,
                                    currentUser = user,
                                    isArabic = isArabic
                                )
                            }
                        }
                    }

                    if (showRoleSwitcherDialog) {
                        RoleSwitcherDialog(
                            isArabic = isArabic,
                            users = allUsers,
                            currentUserId = currentUser?.id,
                            onSelectUser = { selectedUser ->
                                viewModel.switchUser(selectedUser)
                            },
                            onDismiss = { showRoleSwitcherDialog = false }
                        )
                    }

                    if (showGeminiAiDialog) {
                        GeminiAiDialog(
                            viewModel = viewModel,
                            isArabic = isArabic,
                            onDismiss = { showGeminiAiDialog = false }
                        )
                    }
                }
            }
        }
    }
}
