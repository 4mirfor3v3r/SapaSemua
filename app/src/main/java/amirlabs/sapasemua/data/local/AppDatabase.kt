package amirlabs.sapasemua.data.local

import amirlabs.sapasemua.data.local.dao.VideoDao
import amirlabs.sapasemua.data.local.entity.VideoEntity
import amirlabs.sapasemua.utils.ApplicationContext
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [VideoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun videoDao(): VideoDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(
                        ApplicationContext.get(),
                        AppDatabase::class.java,
                        "amirlabs.sapasemua.db"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
    }
}