package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.*

@Database(
    entities = [
        UserAccount::class,
        CentralKitchen::class,
        CateringSite::class,
        PendingContract::class,
        MealItem::class,
        CateringOrder::class,
        CanteenItem::class,
        CanteenSale::class,
        InventoryItem::class,
        FinancialRecord::class,
        QuotationProposal::class,
        CouponItem::class,
        SeasonalOffer::class,
        QualityAudit::class,
        HygieneChecklist::class,
        SupportTicket::class,
        CsrInitiative::class,
        ChatMessage::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cateringDao(): CateringDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "catering_egypt_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
