package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CateringDao {

    // --- Users ---
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserAccount>>

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: String): UserAccount?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserAccount>)

    @Query("UPDATE users SET cashbackBalance = cashbackBalance + :amount WHERE id = :userId")
    suspend fun addCashback(userId: String, amount: Double)

    // --- Central Kitchens ---
    @Query("SELECT * FROM central_kitchens")
    fun getCentralKitchens(): Flow<List<CentralKitchen>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCentralKitchens(kitchens: List<CentralKitchen>)

    @Update
    suspend fun updateKitchen(kitchen: CentralKitchen)

    // --- Sites & Clients ---
    @Query("SELECT * FROM catering_sites")
    fun getCateringSites(): Flow<List<CateringSite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCateringSites(sites: List<CateringSite>)

    @Query("SELECT * FROM catering_sites WHERE id = :siteId LIMIT 1")
    suspend fun getSiteById(siteId: String): CateringSite?

    // --- Pending Contracts ---
    @Query("SELECT * FROM pending_contracts")
    fun getPendingContracts(): Flow<List<PendingContract>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPendingContracts(contracts: List<PendingContract>)

    @Update
    suspend fun updatePendingContract(contract: PendingContract)

    // --- Meals ---
    @Query("SELECT * FROM meal_items")
    fun getAllMeals(): Flow<List<MealItem>>

    @Query("SELECT * FROM meal_items WHERE siteId = 'ALL' OR siteId = :siteId")
    fun getMealsForSite(siteId: String): Flow<List<MealItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(meals: List<MealItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealItem)

    // --- Orders ---
    @Query("SELECT * FROM catering_orders ORDER BY orderDate DESC")
    fun getAllOrders(): Flow<List<CateringOrder>>

    @Query("SELECT * FROM catering_orders WHERE siteId = :siteId ORDER BY orderDate DESC")
    fun getOrdersForSite(siteId: String): Flow<List<CateringOrder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: CateringOrder)

    @Query("UPDATE catering_orders SET status = :status WHERE id = :orderId")
    suspend fun updateOrderStatus(orderId: String, status: OrderLifecycle)

    // --- Canteen ---
    @Query("SELECT * FROM canteen_items WHERE siteId = :siteId OR siteId = 'ALL'")
    fun getCanteenItems(siteId: String): Flow<List<CanteenItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCanteenItems(items: List<CanteenItem>)

    @Query("SELECT * FROM canteen_sales WHERE siteId = :siteId ORDER BY timestamp DESC")
    fun getCanteenSalesForSite(siteId: String): Flow<List<CanteenSale>>

    @Query("SELECT * FROM canteen_sales ORDER BY timestamp DESC")
    fun getAllCanteenSales(): Flow<List<CanteenSale>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCanteenSale(sale: CanteenSale)

    // --- Inventory ---
    @Query("SELECT * FROM inventory_items")
    fun getInventoryItems(): Flow<List<InventoryItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInventory(items: List<InventoryItem>)

    // --- Financial Records ---
    @Query("SELECT * FROM financial_records ORDER BY date DESC")
    fun getFinancialRecords(): Flow<List<FinancialRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFinancialRecord(record: FinancialRecord)

    // --- Quotations ---
    @Query("SELECT * FROM quotation_proposals ORDER BY dateCreated DESC")
    fun getQuotations(): Flow<List<QuotationProposal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotation(quotation: QuotationProposal)

    // --- Marketing Coupons & Cashback ---
    @Query("SELECT * FROM coupons WHERE isActive = 1")
    fun getCoupons(): Flow<List<CouponItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoupons(coupons: List<CouponItem>)

    // --- Seasonal Offers ---
    @Query("SELECT * FROM seasonal_offers")
    fun getSeasonalOffers(): Flow<List<SeasonalOffer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasonalOffers(offers: List<SeasonalOffer>)

    // --- Quality & Hygiene ---
    @Query("SELECT * FROM quality_audits ORDER BY inspectionDate DESC")
    fun getQualityAudits(): Flow<List<QualityAudit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQualityAudit(audit: QualityAudit)

    @Query("SELECT * FROM hygiene_checklists ORDER BY date DESC")
    fun getHygieneChecklists(): Flow<List<HygieneChecklist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHygieneChecklist(checklist: HygieneChecklist)

    // --- Support & Complaints ---
    @Query("SELECT * FROM support_tickets ORDER BY dateSubmitted DESC")
    fun getAllTickets(): Flow<List<SupportTicket>>

    @Query("SELECT * FROM support_tickets WHERE siteId = :siteId ORDER BY dateSubmitted DESC")
    fun getTicketsForSite(siteId: String): Flow<List<SupportTicket>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: SupportTicket)

    @Query("UPDATE support_tickets SET status = :status, statusAr = :statusAr, statusEn = :statusEn WHERE id = :ticketId")
    suspend fun updateTicketStatus(ticketId: String, status: String, statusAr: String, statusEn: String)

    // --- CSR Initiatives ---
    @Query("SELECT * FROM csr_initiatives")
    fun getCsrInitiatives(): Flow<List<CsrInitiative>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCsrInitiatives(initiatives: List<CsrInitiative>)

    // --- Chat Messages ---
    @Query("SELECT * FROM chat_messages WHERE channelOrSite = :channel OR channelOrSite = 'ALL' ORDER BY timestamp ASC")
    fun getMessagesForChannel(channel: String): Flow<List<ChatMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(msg: ChatMessage)
}
