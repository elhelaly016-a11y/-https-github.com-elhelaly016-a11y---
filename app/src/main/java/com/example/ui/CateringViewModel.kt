package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.*
import com.example.data.repository.CateringRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class AppScreen {
    // Admin Portal Screens
    ADMIN_DASHBOARD,
    ADMIN_KITCHENS,
    ADMIN_SITES,
    ADMIN_PENDING,
    ADMIN_MENUS,
    ADMIN_ORDERS,
    ADMIN_CANTEEN,
    ADMIN_INVENTORY,
    ADMIN_FINANCE,
    ADMIN_QUOTATIONS,
    ADMIN_COUPONS,
    ADMIN_SEASONAL,
    ADMIN_QUALITY_HYGIENE,
    ADMIN_COMPLAINTS,
    ADMIN_CSR,
    ADMIN_CHAT,
    ADMIN_REPORTS,
    ADMIN_USERS,

    // Employee & Client Portal Screens (Strictly Isolated to Employee's site)
    EMP_MENU,
    EMP_CANTEEN,
    EMP_COUPONS,
    EMP_COMPLAINTS,
    EMP_ORDERS,
    EMP_SEASONAL,
    EMP_NOTICES
}

class CateringViewModel(application: Application) : AndroidViewModel(application) {
    val repository = CateringRepository(application)

    // Language & Theme State
    private val _isArabic = MutableStateFlow(true)
    val isArabic: StateFlow<Boolean> = _isArabic.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    fun toggleLanguage() {
        _isArabic.value = !_isArabic.value
    }

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    // Active User / Session
    private val _currentUser = MutableStateFlow<UserAccount?>(null)
    val currentUser: StateFlow<UserAccount?> = _currentUser.asStateFlow()

    // Navigation Screen
    private val _currentScreen = MutableStateFlow(AppScreen.ADMIN_DASHBOARD)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    // Observed Data Flows from Repository
    val users = repository.allUsers.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val kitchens = repository.centralKitchens.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val sites = repository.cateringSites.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val pendingContracts = repository.pendingContracts.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allMeals = repository.allMeals.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allOrders = repository.allOrders.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val inventory = repository.inventoryItems.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val financialRecords = repository.financialRecords.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val quotations = repository.quotations.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val coupons = repository.coupons.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val seasonalOffers = repository.seasonalOffers.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val qualityAudits = repository.qualityAudits.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val hygieneChecklists = repository.hygieneChecklists.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allTickets = repository.allTickets.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val csrInitiatives = repository.csrInitiatives.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val canteenSales = repository.allCanteenSales.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Selected items for modal/detail views
    private val _selectedKitchen = MutableStateFlow<CentralKitchen?>(null)
    val selectedKitchen = _selectedKitchen.asStateFlow()

    private val _selectedSite = MutableStateFlow<CateringSite?>(null)
    val selectedSite = _selectedSite.asStateFlow()

    fun selectKitchen(kitchen: CentralKitchen?) {
        _selectedKitchen.value = kitchen
    }

    fun selectSite(site: CateringSite?) {
        _selectedSite.value = site
    }

    init {
        // Automatically set initial user once users flow emits
        viewModelScope.launch {
            users.collect { userList ->
                if (_currentUser.value == null && userList.isNotEmpty()) {
                    // Default to Business Owner
                    _currentUser.value = userList.firstOrNull { it.role == UserRole.OWNER } ?: userList.first()
                }
            }
        }
    }

    fun switchUser(user: UserAccount) {
        _currentUser.value = user
        if (user.role.isAdminPortal) {
            _currentScreen.value = AppScreen.ADMIN_DASHBOARD
        } else {
            _currentScreen.value = AppScreen.EMP_MENU
        }
    }

    // --- Order Lifecycle Actions ---
    fun updateOrderStatus(orderId: String, newStatus: OrderLifecycle) {
        viewModelScope.launch {
            repository.updateOrderStatus(orderId, newStatus)
        }
    }

    fun createNewOrder(
        siteId: String,
        siteNameAr: String,
        siteNameEn: String,
        kitchenId: String,
        kitchenNameAr: String,
        mealSummary: String,
        quantity: Int,
        totalAmount: Double,
        deliveryTime: String,
        notes: String
    ) {
        viewModelScope.launch {
            val orderNum = "ORD-2026-${(1050..9999).random()}"
            val newOrder = CateringOrder(
                id = "ORD_${System.currentTimeMillis()}",
                orderNumber = orderNum,
                siteId = siteId,
                siteNameAr = siteNameAr,
                siteNameEn = siteNameEn,
                kitchenId = kitchenId,
                kitchenNameAr = kitchenNameAr,
                orderDate = "2026-11-10",
                deliveryTime = deliveryTime,
                mealDetailsSummary = mealSummary,
                quantity = quantity,
                totalAmount = totalAmount,
                status = OrderLifecycle.NEW,
                notes = notes,
                createdBy = _currentUser.value?.fullNameAr ?: "الموقع"
            )
            repository.submitOrder(newOrder)
        }
    }

    // --- Complaints & Support ---
    fun submitTicket(
        complaintTypeAr: String,
        complaintTypeEn: String,
        description: String
    ) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            val ticketNum = "TCK-2026-${(100..999).random()}"
            val ticket = SupportTicket(
                id = "TCK_${System.currentTimeMillis()}",
                ticketNumber = ticketNum,
                siteId = user.siteId,
                siteNameAr = if (user.siteId == "GERMAN_CEMENT") "الشركة الألمانية للأسمنت" else if (user.siteId == "ROYAL_HOSPITAL") "مستشفى رويال" else "مستشفى الشروق",
                employeeNumber = user.employeeNumber,
                employeeName = user.fullNameAr,
                complaintTypeAr = complaintTypeAr,
                complaintTypeEn = complaintTypeEn,
                description = description,
                dateSubmitted = "2026-11-10",
                status = "NEW",
                statusAr = "جديدة وتم استلامها",
                statusEn = "New - Received",
                targetResolutionDate = "2026-11-13"
            )
            repository.submitTicket(ticket)
        }
    }

    fun updateTicketStatus(ticketId: String, status: String, statusAr: String, statusEn: String) {
        viewModelScope.launch {
            repository.updateTicketStatus(ticketId, status, statusAr, statusEn)
        }
    }

    // --- Canteen Point-Of-Sale Sale Record ---
    fun recordCanteenSale(
        itemCode: String,
        itemNameAr: String,
        quantity: Int,
        unitPrice: Double,
        paymentMethod: String
    ) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            val sale = CanteenSale(
                id = "SALE_${System.currentTimeMillis()}",
                siteId = user.siteId,
                employeeNumber = user.employeeNumber,
                employeeName = user.fullNameAr,
                itemCode = itemCode,
                itemNameAr = itemNameAr,
                quantity = quantity,
                unitPrice = unitPrice,
                totalAmount = quantity * unitPrice,
                paymentMethod = paymentMethod,
                timestamp = "2026-11-10 12:45"
            )
            repository.recordCanteenSale(sale)

            // Add 5% cashback to user balance if paid
            repository.addCashback(user.id, (quantity * unitPrice) * 0.05)
            // Refresh currentUser state
            val updatedUser = repository.getUser(user.id)
            if (updatedUser != null) {
                _currentUser.value = updatedUser
            }
        }
    }

    // --- Add New Meal Item ---
    fun addNewMeal(
        code: String,
        nameAr: String,
        nameEn: String,
        category: MealCategory,
        ingredientsAr: String,
        portionSize: String,
        calories: Int,
        retailPrice: Double,
        costPrice: Double,
        siteId: String,
        kitchenId: String
    ) {
        viewModelScope.launch {
            val meal = MealItem(
                id = "MEAL_${System.currentTimeMillis()}",
                code = code,
                nameAr = nameAr,
                nameEn = nameEn,
                category = category,
                ingredientsAr = ingredientsAr,
                ingredientsEn = ingredientsAr,
                portionSize = portionSize,
                calories = calories,
                approvedRetailPrice = retailPrice,
                internalCostPrice = costPrice,
                siteId = siteId,
                kitchenId = kitchenId
            )
            repository.addMeal(meal)
        }
    }

    // --- Update Pending Contract Pipeline ---
    fun updateContractStatus(contract: PendingContract, newStatus: String, newStatusAr: String, newStatusEn: String, nextActionAr: String) {
        viewModelScope.launch {
            val updated = contract.copy(
                proposalStatus = newStatus,
                proposalStatusAr = newStatusAr,
                proposalStatusEn = newStatusEn,
                nextActionAr = nextActionAr,
                lastUpdate = "اليوم (تحديث فوري)"
            )
            repository.updatePendingContract(updated)
        }
    }

    // --- Add Quality Audit Log ---
    fun addQualityAudit(
        kitchenOrSiteName: String,
        mealSample: String,
        tempCelsius: Double,
        sensoryScore: Int,
        packagingOk: Boolean,
        nonConformity: String,
        correctiveAction: String
    ) {
        viewModelScope.launch {
            val audit = QualityAudit(
                id = "QA_${System.currentTimeMillis()}",
                inspectionDate = "2026-11-10",
                inspectorName = _currentUser.value?.fullNameAr ?: "د. سارة عزمي",
                kitchenOrSiteName = kitchenOrSiteName,
                mealSampleChecked = mealSample,
                temperatureCelsius = tempCelsius,
                sensoryScore = sensoryScore,
                packagingCompliance = packagingOk,
                nonConformityDetails = nonConformity.ifBlank { "لا توجد" },
                correctiveActionTaken = correctiveAction.ifBlank { "موافق للمواصفات" },
                overallRatingAr = if (sensoryScore >= 4) "ممتاز ومطابق للمواصفات" else "ملاحظات قيد الاستدراك",
                overallRatingEn = if (sensoryScore >= 4) "Excellent & Compliant" else "Needs Improvement"
            )
            repository.addQualityAudit(audit)
        }
    }

    // --- Commercial Quotation Creation ---
    fun createQuotation(
        clientNameAr: String,
        serviceTypeAr: String,
        beneficiaries: Int,
        agreedRate: Double,
        costEstimate: Double,
        validityDays: Int,
        paymentTermsAr: String
    ) {
        viewModelScope.launch {
            val monthlyRev = beneficiaries * agreedRate * 24.0 // 24 working days
            val monthlyCost = beneficiaries * costEstimate * 24.0
            val profitMargin = if (monthlyRev > 0) ((monthlyRev - monthlyCost) / monthlyRev) * 100 else 0.0

            val quote = QuotationProposal(
                id = "QUOTE_${System.currentTimeMillis()}",
                refCode = "CE-OFFER-2026-${(100..999).random()}",
                clientNameAr = clientNameAr,
                clientNameEn = clientNameAr,
                serviceTypeAr = serviceTypeAr,
                serviceTypeEn = serviceTypeAr,
                beneficiariesCount = beneficiaries,
                mealsPerDay = beneficiaries,
                agreedMealRate = agreedRate,
                monthlyTotalRevenue = monthlyRev,
                internalCostEstimate = monthlyCost,
                projectedProfitMarginPct = profitMargin,
                validityDays = validityDays,
                paymentTermsAr = paymentTermsAr,
                paymentTermsEn = paymentTermsAr,
                statusAr = "عرض سعر صادر جديد",
                dateCreated = "2026-11-10"
            )
            repository.submitQuotation(quote)
        }
    }

    // --- Gemini AI Assistant (Firebase AI Logic) ---
    val aiService = com.example.data.ai.CateringAiService()

    private val _aiResponseText = MutableStateFlow("")
    val aiResponseText: StateFlow<String> = _aiResponseText.asStateFlow()

    private val _isAiLoading = MutableStateFlow(false)
    val isAiLoading: StateFlow<Boolean> = _isAiLoading.asStateFlow()

    fun askGemini(prompt: String, contextInfo: String = "") {
        viewModelScope.launch {
            _isAiLoading.value = true
            _aiResponseText.value = ""
            try {
                aiService.askAiStreaming(prompt, contextInfo).collect { chunk ->
                    _aiResponseText.value = chunk
                }
            } finally {
                _isAiLoading.value = false
            }
        }
    }

    fun clearAiResponse() {
        _aiResponseText.value = ""
        _isAiLoading.value = false
    }
}
