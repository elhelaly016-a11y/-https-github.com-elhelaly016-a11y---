package com.example.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.data.model.CateringOrder
import com.example.data.model.MealCategory
import com.example.data.model.MealItem
import com.example.data.model.OrderLifecycle
import com.example.ui.AppScreen
import com.example.ui.CateringViewModel
import com.example.ui.theme.*

@Composable
fun MenuManagementScreen(viewModel: CateringViewModel, isArabic: Boolean) {
    val meals by viewModel.allMeals.collectAsState()
    var selectedCategory by remember { mutableStateOf<MealCategory?>(null) }
    var showAddMealDialog by remember { mutableStateOf(false) }

    val filteredMeals = remember(meals, selectedCategory) {
        if (selectedCategory == null) meals else meals.filter { it.category == selectedCategory }
    }

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
                            text = if (isArabic) "MENU MANAGEMENT | إدارة المنيوهات والوجبات" else "Menu & Recipe Management",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = BrandOrange
                        )
                        Text(
                            text = if (isArabic) "أكواد الوجبات LZ-01..05 وحميات المستشفيات والتكلفة" else "Meal items, codes, nutrition & cost",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Button(
                    onClick = { showAddMealDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = BrandOrange)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = if (isArabic) "إضافة وجبة" else "Add Meal")
                }
            }
        }

        // Category Filter Chips
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    FilterChip(
                        selected = selectedCategory == null,
                        onClick = { selectedCategory = null },
                        label = { Text(if (isArabic) "الكل (${meals.size})" else "All") }
                    )
                }
                items(MealCategory.values()) { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        label = { Text(if (isArabic) cat.titleAr else cat.titleEn) }
                    )
                }
            }
        }

        items(filteredMeals) { meal ->
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Badge(containerColor = BrandOrange, contentColor = Color.White) {
                                Text(
                                    text = meal.code,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isArabic) meal.category.titleAr else meal.category.titleEn,
                                style = MaterialTheme.typography.labelSmall,
                                color = BrandOrangeDark
                            )
                        }

                        Text(
                            text = "${meal.approvedRetailPrice} ج.م",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SuccessGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isArabic) meal.nameAr else meal.nameEn,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "المكونات: ${meal.ingredientsAr}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Divider(modifier = Modifier.padding(vertical = 10.dp), color = BorderGray)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "الحصة: ${meal.portionSize} • السعرات: ${meal.calories} ك.س",
                            style = MaterialTheme.typography.labelSmall,
                            color = MutedGray
                        )

                        // Internal cost (ADMIN ONLY)
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = AlertRedLight
                        ) {
                            Text(
                                text = "التكلفة الداخلية: ${meal.internalCostPrice} ج.م (سرية)",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = AlertRed,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }

    if (showAddMealDialog) {
        var code by remember { mutableStateOf("") }
        var nameAr by remember { mutableStateOf("") }
        var category by remember { mutableStateOf(MealCategory.HOT_MEAL) }
        var ingredients by remember { mutableStateOf("") }
        var portion by remember { mutableStateOf("450 جم") }
        var calories by remember { mutableStateOf("600") }
        var retailPrice by remember { mutableStateOf("120") }
        var costPrice by remember { mutableStateOf("75") }

        AlertDialog(
            onDismissRequest = { showAddMealDialog = false },
            title = { Text("إضافة وجبة أو صنف جديد للمنيو") },
            text = {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = code, onValueChange = { code = it }, label = { Text("كود الوجبة (مثلاً: LZ-06 أو GC-03)") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = nameAr, onValueChange = { nameAr = it }, label = { Text("اسم الوجبة بالعربية") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = ingredients, onValueChange = { ingredients = it }, label = { Text("المكونات والمواصفات") }, modifier = Modifier.fillMaxWidth())
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(value = retailPrice, onValueChange = { retailPrice = it }, label = { Text("سعر البيع (ج.م)") }, modifier = Modifier.weight(1f))
                        OutlinedTextField(value = costPrice, onValueChange = { costPrice = it }, label = { Text("التكلفة الداخلية (ج.م)") }, modifier = Modifier.weight(1f))
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (code.isNotBlank() && nameAr.isNotBlank()) {
                            viewModel.addNewMeal(
                                code = code,
                                nameAr = nameAr,
                                nameEn = nameAr,
                                category = category,
                                ingredientsAr = ingredients,
                                portionSize = portion,
                                calories = calories.toIntOrNull() ?: 600,
                                retailPrice = retailPrice.toDoubleOrNull() ?: 100.0,
                                costPrice = costPrice.toDoubleOrNull() ?: 60.0,
                                siteId = "ALL",
                                kitchenId = "KITCHEN_HANGER"
                            )
                            showAddMealDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BrandOrange)
                ) {
                    Text("حفظ الصنف")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddMealDialog = false }) { Text("إلغاء") }
            }
        )
    }
}

@Composable
fun OrdersScreen(viewModel: CateringViewModel, isArabic: Boolean) {
    val orders by viewModel.allOrders.collectAsState()
    var showCreateOrderDialog by remember { mutableStateOf(false) }

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
                            text = if (isArabic) "ORDERS & SUPPLY | الطلبات والتوريدات" else "Orders & Supply Lifecycle",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = BrandOrange
                        )
                        Text(
                            text = if (isArabic) "دورة التجهيز والتوريد والتسليم للمواقع" else "Live order supply lifecycle tracking",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Button(
                    onClick = { showCreateOrderDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = BrandOrange)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = if (isArabic) "أمر توريد" else "New Order")
                }
            }
        }

        items(orders) { order ->
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
                            text = order.orderNumber,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = BrandOrange
                        )

                        val statusColor = when (order.status) {
                            OrderLifecycle.COMPLETED, OrderLifecycle.DELIVERED -> SuccessGreen
                            OrderLifecycle.IN_PREPARATION, OrderLifecycle.APPROVED -> BrandOrange
                            OrderLifecycle.READY -> InfoBlue
                            OrderLifecycle.REJECTED, OrderLifecycle.CANCELLED -> AlertRed
                            else -> WarningAmber
                        }

                        Badge(containerColor = statusColor, contentColor = Color.White) {
                            Text(
                                text = if (isArabic) order.status.titleAr else order.status.titleEn,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "الموقع المستلم: ${if (isArabic) order.siteNameAr else order.siteNameEn}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "المطبخ المسؤول: ${if (isArabic) order.kitchenNameAr else order.kitchenNameAr}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "تفاصيل الوجبات: ${order.mealDetailsSummary}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "الكمية: ${order.quantity} وجبة • الموعد: ${order.deliveryTime}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MutedGray
                        )
                        Text(
                            text = "الإجمالي: ${order.totalAmount} ج.م",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = SuccessGreen
                        )
                    }

                    Divider(modifier = Modifier.padding(vertical = 10.dp), color = BorderGray)

                    // Operational Next Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (order.status == OrderLifecycle.NEW || order.status == OrderLifecycle.IN_REVIEW) {
                            Button(
                                onClick = { viewModel.updateOrderStatus(order.id, OrderLifecycle.APPROVED) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                            ) {
                                Text("اعتماد الطلب")
                            }
                        } else if (order.status == OrderLifecycle.APPROVED) {
                            Button(
                                onClick = { viewModel.updateOrderStatus(order.id, OrderLifecycle.IN_PREPARATION) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = BrandOrange)
                            ) {
                                Text("بدء الطهي والتحضير")
                            }
                        } else if (order.status == OrderLifecycle.IN_PREPARATION) {
                            Button(
                                onClick = { viewModel.updateOrderStatus(order.id, OrderLifecycle.READY) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = InfoBlue)
                            ) {
                                Text("جاهز للشحن والتسليم")
                            }
                        } else if (order.status == OrderLifecycle.READY) {
                            Button(
                                onClick = { viewModel.updateOrderStatus(order.id, OrderLifecycle.DELIVERED) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                            ) {
                                Text("تسجيل التسليم للموقع")
                            }
                        } else if (order.status == OrderLifecycle.DELIVERED) {
                            Button(
                                onClick = { viewModel.updateOrderStatus(order.id, OrderLifecycle.COMPLETED) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = CharcoalText)
                            ) {
                                Text("إغلاق الطلب نهائياً")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCreateOrderDialog) {
        var siteSelection by remember { mutableStateOf("GERMAN_CEMENT") }
        var mealsSummary by remember { mutableStateOf("600 وجبة كفتة مشوية LZ-02 + 300 وجبة دجاج LZ-01") }
        var quantity by remember { mutableStateOf("900") }
        var amount by remember { mutableStateOf("115000") }
        var deliveryTime by remember { mutableStateOf("12:00 ظهراً") }

        AlertDialog(
            onDismissRequest = { showCreateOrderDialog = false },
            title = { Text("إنشاء أمر توريد جديد للمواقع") },
            text = {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("الموقع المتعاقد:")
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        listOf(
                            Pair("GERMAN_CEMENT", "الألمانية"),
                            Pair("ROYAL_HOSPITAL", "رويال"),
                            Pair("SHOROUK_HOSPITAL", "الشروق")
                        ).forEach { (id, label) ->
                            FilterChip(
                                selected = siteSelection == id,
                                onClick = { siteSelection = id },
                                label = { Text(label) }
                            )
                        }
                    }
                    OutlinedTextField(value = mealsSummary, onValueChange = { mealsSummary = it }, label = { Text("تفاصيل ومكونات الوجبات المطلوبة") }, modifier = Modifier.fillMaxWidth())
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(value = quantity, onValueChange = { quantity = it }, label = { Text("الكمية") }, modifier = Modifier.weight(1f))
                        OutlinedTextField(value = deliveryTime, onValueChange = { deliveryTime = it }, label = { Text("توقيت التوريد") }, modifier = Modifier.weight(1f))
                    }
                    OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("الإجمالي التقديري (ج.م)") }, modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val siteName = when (siteSelection) {
                            "GERMAN_CEMENT" -> Pair("الشركة الألمانية للأسمنت", "German Cement Company")
                            "ROYAL_HOSPITAL" -> Pair("مستشفى رويال", "Royal Hospital")
                            else -> Pair("مستشفى الشروق", "El Shorouk Hospital")
                        }
                        val kitchen = when (siteSelection) {
                            "GERMAN_CEMENT" -> Pair("KITCHEN_RAYAN", "مطبخ الريان")
                            "ROYAL_HOSPITAL" -> Pair("KITCHEN_HANGER", "مطبخ هنجر استيشن")
                            else -> Pair("KITCHEN_BADR", "مطبخ مدينة بدر")
                        }
                        viewModel.createNewOrder(
                            siteId = siteSelection,
                            siteNameAr = siteName.first,
                            siteNameEn = siteName.second,
                            kitchenId = kitchen.first,
                            kitchenNameAr = kitchen.second,
                            mealSummary = mealsSummary,
                            quantity = quantity.toIntOrNull() ?: 100,
                            totalAmount = amount.toDoubleOrNull() ?: 10000.0,
                            deliveryTime = deliveryTime,
                            notes = "طلب معتمد من إدارة العمليات"
                        )
                        showCreateOrderDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BrandOrange)
                ) {
                    Text("إصدار الأمر وإرساله للمطبخ")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateOrderDialog = false }) { Text("إلغاء") }
            }
        )
    }
}
