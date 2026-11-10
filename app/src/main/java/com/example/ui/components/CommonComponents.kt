package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserAccount
import com.example.ui.theme.*

@Composable
fun AppHeader(
    isArabic: Boolean,
    isDarkMode: Boolean,
    currentUser: UserAccount?,
    onToggleLanguage: () -> Unit,
    onToggleDarkMode: () -> Unit,
    onOpenRoleSwitcher: () -> Unit,
    onOpenGeminiAi: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Brand Header with Icon & Subtitle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(BrandOrange, BrandOrangeDark)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Restaurant,
                            contentDescription = "Logo",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = if (isArabic) "كاترينج مصر | CATERING EGYPT" else "CATERING EGYPT | كاترينج مصر",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = BrandOrange
                        )
                        Text(
                            text = if (isArabic) "الشركة الوطنية اللوجستية" else "AN ELWATANYA LOGISTICS COMPANY",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Controls: Language, Theme, Gemini AI, Role
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Gemini AI Advisor button
                    IconButton(
                        onClick = onOpenGeminiAi,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Gemini AI",
                            tint = BrandOrange
                        )
                    }

                    // Dark mode toggle
                    IconButton(
                        onClick = onToggleDarkMode,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Theme",
                            tint = BrandOrange
                        )
                    }

                    // Language toggle button
                    FilledTonalButton(
                        onClick = onToggleLanguage,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = BrandOrange.copy(alpha = 0.12f),
                            contentColor = BrandOrange
                        )
                    ) {
                        Text(
                            text = if (isArabic) "EN" else "عربي",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // User & Role bar (Clickable to switch demo roles)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onOpenRoleSwitcher() },
                color = if (currentUser?.role?.isAdminPortal == true)
                    BrandOrange.copy(alpha = 0.08f)
                else
                    SuccessGreen.copy(alpha = 0.08f),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (currentUser?.role?.isAdminPortal == true) BrandOrange.copy(alpha = 0.25f) else SuccessGreen.copy(alpha = 0.25f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(if (currentUser?.role?.isAdminPortal == true) BrandOrange else SuccessGreen)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = if (isArabic)
                                    "${currentUser?.fullNameAr} (${currentUser?.role?.titleAr})"
                                else
                                    "${currentUser?.fullNameEn} (${currentUser?.role?.titleEn})",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (currentUser?.role?.isAdminPortal == true)
                                    (if (isArabic) "بوابة الإدارة المركزية (صلاحيات كاملة)" else "Private Admin Portal")
                                else
                                    (if (isArabic) "بوابة العاملين والعملاء (بيانات الموقع فقط)" else "Employee & Site Portal"),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (currentUser?.role?.isAdminPortal == true) BrandOrangeDark else SuccessGreen
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isArabic) "تبديل الدور" else "Switch Role",
                            style = MaterialTheme.typography.labelSmall,
                            color = BrandOrange,
                            fontWeight = FontWeight.SemiBold
                        )
                        Icon(
                            imageVector = Icons.Default.SwapHoriz,
                            contentDescription = "Switch",
                            tint = BrandOrange,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RoleSwitcherDialog(
    isArabic: Boolean,
    users: List<UserAccount>,
    currentUserId: String?,
    onSelectUser: (UserAccount) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (isArabic) "تبديل المستخدم والصلاحية للتشغيل التجريبي" else "Switch User & Operational Role",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (isArabic)
                        "اختر مستخدماً لتجربة عزل الصلاحيات وحماية البيانات بين الإدارة المركزية والمواقع والعاملين:"
                    else
                        "Select a user profile to verify role isolation between Central Admin and Site Workers:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))

                users.forEach { user ->
                    val isSelected = user.id == currentUserId
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                onSelectUser(user)
                                onDismiss()
                            },
                        color = if (isSelected)
                            BrandOrange.copy(alpha = 0.15f)
                        else
                            MaterialTheme.colorScheme.surfaceVariant,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) BrandOrange else BorderGray
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (isArabic) user.fullNameAr else user.fullNameEn,
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (isArabic)
                                        "${user.role.titleAr} • ${user.departmentAr}"
                                    else
                                        "${user.role.titleEn} • ${user.departmentEn}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (user.role.isAdminPortal) BrandOrangeDark else SuccessGreen
                                )
                            }

                            Badge(
                                containerColor = if (user.role.isAdminPortal) BrandOrange else SuccessGreen,
                                contentColor = Color.White
                            ) {
                                Text(
                                    text = if (user.role.isAdminPortal)
                                        (if (isArabic) "إدارة" else "Admin")
                                    else
                                        (if (isArabic) "موقع" else "Site"),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = if (isArabic) "إغلاق" else "Close", color = BrandOrange)
            }
        }
    )
}

@Composable
fun KpiCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
                }

                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
