package com.example.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppScreen
import com.example.ui.CateringViewModel
import com.example.ui.components.KpiCard
import com.example.ui.theme.*

data class AdminNavTile(
    val titleAr: String,
    val titleEn: String,
    val subtitleAr: String,
    val subtitleEn: String,
    val icon: ImageVector,
    val targetScreen: AppScreen,
    val color: Color = BrandOrange
)

@Composable
fun AdminDashboardScreen(
    viewModel: CateringViewModel,
    isArabic: Boolean
) {
    val navTiles = listOf(
        AdminNavTile("المطابخ المركزية", "Central Kitchens", "3 مطابخ بطاقة 16 ألف وجبة", "3 kitchens (16k cap)", Icons.Default.Kitchen, AppScreen.ADMIN_KITCHENS, BrandOrange),
        AdminNavTile("العملاء والمواقع", "Active Sites", "رويال، الألمانية للأسمنت، الشروق", "Royal, German Cement, Shorouk", Icons.Default.Business, AppScreen.ADMIN_SITES, InfoBlue),
        AdminNavTile("جهات تحت الانتظار", "Pending Pipeline", "نجوى، مطبعة الشروق، مستر مينا", "5 opportunities in progress", Icons.Default.PendingActions, AppScreen.ADMIN_PENDING, WarningAmber),
        AdminNavTile("المنيوهات والوجبات", "Menu Management", "أكواد LZ-01..05 وحميات ومكونات", "Codes LZ-01..05 & diets", Icons.Default.RestaurantMenu, AppScreen.ADMIN_MENUS, BrandOrangeDark),
        AdminNavTile("الطلبات والتوريدات", "Orders & Supply", "متابعة دورة الطلب والتحضير", "Supply lifecycle tracking", Icons.Default.LocalShipping, AppScreen.ADMIN_ORDERS, SuccessGreen),
        AdminNavTile("الكنتِين والمبيعات", "Canteen & POS", "حركة البيع، المخزون، الآجل", "Retail, stock & employee credit", Icons.Default.Storefront, AppScreen.ADMIN_CANTEEN, BrandOrange),
        AdminNavTile("المخزون والمشتريات", "Inventory & POs", "الخامات، اللحوم، حد الطلب", "Raw materials & threshold alerts", Icons.Default.Inventory2, AppScreen.ADMIN_INVENTORY, CharcoalSecondary),
        AdminNavTile("الحسابات والمالية", "Finance & Ledger", "الإيرادات، المصروفات، التحصيل", "Revenues, vouchers & margins", Icons.Default.AccountBalance, AppScreen.ADMIN_FINANCE, SuccessGreen),
        AdminNavTile("عروض الأسعار", "Quotations Engine", "إعداد عروض تجارية بالعربية والإنجليزية", "Bilingual quotation generator", Icons.Default.Description, AppScreen.ADMIN_QUOTATIONS, InfoBlue),
        AdminNavTile("الكوبونات والكاش باك", "Coupons & Cashback", "بطاقات مشرشرة وأكواد فريدة", "Jagged promo cards & rewards", Icons.Default.ConfirmationNumber, AppScreen.ADMIN_COUPONS, BrandOrange),
        AdminNavTile("عروض المواسم", "Seasonal Offers", "كرتونة رمضان، كحك العيد، لحوم الأضحى", "Ramadan box, Eid packs, dates", Icons.Default.Celebration, AppScreen.ADMIN_SEASONAL, WarningAmber),
        AdminNavTile("الجودة والهايجين", "Quality & Hygiene", "فحص العينات، الحرارة، سلامة الغذاء", "HACCP & temperature audits", Icons.Default.HealthAndSafety, AppScreen.ADMIN_QUALITY_HYGIENE, SuccessGreen),
        AdminNavTile("الشكاوى والدعم", "Support & Tickets", "متابعة بلاغات المواقع والعاملين", "SLA tracking & resolution", Icons.Default.SupportAgent, AppScreen.ADMIN_COMPLAINTS, AlertRed),
        AdminNavTile("المبادرات السنوية", "CSR & Annual Rel.", "الهلال الأحمر، حياة كريمة، د. مصطفى محمود", "Red Crescent & Haya Karima", Icons.Default.VolunteerActivism, AppScreen.ADMIN_CSR, InfoBlue),
        AdminNavTile("التقارير والطباعة", "Reports & Export", "تقارير تشغيلية ومالية قابلة للطباعة", "Printable daily & financial reports", Icons.Default.Print, AppScreen.ADMIN_REPORTS, CharcoalText)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(BrandOrange.copy(alpha = 0.15f), BrandOrangeGlow.copy(alpha = 0.05f))
                            )
                        )
                        .padding(18.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Badge(containerColor = BrandOrange, contentColor = Color.White) {
                                Text(
                                    text = if (isArabic) "لوحة القيادة التنفيذية المركزية" else "Executive KPI Dashboard",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "2026-11-10",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = if (isArabic)
                                "نظام التشغيل والإعاشة وإدارة المطابخ المركزية"
                            else
                                "National Food Service & Kitchen Management Platform",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = if (isArabic)
                                "متابعة حية للإنتاج اليومي في (هنجر استيشن، مدينة بدر، اللاهون ببني سويف) ومواقع المستشفيات والشركات."
                            else
                                "Live monitoring across Hanger Station, Badr City, Lahoon kitchen & active hospital/industrial sites.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Gemini AI Smart Assistant Banner
        item {
            var showGeminiModal by remember { mutableStateOf(false) }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { showGeminiModal = true },
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.5.dp, BrandOrange)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    Brush.linearGradient(listOf(BrandOrange, Color(0xFF673AB7)))
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (isArabic) "مستشار التشغيل والجودة الذكي" else "Smart Catering & QA Advisor",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Badge(containerColor = Color(0xFF673AB7), contentColor = Color.White) {
                                    Text("Gemini", modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                                }
                            }
                            Text(
                                text = if (isArabic) "توليد الحميات، تحليل الهايجين وتخطيط التكاليف فورياً" else "Instant AI diets, HACCP checks & feasibility",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = BrandOrange)
                }
            }

            if (showGeminiModal) {
                com.example.ui.components.GeminiAiDialog(
                    viewModel = viewModel,
                    isArabic = isArabic,
                    onDismiss = { showGeminiModal = false }
                )
            }
        }

        // Executive KPIs
        item {
            Text(
                text = if (isArabic) "المؤشرات التشغيلية الحية اليوم" else "Live Today's Operational KPIs",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                KpiCard(
                    title = if (isArabic) "إجمالي الإنتاج اليومي" else "Today's Production",
                    value = "10,170",
                    subtitle = if (isArabic) "وجبة جاهزة للشحن" else "meals produced",
                    icon = Icons.Default.Restaurant,
                    color = BrandOrange,
                    modifier = Modifier.weight(1f)
                )
                KpiCard(
                    title = if (isArabic) "المطابخ المركزية" else "Active Kitchens",
                    value = "3 / 3",
                    subtitle = if (isArabic) "تعمل بكامل الطاقة" else "100% capacity",
                    icon = Icons.Default.Kitchen,
                    color = SuccessGreen,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                KpiCard(
                    title = if (isArabic) "المواقع النشطة" else "Active Sites",
                    value = "3 مواقع",
                    subtitle = if (isArabic) "رويال، الألمانية، الشروق" else "Hospital & Cement",
                    icon = Icons.Default.Domain,
                    color = InfoBlue,
                    modifier = Modifier.weight(1f)
                )
                KpiCard(
                    title = if (isArabic) "فرص تحت الانتظار" else "Pending Pipeline",
                    value = "5 جهات",
                    subtitle = if (isArabic) "قيد العروض والتفاوض" else "in negotiation",
                    icon = Icons.Default.Handshake,
                    color = WarningAmber,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                KpiCard(
                    title = if (isArabic) "مؤشر الهايجين والجودة" else "Hygiene & QA Score",
                    value = "98.2%",
                    subtitle = if (isArabic) "مطابق للأيزو 22000" else "ISO compliant",
                    icon = Icons.Default.Verified,
                    color = SuccessGreen,
                    modifier = Modifier.weight(1f)
                )
                KpiCard(
                    title = if (isArabic) "الشكاوى المفتوحة" else "Open Tickets",
                    value = "1",
                    subtitle = if (isArabic) "قيد المعالجة (تطوير منيو)" else "in progress SLA",
                    icon = Icons.Default.PendingActions,
                    color = AlertRed,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Section Title: Quick Actions & Management Modules
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isArabic) "أقسام الإدارة والتشغيل (21 وحدة متكاملة)" else "Management & Operations Modules (21 Units)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Grid of Management Modules
        items(navTiles.chunked(2)) { pair ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                pair.forEach { tile ->
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .shadow(2.dp, RoundedCornerShape(14.dp))
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { viewModel.navigateTo(tile.targetScreen) },
                        color = MaterialTheme.colorScheme.surface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(tile.color.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = tile.icon, contentDescription = null, tint = tile.color, modifier = Modifier.size(24.dp))
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = if (isArabic) tile.titleAr else tile.titleEn,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isArabic) tile.subtitleAr else tile.subtitleEn,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2
                            )
                        }
                    }
                }
                if (pair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
