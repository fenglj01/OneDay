package com.knight.oneday.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.knight.oneday.utilities.DATABASE_NAME

/**
 * @author knight
 * create at 20-2-29 下午4:01
 * AppDatabase
 */
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
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
                )
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        // TODO 在数据库创建的时候 创建一条初始数据 用于指引用户完成OneDay的基本使用 可以通过json作为数据源 workManager来完成后台任务
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

        }
    }
}

