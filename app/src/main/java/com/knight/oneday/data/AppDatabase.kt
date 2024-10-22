package com.knight.oneday.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.knight.oneday.utilities.DATABASE_NAME
import com.knight.oneday.workers.SeedDatabaseWorker

/**
 * @author knight
 * create at 20-2-29 下午4:01
 * AppDatabase
 */
@Database(entities = [Step::class, Task::class], version = 2, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): TaskDao
    abstract fun stepDao(): StepDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).addMigrations(
                    Migration1To2
                )
                    /*.addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // 在创建数据库得时候 加载一些默认数据 (这里时引导用户使用得一些数据)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    })*/
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

}

