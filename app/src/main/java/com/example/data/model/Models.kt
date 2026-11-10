package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// --- 1. User Account & Authentication ---
@Entity(tableName = "users")
data class UserAccount(
    @PrimaryKey val id: String,
    val username: String,
    val fullNameAr: String,
    val fullNameEn: String,
    val role: UserRole,
    val siteId: String, // "ALL" for executive, or site code like "ROYAL", "GERMAN_CEMENT", "SHOROUK"
    val kitchenId: String = "ALL", // for Kitchen Managers
    val departmentAr: String = "الإدارة المركزية",
    val departmentEn: String = "Central Management",
    val employeeNumber: String,
    val phone: String,
    val cashbackBalance: Double = 0.0,
    val isActive: Boolean = true
)

// --- 2. Central Kitchens ---
@Entity(tableName = "central_kitchens")
data class CentralKitchen(
    @PrimaryKey val id: String, // "KITCHEN_HANGER", "KITCHEN_BADR", "KITCHEN_RAYAN"
    val nameAr: String,
    val nameEn: String,
    val locationAr: String,
    val locationEn: String,
    val managerName: String,
    val staffCount: Int,
    val dailyCapacity: Int, // e.g., 5000 meals
    val todayProductionCount: Int,
    val rawMaterialHealthScore: Int, // 0 - 100
    val hygieneScore: Int, // 0 - 100
    val qualityScore: Int, // 0 - 100
    val activeOrdersCount: Int,
    val statusAr: String = "يعمل بكامل الطاقة",
    val statusEn: String = "Fully Operational"
)

// --- 3. Active Clients & Sites ---
@Entity(tableName = "catering_sites")
data class CateringSite(
    @PrimaryKey val id: String, // "ROYAL_HOSPITAL", "GERMAN_CEMENT", "SHOROUK_HOSPITAL"
    val nameAr: String,
    val nameEn: String,
    val categoryAr: String, // مستشفيات ورعاية صحية / صناعات ثقيلة وأسمنت
    val categoryEn: String,
    val contactPerson: String,
    val phone: String,
    val dailyMealsContracted: Int,
    val hasCanteen: Boolean,
    val assignedKitchenId: String,
    val assignedKitchenNameAr: String,
    val assignedKitchenNameEn: String,
    val contractValidUntil: String,
    val statusAr: String = "نشط وساري",
    val statusEn: String = "Active & In Service"
)

// --- 4. Pending Contracts / Pipeline ---
@Entity(tableName = "pending_contracts")
data class PendingContract(
    @PrimaryKey val id: String,
    val nameAr: String,
    val nameEn: String,
    val activityAr: String,
    val activityEn: String,
    val contactPerson: String,
    val phone: String,
    val contactDate: String,
    val proposalStatus: String, // "NEW_LEAD", "CONTACTED", "PREPARING_QUOTE", "QUOTE_SENT", "NEGOTIATING", "AWAITING_REPLY", "APPROVED", "REJECTED"
    val proposalStatusAr: String,
    val proposalStatusEn: String,
    val estimatedMealsPerDay: Int,
    val nextActionAr: String,
    val nextActionEn: String,
    val lastUpdate: String,
    val notes: String
)

// --- 5. Meals & Menus ---
enum class MealCategory(val titleAr: String, val titleEn: String) {
    BREAKFAST("إفطار", "Breakfast"),
    LUNCH("غداء", "Lunch"),
    DINNER("عشاء", "Dinner"),
    HOT_MEAL("وجبات ساخنة", "Hot Meals"),
    DRY_MEAL("وجبات جافة", "Dry Meals"),
    PATIENT_DIET("وجبات مرضى وحميات", "Patient Diets"),
    REGULAR("وجبات اعتيادية", "Regular Meals"),
    SPECIAL("وجبات خاصة وإشرافية", "Executive & Special"),
    CANTEEN("وجبات كنتِين", "Canteen Meals"),
    SEASONAL("وجبات موسمية", "Seasonal Specials")
}

@Entity(tableName = "meal_items")
data class MealItem(
    @PrimaryKey val id: String,
    val code: String, // e.g. LZ-01, LZ-02, SL-04, RH-01
    val nameAr: String,
    val nameEn: String,
    val category: MealCategory,
    val ingredientsAr: String,
    val ingredientsEn: String,
    val portionSize: String, // e.g. "450 جم"
    val calories: Int,
    val approvedRetailPrice: Double, // Price billed to site / employee
    val internalCostPrice: Double, // Cost to produce (ADMIN ONLY)
    val siteId: String, // "ALL" or specific site
    val kitchenId: String, // produced by
    val isAvailable: Boolean = true,
    val tags: String = "طازج يومياً"
)

// --- 6. Orders & Supply Cycle ---
enum class OrderLifecycle(val titleAr: String, val titleEn: String) {
    DRAFT("مسودة", "Draft"),
    NEW("جديد", "New"),
    IN_REVIEW("قيد المراجعة", "In Review"),
    APPROVED("معتمد", "Approved"),
    IN_PREPARATION("قيد التجهيز", "In Preparation"),
    READY("جاهز للشحن", "Ready"),
    DELIVERED("تم التسليم", "Delivered"),
    COMPLETED("مكتمل ومغلق", "Completed"),
    REJECTED("مرفوض", "Rejected"),
    CANCELLED("ملغي", "Cancelled")
}

@Entity(tableName = "catering_orders")
data class CateringOrder(
    @PrimaryKey val id: String,
    val orderNumber: String,
    val siteId: String,
    val siteNameAr: String,
    val siteNameEn: String,
    val kitchenId: String,
    val kitchenNameAr: String,
    val orderDate: String,
    val deliveryTime: String,
    val mealDetailsSummary: String,
    val quantity: Int,
    val totalAmount: Double,
    val status: OrderLifecycle,
    val notes: String = "",
    val createdBy: String,
    val deliveryConfirmationCode: String = "NAT-LOG-${(1000..9999).random()}"
)

// --- 7. Canteen Products & Point of Sale ---
@Entity(tableName = "canteen_items")
data class CanteenItem(
    @PrimaryKey val id: String,
    val code: String,
    val nameAr: String,
    val nameEn: String,
    val categoryAr: String,
    val categoryEn: String,
    val approvedSalePrice: Double,
    val internalCost: Double, // ADMIN ONLY
    val siteId: String, // "GERMAN_CEMENT", etc.
    val stockQuantity: Int,
    val unitAr: String,
    val unitEn: String
)

@Entity(tableName = "canteen_sales")
data class CanteenSale(
    @PrimaryKey val id: String,
    val siteId: String,
    val employeeNumber: String,
    val employeeName: String,
    val itemCode: String,
    val itemNameAr: String,
    val quantity: Int,
    val unitPrice: Double,
    val totalAmount: Double,
    val paymentMethod: String, // "نقدًا" or "خصم من الراتب"
    val timestamp: String
)

// --- 8. Central & Site Inventory ---
@Entity(tableName = "inventory_items")
data class InventoryItem(
    @PrimaryKey val id: String,
    val code: String,
    val nameAr: String,
    val nameEn: String,
    val categoryAr: String, // لحوم ودواجن، خضروات، بقالة جافة، عبوات
    val categoryEn: String,
    val unit: String,
    val kitchenId: String, // "KITCHEN_HANGER", "KITCHEN_BADR", "KITCHEN_RAYAN", "CENTRAL"
    val kitchenNameAr: String,
    val currentStock: Double,
    val reorderThreshold: Double,
    val unitCost: Double, // ADMIN ONLY
    val supplierName: String,
    val lastRestockDate: String
)

// --- 9. Financial Transactions & Ledgers ---
@Entity(tableName = "financial_records")
data class FinancialRecord(
    @PrimaryKey val id: String,
    val docNumber: String,
    val type: String, // "REVENUE", "OPERATIONAL_EXPENSE", "RECEIPT_VOUCHER", "DISBURSEMENT_VOUCHER"
    val typeAr: String,
    val typeEn: String,
    val targetEntity: String, // Royal Hospital, German Cement, El Rayan Kitchen, etc.
    val amount: Double,
    val date: String,
    val descriptionAr: String,
    val descriptionEn: String,
    val isVerified: Boolean = true,
    val accountantName: String
)

// --- 10. Commercial Quotations ---
@Entity(tableName = "quotation_proposals")
data class QuotationProposal(
    @PrimaryKey val id: String,
    val refCode: String,
    val clientNameAr: String,
    val clientNameEn: String,
    val serviceTypeAr: String,
    val serviceTypeEn: String,
    val beneficiariesCount: Int,
    val mealsPerDay: Int,
    val agreedMealRate: Double,
    val monthlyTotalRevenue: Double,
    val internalCostEstimate: Double, // ADMIN ONLY
    val projectedProfitMarginPct: Double, // ADMIN ONLY
    val validityDays: Int,
    val paymentTermsAr: String,
    val paymentTermsEn: String,
    val statusAr: String,
    val dateCreated: String
)

// --- 11. Marketing Coupons & Cashback ---
@Entity(tableName = "coupons")
data class CouponItem(
    @PrimaryKey val id: String,
    val code: String, // e.g. "EGYPT-RAMADAN-25"
    val titleAr: String,
    val titleEn: String,
    val descriptionAr: String,
    val descriptionEn: String,
    val discountPercent: Int, // e.g. 10 or 15%
    val discountFixedAmount: Double = 0.0,
    val applicableSites: String, // "ALL" or site IDs
    val validFrom: String,
    val validUntil: String,
    val termsAr: String,
    val termsEn: String,
    val isActive: Boolean = true,
    val isJaggedTicket: Boolean = true
)

// --- 12. Seasonal Offers ---
@Entity(tableName = "seasonal_offers")
data class SeasonalOffer(
    @PrimaryKey val id: String,
    val titleAr: String,
    val titleEn: String,
    val subtitleAr: String,
    val subtitleEn: String,
    val descriptionAr: String,
    val descriptionEn: String,
    val discountTextAr: String = "خصم حتى 10% لعملاء الشركة",
    val discountTextEn: String = "Up to 10% discount for National Logistics clients",
    val pledgeAr: String = "جودة تستحق الثقة — عروض خاصة لعملاء الوطنية اللوجستية",
    val pledgeEn: String = "Trustworthy Quality — Exclusive Offers for National Logistics Clients",
    val priceEgp: Double,
    val availabilityStatusAr: String = "متاح للحجز والتوريد",
    val availabilityStatusEn: String = "Available for Booking"
)

// --- 13. Quality & Hygiene Logs ---
@Entity(tableName = "quality_audits")
data class QualityAudit(
    @PrimaryKey val id: String,
    val inspectionDate: String,
    val inspectorName: String,
    val kitchenOrSiteName: String,
    val mealSampleChecked: String,
    val temperatureCelsius: Double,
    val sensoryScore: Int, // 1 to 5
    val packagingCompliance: Boolean,
    val nonConformityDetails: String = "",
    val correctiveActionTaken: String = "",
    val overallRatingAr: String = "ممتاز ومطابق للمواصفات",
    val overallRatingEn: String = "Excellent & Compliant"
)

@Entity(tableName = "hygiene_checklists")
data class HygieneChecklist(
    @PrimaryKey val id: String,
    val date: String,
    val officerName: String,
    val targetFacility: String,
    val kitchenCleanlinessScore: Int, // out of 100
    val equipmentSanitizationScore: Int,
    val staffPersonalHygieneScore: Int,
    val coldStorageTempPassed: Boolean,
    val foodExpiryValidated: Boolean,
    val disinfectionLogUpdated: Boolean,
    val hazardAlerts: String = "لا توجد مخاطر مسجلة",
    val nextAuditDate: String
)

// --- 14. Support Tickets & Complaints ---
@Entity(tableName = "support_tickets")
data class SupportTicket(
    @PrimaryKey val id: String,
    val ticketNumber: String,
    val siteId: String,
    val siteNameAr: String,
    val employeeNumber: String,
    val employeeName: String,
    val complaintTypeAr: String, // جودة وجبة، تأخير توريد، ملاحظة كنتين، هاجين
    val complaintTypeEn: String,
    val description: String,
    val dateSubmitted: String,
    val status: String, // "NEW", "RECEIVED", "UNDER_REVIEW", "IN_PROGRESS", "RESOLVED", "CLOSED"
    val statusAr: String,
    val statusEn: String,
    val assignedOfficer: String = "قسم علاقات المواقع والجودة",
    val targetResolutionDate: String,
    val resolutionNotes: String = ""
)

// --- 15. Annual Distribution & CSR Initiatives ---
@Entity(tableName = "csr_initiatives")
data class CsrInitiative(
    @PrimaryKey val id: String,
    val entityNameAr: String,
    val entityNameEn: String,
    val initiativeTitleAr: String,
    val initiativeTitleEn: String,
    val campaignTypeAr: String, // توريدات سنوية / كراتين إطعام / وجبات ساخنة
    val campaignTypeEn: String,
    val targetVolumeMeals: Int,
    val distributionScheduleAr: String,
    val distributionScheduleEn: String,
    val verifiedPartnerStatusAr: String = "جهة معتمدة - ملف توريد رسمي",
    val verifiedPartnerStatusEn: String = "Verified Supply Partner",
    val notesAr: String
)

// --- 16. Messages & Announcements ---
@Entity(tableName = "chat_messages")
data class ChatMessage(
    @PrimaryKey val id: String,
    val senderName: String,
    val senderRole: String,
    val channelOrSite: String, // "ALL_ADMIN", "ROYAL_HOSPITAL", "GERMAN_CEMENT", "KITCHENS"
    val messageText: String,
    val timestamp: String,
    val isUrgent: Boolean = false
)
