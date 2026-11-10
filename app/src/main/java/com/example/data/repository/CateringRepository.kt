package com.example.data.repository

import android.content.Context
import com.example.data.local.AppDatabase
import com.example.data.local.CateringDao
import com.example.data.local.DatabaseInitializer
import com.example.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class CateringRepository(context: Context) {
    private val database = AppDatabase.getDatabase(context)
    val dao: CateringDao = database.cateringDao()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            checkAndSeed()
        }
    }

    private suspend fun checkAndSeed() {
        val existingUsers = dao.getAllUsers().firstOrNull()
        if (existingUsers.isNullOrEmpty()) {
            DatabaseInitializer.populateInitialData(dao)
        }
    }

    // --- Users & Auth ---
    val allUsers: Flow<List<UserAccount>> = dao.getAllUsers()
    suspend fun getUser(id: String) = dao.getUserById(id)
    suspend fun addCashback(userId: String, amount: Double) = dao.addCashback(userId, amount)

    // --- Kitchens ---
    val centralKitchens: Flow<List<CentralKitchen>> = dao.getCentralKitchens()
    suspend fun updateKitchen(kitchen: CentralKitchen) = dao.updateKitchen(kitchen)

    // --- Sites ---
    val cateringSites: Flow<List<CateringSite>> = dao.getCateringSites()
    suspend fun getSite(siteId: String) = dao.getSiteById(siteId)

    // --- Pending Contracts ---
    val pendingContracts: Flow<List<PendingContract>> = dao.getPendingContracts()
    suspend fun updatePendingContract(contract: PendingContract) = dao.updatePendingContract(contract)

    // --- Meals ---
    val allMeals: Flow<List<MealItem>> = dao.getAllMeals()
    fun getMealsForSite(siteId: String): Flow<List<MealItem>> = dao.getMealsForSite(siteId)
    suspend fun addMeal(meal: MealItem) = dao.insertMeal(meal)

    // --- Orders ---
    val allOrders: Flow<List<CateringOrder>> = dao.getAllOrders()
    fun getOrdersForSite(siteId: String): Flow<List<CateringOrder>> = dao.getOrdersForSite(siteId)
    suspend fun submitOrder(order: CateringOrder) = dao.insertOrder(order)
    suspend fun updateOrderStatus(orderId: String, status: OrderLifecycle) = dao.updateOrderStatus(orderId, status)

    // --- Canteen ---
    fun getCanteenItems(siteId: String): Flow<List<CanteenItem>> = dao.getCanteenItems(siteId)
    val allCanteenSales: Flow<List<CanteenSale>> = dao.getAllCanteenSales()
    fun getCanteenSalesForSite(siteId: String): Flow<List<CanteenSale>> = dao.getCanteenSalesForSite(siteId)
    suspend fun recordCanteenSale(sale: CanteenSale) = dao.insertCanteenSale(sale)

    // --- Inventory ---
    val inventoryItems: Flow<List<InventoryItem>> = dao.getInventoryItems()

    // --- Finance ---
    val financialRecords: Flow<List<FinancialRecord>> = dao.getFinancialRecords()
    suspend fun addFinancialRecord(record: FinancialRecord) = dao.insertFinancialRecord(record)

    // --- Quotations ---
    val quotations: Flow<List<QuotationProposal>> = dao.getQuotations()
    suspend fun submitQuotation(quote: QuotationProposal) = dao.insertQuotation(quote)

    // --- Marketing & Coupons ---
    val coupons: Flow<List<CouponItem>> = dao.getCoupons()

    // --- Seasonal Offers ---
    val seasonalOffers: Flow<List<SeasonalOffer>> = dao.getSeasonalOffers()

    // --- Quality & Hygiene ---
    val qualityAudits: Flow<List<QualityAudit>> = dao.getQualityAudits()
    suspend fun addQualityAudit(audit: QualityAudit) = dao.insertQualityAudit(audit)

    val hygieneChecklists: Flow<List<HygieneChecklist>> = dao.getHygieneChecklists()
    suspend fun addHygieneChecklist(checklist: HygieneChecklist) = dao.insertHygieneChecklist(checklist)

    // --- Tickets & Complaints ---
    val allTickets: Flow<List<SupportTicket>> = dao.getAllTickets()
    fun getTicketsForSite(siteId: String): Flow<List<SupportTicket>> = dao.getTicketsForSite(siteId)
    suspend fun submitTicket(ticket: SupportTicket) = dao.insertTicket(ticket)
    suspend fun updateTicketStatus(ticketId: String, status: String, statusAr: String, statusEn: String) =
        dao.updateTicketStatus(ticketId, status, statusAr, statusEn)

    // --- CSR ---
    val csrInitiatives: Flow<List<CsrInitiative>> = dao.getCsrInitiatives()

    // --- Chat ---
    fun getMessagesForChannel(channel: String): Flow<List<ChatMessage>> = dao.getMessagesForChannel(channel)
    suspend fun sendMessage(msg: ChatMessage) = dao.insertMessage(msg)
}
