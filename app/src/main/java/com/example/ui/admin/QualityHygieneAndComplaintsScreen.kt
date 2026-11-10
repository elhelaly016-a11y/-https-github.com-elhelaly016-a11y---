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
import com.example.data.model.HygieneChecklist
import com.example.data.model.QualityAudit
import com.example.data.model.SupportTicket
import com.example.ui.AppScreen
import com.example.ui.CateringViewModel
import com.example.ui.theme.*

@Composable
fun QualityHygieneScreen(viewModel: CateringViewModel, isArabic: Boolean) {
    val qualityAudits by viewModel.qualityAudits.collectAsState()
    val hygieneLogs by viewModel.hygieneChecklists.collectAsState()
    var showAddAuditDialog by remember { mutableStateOf(false) }

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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { viewModel.navigateTo(AppScreen.ADMIN_DASHBOARD) }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                    Column {
                        Text(
                            text = if (isArabic) "QUALITY & HYGIENE | الجودة والهايجين" else "Quality & Food Safety Assurance",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = BrandOrange
                        )
                        Text(
                            text = if (isArabic) "فحص عينات الوجبات، درجات الحرارة، معايير الأيزو" else "Meal sample temps, ISO 22000 & inspections",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Button(
                    onClick = { showAddAuditDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = BrandOrange)
                ) {
                    Icon(imageVector = Icons.Default.PlaylistAddCheck, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = if (isArabic) "فحص جديد" else "New Audit")
                }
            }
        }

        item {
            Text(text = "سجلات فحص جودة الوجبات والعينات الحية", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }

        items(qualityAudits) { audit ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = audit.mealSampleChecked, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Badge(containerColor = SuccessGreen, contentColor = Color.White) {
                            Text(text = audit.overallRatingAr, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "الموقع / المنشأة: ${audit.kitchenOrSiteName}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(text = "المفتش المسؤول: ${audit.inspectorName} • التاريخ: ${audit.inspectionDate}", style = MaterialTheme.typography.bodySmall, color = MutedGray)

                    Divider(modifier = Modifier.padding(vertical = 10.dp), color = BorderGray)

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(text = "درجة الحرارة المقاسة", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                            Text(text = "${audit.temperatureCelsius}°C", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = BrandOrange)
                        }
                        Column {
                            Text(text = "التقييم الحسي", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                            Text(text = "${audit.sensoryScore} / 5 نجوم", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = SuccessGreen)
                        }
                        Column {
                            Text(text = "مطابقة التعبئة والتغليف", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                            Text(text = if (audit.packagingCompliance) "مطابق 100%" else "مخالف", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = SuccessGreen)
                        }
                    }

                    if (audit.correctiveActionTaken.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SuccessGreenLight,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "الإجراء المتبع: ${audit.correctiveActionTaken}",
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = SuccessGreen,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "قوائم الفحص اليومي للهايجين وسلامة الغذاء بالمطابخ", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }

        items(hygieneLogs) { log ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = log.targetFacility, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text(text = "مسؤول الهايجين: ${log.officerName} • التاريخ: ${log.date}", style = MaterialTheme.typography.bodySmall, color = MutedGray)

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SuggestionChip(
                            onClick = {},
                            label = { Text("نظافة المطبخ: ${log.kitchenCleanlinessScore}%") },
                            colors = SuggestionChipDefaults.suggestionChipColors(containerColor = SuccessGreenLight)
                        )
                        SuggestionChip(
                            onClick = {},
                            label = { Text("تطهير المعدات: ${log.equipmentSanitizationScore}%") },
                            colors = SuggestionChipDefaults.suggestionChipColors(containerColor = SuccessGreenLight)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "ملاحظات وتنبيهات المخاطر: ${log.hazardAlerts}", style = MaterialTheme.typography.bodySmall, color = BrandOrangeDark)
                }
            }
        }
    }

    if (showAddAuditDialog) {
        var kitchenName by remember { mutableStateOf("مطبخ هنجر استيشن المركزي") }
        var sampleName by remember { mutableStateOf("وجبة كفتة مشوية LZ-02") }
        var temp by remember { mutableStateOf("72.0") }
        var sensory by remember { mutableStateOf("5") }

        AlertDialog(
            onDismissRequest = { showAddAuditDialog = false },
            title = { Text("تسجيل فحص جودة وهايجين جديد") },
            text = {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = kitchenName, onValueChange = { kitchenName = it }, label = { Text("المنشأة أو المطبخ") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = sampleName, onValueChange = { sampleName = it }, label = { Text("عينة الوجبة المفحوصة") }, modifier = Modifier.fillMaxWidth())
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(value = temp, onValueChange = { temp = it }, label = { Text("درجة الحرارة (°C)") }, modifier = Modifier.weight(1f))
                        OutlinedTextField(value = sensory, onValueChange = { sensory = it }, label = { Text("التقييم الحسي (1-5)") }, modifier = Modifier.weight(1f))
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.addQualityAudit(
                            kitchenOrSiteName = kitchenName,
                            mealSample = sampleName,
                            tempCelsius = temp.toDoubleOrNull() ?: 70.0,
                            sensoryScore = sensory.toIntOrNull() ?: 5,
                            packagingOk = true,
                            nonConformity = "",
                            correctiveAction = "تم الفحص الدوري ومطابق تماماً للمواصفات"
                        )
                        showAddAuditDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BrandOrange)
                ) {
                    Text("حفظ تقرير الفحص")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddAuditDialog = false }) { Text("إلغاء") }
            }
        )
    }
}

@Composable
fun AdminComplaintsScreen(viewModel: CateringViewModel, isArabic: Boolean) {
    val tickets by viewModel.allTickets.collectAsState()

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
                        text = if (isArabic) "SUPPORT & COMPLAINTS | الشكاوى وخدمة المواقع" else "Complaints & Client Support",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = BrandOrange
                    )
                    Text(
                        text = if (isArabic) "متابعة بلاغات المواقع والعاملين والحلول السريعة" else "Issue tracking, SLAs & corrective actions",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(tickets) { ticket ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = ticket.ticketNumber, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = BrandOrange)
                        val color = if (ticket.status == "RESOLVED") SuccessGreen else WarningAmber
                        Badge(containerColor = color, contentColor = Color.White) {
                            Text(text = ticket.statusAr, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "نوع الطلب/البلاغ: ${ticket.complaintTypeAr}", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text(text = "الموقع: ${ticket.siteNameAr} • مقدم الطلب: ${ticket.employeeName} (${ticket.employeeNumber})", style = MaterialTheme.typography.bodySmall, color = MutedGray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "الوصف: ${ticket.description}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)

                    if (ticket.resolutionNotes.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = LightGraySurface,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "ملاحظات المعالجة: ${ticket.resolutionNotes}",
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = BrandOrangeDark
                            )
                        }
                    }

                    if (ticket.status != "RESOLVED") {
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                                viewModel.updateTicketStatus(
                                    ticketId = ticket.id,
                                    status = "RESOLVED",
                                    statusAr = "تم الحل والمتابعة بنجاح",
                                    statusEn = "Resolved"
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                        ) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("اعتماد حل الشكوى وإغلاق البلاغ")
                        }
                    }
                }
            }
        }
    }
}
