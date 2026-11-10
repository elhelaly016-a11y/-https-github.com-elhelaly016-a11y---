package com.example.ui.employee

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.MealItem
import com.example.data.model.UserAccount
import com.example.ui.CateringViewModel
import com.example.ui.components.JaggedTicketShape
import com.example.ui.theme.*

@Composable
fun EmployeePortalScreen(
    viewModel: CateringViewModel,
    currentUser: UserAccount,
    isArabic: Boolean
) {
    var selectedBottomTab by remember { mutableStateOf(0) }
    val allMeals by viewModel.allMeals.collectAsState()
    val canteenSales by viewModel.canteenSales.collectAsState()
    val coupons by viewModel.coupons.collectAsState()
    val seasonalOffers by viewModel.seasonalOffers.collectAsState()
    val tickets by viewModel.allTickets.collectAsState()

    // Strictly isolated meals for this employee's site or global
    val siteMeals = remember(allMeals, currentUser.siteId) {
        allMeals.filter { it.siteId == "ALL" || it.siteId == currentUser.siteId }
    }

    // Site Canteen Products
    val canteenItems by viewModel.repository.getCanteenItems(currentUser.siteId).collectAsState(initial = emptyList())

    // Site tickets
    val siteTickets = remember(tickets, currentUser.employeeNumber) {
        tickets.filter { it.employeeNumber == currentUser.employeeNumber || it.siteId == currentUser.siteId }
    }

    var showComplaintDialog by remember { mutableStateOf(false) }
    var showPurchaseSuccessToast by remember { mutableStateOf<String?>(null) }
    var showCouponClaimedToast by remember { mutableStateOf<String?>(null) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedBottomTab == 0,
                    onClick = { selectedBottomTab = 0 },
                    icon = { Icon(Icons.Default.RestaurantMenu, contentDescription = null) },
                    label = { Text("المنيو المعتمد", style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BrandOrange,
                        selectedTextColor = BrandOrange,
                        indicatorColor = BrandOrange.copy(alpha = 0.15f)
                    )
                )

                if (currentUser.siteId == "GERMAN_CEMENT") {
                    NavigationBarItem(
                        selected = selectedBottomTab == 1,
                        onClick = { selectedBottomTab = 1 },
                        icon = { Icon(Icons.Default.Storefront, contentDescription = null) },
                        label = { Text("الكنتِين", style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BrandOrange,
                            selectedTextColor = BrandOrange,
                            indicatorColor = BrandOrange.copy(alpha = 0.15f)
                        )
                    )
                }

                NavigationBarItem(
                    selected = selectedBottomTab == 2,
                    onClick = { selectedBottomTab = 2 },
                    icon = { Icon(Icons.Default.ConfirmationNumber, contentDescription = null) },
                    label = { Text("العروض والكوبونات", style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BrandOrange,
                        selectedTextColor = BrandOrange,
                        indicatorColor = BrandOrange.copy(alpha = 0.15f)
                    )
                )

                NavigationBarItem(
                    selected = selectedBottomTab == 3,
                    onClick = { selectedBottomTab = 3 },
                    icon = { Icon(Icons.Default.SupportAgent, contentDescription = null) },
                    label = { Text("الشكاوى والدعم", style = MaterialTheme.typography.labelSmall) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BrandOrange,
                        selectedTextColor = BrandOrange,
                        indicatorColor = BrandOrange.copy(alpha = 0.15f)
                    )
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Employee Profile & Site Banner
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
                                    listOf(BrandOrange.copy(alpha = 0.12f), LightGraySurface)
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = currentUser.fullNameAr,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "الرقم الوظيفي: ${currentUser.employeeNumber} • ${currentUser.departmentAr}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Badge(containerColor = SuccessGreen, contentColor = Color.White) {
                                    Text(
                                        text = "حساب نشط",
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Divider(color = BorderGray)
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = "الموقع المعتمد لك", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                                    val siteName = when (currentUser.siteId) {
                                        "GERMAN_CEMENT" -> "الشركة الألمانية للأسمنت"
                                        "ROYAL_HOSPITAL" -> "مستشفى رويال"
                                        else -> "مستشفى الشروق"
                                    }
                                    Text(text = siteName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = BrandOrangeDark)
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(text = "رصيد الكاش باك المكتسب", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                                    Text(
                                        text = "${String.format("%.2f", currentUser.cashbackBalance)} ج.م",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = SuccessGreen
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Notification Toasts
            if (showPurchaseSuccessToast != null) {
                item {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = SuccessGreenLight,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = showPurchaseSuccessToast!!, style = MaterialTheme.typography.bodySmall, color = SuccessGreen, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            if (showCouponClaimedToast != null) {
                item {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = BrandOrangeGlow,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.CardGiftcard, contentDescription = null, tint = BrandOrange)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = showCouponClaimedToast!!, style = MaterialTheme.typography.bodySmall, color = BrandOrangeDark, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // TAB 0: Approved Site Menus
            if (selectedBottomTab == 0) {
                item {
                    Text(
                        text = "قائمة وجبات موقعك المعتمدة اليوم",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                items(siteMeals) { meal ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(2.dp, RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Badge(containerColor = BrandOrange, contentColor = Color.White) {
                                        Text(text = meal.code, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), fontWeight = FontWeight.Bold)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = meal.category.titleAr, style = MaterialTheme.typography.labelSmall, color = BrandOrangeDark)
                                }

                                Text(
                                    text = "${meal.approvedRetailPrice} ج.م",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessGreen
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = meal.nameAr, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "المكونات: ${meal.ingredientsAr}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                            Divider(modifier = Modifier.padding(vertical = 10.dp), color = BorderGray)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "الحصة: ${meal.portionSize} • ${meal.calories} سعر حراري",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MutedGray
                                )

                                Badge(containerColor = SuccessGreenLight, contentColor = SuccessGreen) {
                                    Text(text = "وجبة جاهزة وطازجة", modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                }
            }

            // TAB 1: Canteen (for German Cement)
            if (selectedBottomTab == 1 && currentUser.siteId == "GERMAN_CEMENT") {
                item {
                    Text(
                        text = "كنتِين الموقع — مأكولات ومشروبات سريعة",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                items(canteenItems) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = item.nameAr, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                                Text(text = "التصنيف: ${item.categoryAr} • المتبقي: ${item.stockQuantity} ${item.unitAr}", style = MaterialTheme.typography.labelSmall, color = MutedGray)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "${item.approvedSalePrice} ج.م", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = SuccessGreen)
                            }

                            Button(
                                onClick = {
                                    viewModel.recordCanteenSale(
                                        itemCode = item.code,
                                        itemNameAr = item.nameAr,
                                        quantity = 1,
                                        unitPrice = item.approvedSalePrice,
                                        paymentMethod = "خصم من الراتب"
                                    )
                                    showPurchaseSuccessToast = "تم تسجيل طلبك بنجاح بخصم من الراتب وحصلت على 5% كاش باك!"
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = BrandOrange)
                            ) {
                                Text("طلب فوري")
                            }
                        }
                    }
                }
            }

            // TAB 2: Coupons & Seasonal Offers
            if (selectedBottomTab == 2) {
                item {
                    Text(text = "كوبونات الخصم المشرشرة لعمال وعملاء الشركة", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }

                items(coupons) { coupon ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(3.dp, shape = JaggedTicketShape(notchRadius = 12f, toothCount = 7)),
                        shape = JaggedTicketShape(notchRadius = 12f, toothCount = 7),
                        color = MaterialTheme.colorScheme.surface,
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, BrandOrange)
                    ) {
                        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Badge(containerColor = BrandOrange, contentColor = Color.White) {
                                    Text(text = coupon.code, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), fontWeight = FontWeight.Bold)
                                }
                                Text(
                                    text = if (coupon.discountPercent > 0) "خصم ${coupon.discountPercent}%" else "${coupon.discountFixedAmount} ج.م",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = BrandOrange
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = coupon.titleAr, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(text = coupon.descriptionAr, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = {
                                    showCouponClaimedToast = "تم تفعيل الكوبون [${coupon.code}] لحسابك بنجاح، سيطبق على طلبك القادم."
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = BrandOrange)
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("استخدام الكوبون الآن")
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "عروض المواسم — خصومات خاصة حتى 10%", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }

                items(seasonalOffers) { offer ->
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
                                Text(text = offer.titleAr, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                                Text(text = "${offer.priceEgp} ج.م", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = SuccessGreen)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = offer.descriptionAr, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = offer.pledgeAr, style = MaterialTheme.typography.labelSmall, color = BrandOrangeDark, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            // TAB 3: Complaints & Support
            if (selectedBottomTab == 3) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "الشكاوى والاقتراحات", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Button(
                            onClick = { showComplaintDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = BrandOrange)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("تقديم طلب جديد")
                        }
                    }
                }

                items(siteTickets) { ticket ->
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
                                Text(text = ticket.ticketNumber, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = BrandOrange)
                                val color = if (ticket.status == "RESOLVED") SuccessGreen else WarningAmber
                                Badge(containerColor = color, contentColor = Color.White) {
                                    Text(text = ticket.statusAr, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = ticket.complaintTypeAr, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(text = ticket.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)

                            if (ticket.resolutionNotes.isNotBlank()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = "رد الإدارة: ${ticket.resolutionNotes}", style = MaterialTheme.typography.labelSmall, color = SuccessGreen, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showComplaintDialog) {
        var complaintType by remember { mutableStateOf("اقتراح إضافة صنف للمنيو") }
        var description by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showComplaintDialog = false },
            title = { Text("تقديم شكوى أو اقتراح لإدارة الإعاشة") },
            text = {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("نوع البلاغ:")
                    listOf("اقتراح إضافة صنف للمنيو", "ملاحظة توقيت تسليم الوجبة", "ملاحظة تغليف وحرارة الوجبة", "خدمة الكنتِين").forEach { type ->
                        FilterChip(
                            selected = complaintType == type,
                            onClick = { complaintType = type },
                            label = { Text(type) }
                        )
                    }
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("تفاصيل الملاحظة أو الاقتراح") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (description.isNotBlank()) {
                            viewModel.submitTicket(complaintType, complaintType, description)
                            showComplaintDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BrandOrange)
                ) {
                    Text("إرسال الطلب")
                }
            },
            dismissButton = {
                TextButton(onClick = { showComplaintDialog = false }) { Text("إلغاء") }
            }
        )
    }
}
