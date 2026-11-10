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
import com.example.data.model.CanteenItem
import com.example.data.model.FinancialRecord
import com.example.data.model.InventoryItem
import com.example.data.model.QuotationProposal
import com.example.ui.AppScreen
import com.example.ui.CateringViewModel
import com.example.ui.theme.*

@Composable
fun CanteenAdminScreen(viewModel: CateringViewModel, isArabic: Boolean) {
    val sales by viewModel.canteenSales.collectAsState()
    val totalRevenue = sales.sumOf { it.totalAmount }

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
                        text = if (isArabic) "CANTEEN & POS | إدارة الكنتِين والمبيعات" else "Canteen & Retail Operations",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = BrandOrange
                    )
                    Text(
                        text = if (isArabic) "المبيعات اليومية، الأصناف، الخصومات من الراتب" else "Daily sales, inventory & employee credit",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Summary Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "موقع الكنتِين الرئيسي", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                        Text(text = "الشركة الألمانية للأسمنت", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "إجمالي مبيعات الوردية", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                        Text(text = "${totalRevenue + 4850.0} ج.م", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = SuccessGreen)
                    }
                }
            }
        }

        item {
            Text(
                text = "سجل عمليات البيع المباشر (POS)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (sales.isEmpty()) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    color = LightGraySurface
                ) {
                    Text(
                        text = "لا توجد حركات مبيعات جديدة في السجل حالياً. يمكنك تجربة الشراء من بوابة الموظف بالشركة الألمانية للأسمنت.",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = CharcoalSecondary
                    )
                }
            }
        } else {
            items(sales) { sale ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = sale.itemNameAr, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                            Text(text = "الموظف: ${sale.employeeName} (${sale.employeeNumber})", style = MaterialTheme.typography.bodySmall, color = MutedGray)
                            Text(text = "طريقة الدفع: ${sale.paymentMethod} • ${sale.timestamp}", style = MaterialTheme.typography.labelSmall, color = BrandOrangeDark)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "${sale.totalAmount} ج.م", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = SuccessGreen)
                            Text(text = "${sale.quantity} × ${sale.unitPrice}", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InventoryFinanceScreen(viewModel: CateringViewModel, isArabic: Boolean) {
    var selectedTab by remember { mutableStateOf(0) }
    val inventory by viewModel.inventory.collectAsState()
    val finance by viewModel.financialRecords.collectAsState()
    val quotes by viewModel.quotations.collectAsState()

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
                        text = if (isArabic) "INVENTORY & FINANCE | المخزون والمالية" else "Inventory & Finance Directorate",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = BrandOrange
                    )
                    Text(
                        text = if (isArabic) "إدارة الخامات والمشتريات والقيود المحاسبية" else "Raw materials, procurement & financial ledger",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Tabs
        item {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("المخزون المركزي") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("الدفاتر المحاسبية") }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text("عروض الأسعار") }
                )
            }
        }

        if (selectedTab == 0) {
            // Inventory
            items(inventory) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Badge(containerColor = BrandOrange, contentColor = Color.White) {
                                Text(text = item.code, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                            Text(
                                text = "الرصيد: ${item.currentStock} ${item.unit}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (item.currentStock <= item.reorderThreshold) AlertRed else SuccessGreen
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = item.nameAr, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(text = "الموقع: ${item.kitchenNameAr} • التصنيف: ${item.categoryAr}", style = MaterialTheme.typography.bodySmall, color = MutedGray)
                        Text(text = "المورد المعتمد: ${item.supplierName}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Divider(modifier = Modifier.padding(vertical = 8.dp), color = BorderGray)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "حد إعادة الطلب: ${item.reorderThreshold} ${item.unit}", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                            Text(text = "سعر الوحدة: ${item.unitCost} ج.م", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = AlertRed)
                        }
                    }
                }
            }
        } else if (selectedTab == 1) {
            // Finance Records
            items(finance) { record ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = record.docNumber, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = BrandOrange)
                            val color = if (record.type == "REVENUE") SuccessGreen else AlertRed
                            Badge(containerColor = color, contentColor = Color.White) {
                                Text(text = record.typeAr, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "${record.amount} ج.م", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text(text = "الجهة: ${record.targetEntity}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                        Text(text = record.descriptionAr, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Divider(modifier = Modifier.padding(vertical = 8.dp), color = BorderGray)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "التاريخ: ${record.date}", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                            Text(text = "المحاسب المسؤول: ${record.accountantName}", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                        }
                    }
                }
            }
        } else {
            // Quotations
            items(quotes) { quote ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = quote.refCode, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = BrandOrange)
                            Badge(containerColor = InfoBlue, contentColor = Color.White) {
                                Text(text = quote.statusAr, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = quote.clientNameAr, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(text = "نوع الخدمة: ${quote.serviceTypeAr}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "المستفيدون: ${quote.beneficiariesCount} فرد • سعر الوجبة المتفق عليه: ${quote.agreedMealRate} ج.م", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)

                        Divider(modifier = Modifier.padding(vertical = 8.dp), color = BorderGray)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(text = "الإيراد الشهري المتوقع", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                                Text(text = "${quote.monthlyTotalRevenue} ج.م", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = SuccessGreen)
                            }
                            Column {
                                Text(text = "هامش الربح المتوقع (إداري)", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                                Text(text = "${String.format("%.1f", quote.projectedProfitMarginPct)}%", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = BrandOrange)
                            }
                        }
                    }
                }
            }
        }
    }
}
