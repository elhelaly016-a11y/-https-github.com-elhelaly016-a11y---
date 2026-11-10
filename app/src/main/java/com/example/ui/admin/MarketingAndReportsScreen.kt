package com.example.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.data.model.CouponItem
import com.example.data.model.CsrInitiative
import com.example.data.model.SeasonalOffer
import com.example.ui.AppScreen
import com.example.ui.CateringViewModel
import com.example.ui.components.JaggedTicketShape
import com.example.ui.theme.*

@Composable
fun MarketingAdminScreen(viewModel: CateringViewModel, isArabic: Boolean) {
    var selectedTab by remember { mutableStateOf(0) }
    val coupons by viewModel.coupons.collectAsState()
    val seasonal by viewModel.seasonalOffers.collectAsState()
    val csr by viewModel.csrInitiatives.collectAsState()

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
                        text = if (isArabic) "MARKETING & SEASONAL | التسويق والمواسم" else "Marketing, Coupons & Seasonal Offers",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = BrandOrange
                    )
                    Text(
                        text = if (isArabic) "الكوبونات المشرشرة، عروض المواسم، المبادرات السنوية" else "Jagged vouchers, seasonal campaigns & CSR",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("الكوبونات والكاش باك") })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("عروض المواسم") })
                Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }, text = { Text("المبادرات والمسؤولية") })
            }
        }

        if (selectedTab == 0) {
            items(coupons) { coupon ->
                // Jagged ticket coupon
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, shape = JaggedTicketShape(notchRadius = 14f, toothCount = 8)),
                    shape = JaggedTicketShape(notchRadius = 14f, toothCount = 8),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, BrandOrange)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 22.dp, vertical = 18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Badge(containerColor = BrandOrange, contentColor = Color.White) {
                                Text(
                                    text = coupon.code,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }

                            Text(
                                text = if (coupon.discountPercent > 0) "خصم ${coupon.discountPercent}%" else "خصم ${coupon.discountFixedAmount} ج.م",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = BrandOrange
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = coupon.titleAr, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(text = coupon.descriptionAr, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Divider(modifier = Modifier.padding(vertical = 10.dp), color = BorderGray)

                        Text(text = "الشروط: ${coupon.termsAr}", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                        Text(text = "صالح حتى: ${coupon.validUntil}", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = BrandOrangeDark)
                    }
                }
            }
        } else if (selectedTab == 1) {
            // Seasonal Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = BrandOrange.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "جودة تستحق الثقة — عروض خاصة لعملاء الوطنية اللوجستية.",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = BrandOrangeDark
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "خصومات تصل إلى 10% للعملاء التابعين للشركة على كراتين رمضان وحلويات الأعياد واللحوم البلدية المعتمدة.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            items(seasonal) { offer ->
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
                            Text(text = offer.titleAr, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(text = "${offer.priceEgp} ج.م", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = SuccessGreen)
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = offer.subtitleAr, style = MaterialTheme.typography.labelSmall, color = BrandOrange)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = offer.descriptionAr, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Divider(modifier = Modifier.padding(vertical = 10.dp), color = BorderGray)

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = offer.discountTextAr, style = MaterialTheme.typography.labelSmall, color = SuccessGreen, fontWeight = FontWeight.Bold)
                            Text(text = offer.availabilityStatusAr, style = MaterialTheme.typography.labelSmall, color = BrandOrangeDark)
                        }
                    }
                }
            }
        } else {
            // CSR
            items(csr) { item ->
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
                            Text(text = item.entityNameAr, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = BrandOrange)
                            Badge(containerColor = InfoBlue, contentColor = Color.White) {
                                Text(text = item.verifiedPartnerStatusAr, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = item.initiativeTitleAr, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                        Text(text = "نوع الحملة: ${item.campaignTypeAr}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "الحجم السنوي المستهدف: ${item.targetVolumeMeals} وجبة إغاثة وتغذية", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = SuccessGreen)

                        Divider(modifier = Modifier.padding(vertical = 10.dp), color = BorderGray)
                        Text(text = "جدول التوزيع: ${item.distributionScheduleAr}", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                        Text(text = "ملاحظات التوريد: ${item.notesAr}", style = MaterialTheme.typography.labelSmall, color = CharcoalSecondary)
                    }
                }
            }
        }
    }
}

@Composable
fun ReportsScreen(viewModel: CateringViewModel, isArabic: Boolean) {
    var showExportToast by remember { mutableStateOf(false) }

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
                        text = if (isArabic) "REPORTS & ANALYTICS | التقارير والتحليلات" else "Operational Reports & Export",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = BrandOrange
                    )
                    Text(
                        text = if (isArabic) "تقارير الإنتاج، التوريد، المالية، والهايجين" else "Production, supply, finance & QA reports",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "تقرير التشغيل الموحد لليوم (2026-11-10)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "• إجمالي الوجبات المطهوة اليوم: 10,170 وجبة (هنجر 3,850 + بدر 2,920 + الريان 3,400)", style = MaterialTheme.typography.bodySmall)
                    Text(text = "• إجمالي التوريدات المنفذة للمواقع: 2,700 وجبة معتمدة ومسلمة", style = MaterialTheme.typography.bodySmall)
                    Text(text = "• مؤشر مطابقة الهايجين وسلامة الغذاء: 98.2% (خالي تماماً من المخالفات الحرجة)", style = MaterialTheme.typography.bodySmall)
                    Text(text = "• إجمالي الفواتير والمتحصلات المسجلة: 895,000 ج.م", style = MaterialTheme.typography.bodySmall)
                    Text(text = "• حالة الشكاوى: 1 قيد المعالجة (اقتراح منيو)، 1 تم حلها نهائياً", style = MaterialTheme.typography.bodySmall)

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showExportToast = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandOrange)
                    ) {
                        Icon(imageVector = Icons.Default.PictureAsPdf, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("تصدير وطباعة تقرير الإدارة العليا (PDF / Excel)")
                    }

                    if (showExportToast) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "تم تجهيز التقرير الرقمي الرسمي بنجاح وحفظه في سجل العمليات.",
                            style = MaterialTheme.typography.labelSmall,
                            color = SuccessGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
