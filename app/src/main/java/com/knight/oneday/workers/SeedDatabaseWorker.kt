package com.knight.oneday.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.coroutineScope

/**
 * Create by FLJ in 2020/3/3 9:20
 * 用于一次创建数据库时得加载一些默认数据
 */
class SeedDatabaseWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {

        Result.failure()
    }
}