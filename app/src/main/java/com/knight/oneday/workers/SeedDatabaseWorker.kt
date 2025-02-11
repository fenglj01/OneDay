package com.knight.oneday.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.knight.oneday.R
import com.knight.oneday.data.AppDatabase
import com.knight.oneday.data.Task
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

/**
 * Create by FLJ in 2020/3/3 9:20
 * 用于一次创建数据库时得加载一些默认数据
 */
class SeedDatabaseWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val guideEvent = Task(
                content = applicationContext.resources.getString(R.string.guide_welcome_content)
            )
            val database = AppDatabase.getDatabase(applicationContext)
            database.eventDao().insert(guideEvent)
            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        val TAG = SeedDatabaseWorker::class.java.simpleName
    }
}