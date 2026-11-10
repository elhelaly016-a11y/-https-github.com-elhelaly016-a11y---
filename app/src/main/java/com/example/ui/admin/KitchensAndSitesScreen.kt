package com.example.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.CentralKitchen
import com.example.data.model.CateringSite
import com.example.data.model.PendingContract
import com.example.ui.AppScreen
import com.example.ui.CateringViewModel
import com.example.ui.theme.*

@Composable
fun KitchensScreen(viewModel: CateringViewModel, isArabic: Boolean) {
    val kitchens by viewModel.kitchens.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateTo(AppScreen.ADMIN_DASHBOARD) }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Column {
                    Text(
                        text = if (isArabic) "CENTRAL KITCHENS | المطابخ المركزية" else "Central Kitchens Operations",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = BrandOrange
                    )
                    Text(
                        text = if (isArabic) "3 مطابخ مركزية كبرى - تشغيل وإعاشة لوجستية" else "3 Mega Central Production Kitchens",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(kitchens) { kitchen ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isArabic) kitchen.nameAr else kitchen.nameEn,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Badge(containerColor = SuccessGreen, contentColor = Color.White) {
                            Text(
                                text = if (isArabic) kitchen.statusAr else kitchen.statusEn,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = BrandOrange, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isArabic) kitchen.locationAr else kitchen.locationEn,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Divider(modifier = Modifier.padding(vertical = 12.dp), color = BorderGray)

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(text = if (isArabic) "مدير المطبخ" else "Manager", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                            Text(text = kitchen.managerName, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                        }
                        Column {
                            Text(text = if (isArabic) "الطاقة اليومية" else "Daily Capacity", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                            Text(text = "${kitchen.dailyCapacity} وجبة", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = BrandOrange)
                        }
                        Column {
                            Text(text = if (isArabic) "إنتاج اليوم" else "Today's Cook", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                            Text(text = "${kitchen.todayProductionCount} وجبة", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = SuccessGreen)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SuggestionChip(
                            onClick = {},
                            label = { Text("سلامة الخامات: ${kitchen.rawMaterialHealthScore}%") },
                            colors = SuggestionChipDefaults.suggestionChipColors(containerColor = LightGraySurface)
                        )
                        SuggestionChip(
                            onClick = {},
                            label = { Text("الهايجين: ${kitchen.hygieneScore}%") },
                            colors = SuggestionChipDefaults.suggestionChipColors(containerColor = SuccessGreenLight)
                        )
                        SuggestionChip(
                            onClick = {},
                            label = { Text("الطلبات: ${kitchen.activeOrdersCount}") },
                            colors = SuggestionChipDefaults.suggestionChipColors(containerColor = LightGraySurface)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SitesScreen(viewModel: CateringViewModel, isArabic: Boolean) {
    val sites by viewModel.sites.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateTo(AppScreen.ADMIN_DASHBOARD) }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Column {
                    Text(
                        text = if (isArabic) "ACTIVE SITES | العملاء ومواقع التشغيل" else "Active Sites & Clients",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = BrandOrange
                    )
                    Text(
                        text = if (isArabic) "رويال، الألمانية للأسمنت، الشروق - بيانات العقود والإعاشة" else "Contracted client operations & sites",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(sites) { site ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isArabic) site.nameAr else site.nameEn,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Badge(containerColor = InfoBlue, contentColor = Color.White) {
                            Text(
                                text = if (isArabic) site.statusAr else site.statusEn,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (isArabic) "التصنيف: ${site.categoryAr}" else "Category: ${site.categoryEn}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "مسؤول الموقع: ${site.contactPerson} (${site.phone})",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Divider(modifier = Modifier.padding(vertical = 10.dp), color = BorderGray)

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(text = "الوجبات المتعاقد عليها", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                            Text(text = "${site.dailyMealsContracted} وجبة/يوم", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = BrandOrange)
                        }
                        Column {
                            Text(text = "المطبخ المركزي المغذي", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                            Text(text = if (isArabic) site.assignedKitchenNameAr else site.assignedKitchenNameEn, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                        }
                        Column {
                            Text(text = "خدمة الكنتِين", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                            Text(
                                text = if (site.hasCanteen) "مفعل ومباشر" else "غير مشمول",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = if (site.hasCanteen) SuccessGreen else MutedGray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    FilledTonalButton(
                        onClick = { viewModel.navigateTo(AppScreen.ADMIN_MENUS) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.filledTonalButtonColors(containerColor = BrandOrange.copy(alpha = 0.1f), contentColor = BrandOrange)
                    ) {
                        Icon(imageVector = Icons.Default.RestaurantMenu, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = if (isArabic) "استعراض منيو وجبات وخدمات هذا الموقع" else "View Site Menus & Services")
                    }
                }
            }
        }
    }
}

@Composable
fun PendingContractsScreen(viewModel: CateringViewModel, isArabic: Boolean) {
    val pending by viewModel.pendingContracts.collectAsState()
    var selectedContractForUpdate by remember { mutableStateOf<PendingContract?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateTo(AppScreen.ADMIN_DASHBOARD) }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Column {
                    Text(
                        text = if (isArabic) "PENDING CONTRACTS | جهات تحت الانتظار" else "Pending Contracts Pipeline",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = BrandOrange
                    )
                    Text(
                        text = if (isArabic) "نجوى، مطبعة الشروق، مستر مينا، يونيماك، القصر العيني الفرنساوي" else "5 Qualified catering contract leads",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(pending) { contract ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isArabic) contract.nameAr else contract.nameEn,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Badge(containerColor = WarningAmber, contentColor = Color.White) {
                            Text(
                                text = if (isArabic) contract.proposalStatusAr else contract.proposalStatusEn,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "النشاط: ${if (isArabic) contract.activityAr else contract.activityEn}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "مسؤول التواصل: ${contract.contactPerson} • ${contract.phone}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "الكمية المتوقعة: ${contract.estimatedMealsPerDay} وجبة يومياً",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = BrandOrange
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        color = LightGraySurface
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "الإجراء القادم: ${if (isArabic) contract.nextActionAr else contract.nextActionEn}",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = BrandOrangeDark
                            )
                            Text(
                                text = "ملاحظات التفاوض: ${contract.notes}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedButton(
                        onClick = { selectedContractForUpdate = contract },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.EditNote, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = if (isArabic) "تحديث مرحلة التعاقد والتفاوض" else "Update Pipeline Stage")
                    }
                }
            }
        }
    }

    // Modal for updating status
    if (selectedContractForUpdate != null) {
        val contract = selectedContractForUpdate!!
        var newAction by remember { mutableStateOf(contract.nextActionAr) }

        AlertDialog(
            onDismissRequest = { selectedContractForUpdate = null },
            title = { Text("تحديث حالة التعاقد: ${contract.nameAr}") },
            text = {
                Column {
                    Text("اختر مرحلة التفاوض الجديدة:")
                    Spacer(modifier = Modifier.height(8.dp))
                    listOf(
                        Triple("NEGOTIATING", "تفاوض على شروط العقد وبنود السداد", "Contract Negotiations"),
                        Triple("AWAITING_REPLY", "انتظار رد مجلس الإدارة / اللجنة الفنية", "Awaiting Decision"),
                        Triple("APPROVED", "تعاقد معتمد ورسمي (جاهز للنقل للتشغيل)", "Approved Contract"),
                        Triple("REJECTED", "اعتذار أو إغلاق الفرصة", "Opportunity Closed")
                    ).forEach { (code, ar, en) ->
                        OutlinedButton(
                            onClick = {
                                viewModel.updateContractStatus(contract, code, ar, en, newAction)
                                selectedContractForUpdate = null
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(text = ar, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { selectedContractForUpdate = null }) {
                    Text("إلغاء")
                }
            }
        )
    }
}
