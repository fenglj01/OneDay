package com.knight.oneday.data

/**
 * Create by FLJ in 2020/3/2 15:03
 * 步骤仓库
 */
class StepRepository private constructor(private val stepDao: StepDao) {

    fun getStepsByEventId(eventId: Long) = stepDao.getStepByEventId(eventId)

    suspend fun removeStepById(stepId: Long) {
        stepDao.deleteStepById(stepId)
    }

    suspend fun removeStepsByEventId(eventId: Long) {
        stepDao.deleteStepByEventId(eventId)
    }

    suspend fun removeAllStep() {
        stepDao.deleteAll()
    }

    suspend fun createStep(step: Step) {
        stepDao.insertStep(step)
    }

    companion object {
        private var INSTANCE: StepRepository? = null

        fun getInstance(stepDao: StepDao): StepRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: StepRepository(stepDao).also { INSTANCE = it }
            }
        }
    }
}