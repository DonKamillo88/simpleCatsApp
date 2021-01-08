package org.k2apps.simplecatsapp.data.repository.db


import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.k2apps.simplecatsapp.data.model.Breed
import org.k2apps.simplecatsapp.data.model.Cat

@Database(entities = [Cat::class, Breed::class], version = 2, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    abstract fun catsDao(): CatsDao
    abstract fun breedsDao(): BreedsDao

    companion object {
        private const val TAG = "AppDatabase"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = buildDatabase(context)
                INSTANCE = instance
                instance
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "room_database"
            )
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build()


        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.e(TAG, "empty MIGRATION_1_2")
            }
        }


    }

}