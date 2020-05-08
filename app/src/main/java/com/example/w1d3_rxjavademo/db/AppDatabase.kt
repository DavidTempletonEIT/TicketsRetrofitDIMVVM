package com.example.w1d3_rxjavademo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.w1d3_rxjavademo.model.local.TicketDAO
import com.example.w1d3_rxjavademo.model.response.Ticket
import com.example.w1d3_rxjavademo.network.model.Ticket

@Database(entities = [Ticket::class], version = DB_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDAO

    companion object {
        @Volatile
        private var databseInstance: AppDatabase? = null

        fun getDatabaseInstance(mContext: Context): AppDatabase =
            databseInstance ?: synchronized(this) {
                databseInstance ?: buildDatabaseInstance(mContext).also {
                    databseInstance = it
                }
            }

        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

    }
}
const val DB_VERSION = 1
const val DB_NAME = "wordDB.db"