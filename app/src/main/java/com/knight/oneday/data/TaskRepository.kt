package com.knight.oneday.data

class TaskRepository private constructor(
    private val taskDao: TaskDao
) {

    companion object {
        private var INSTANCE: TaskRepository? = null

        fun getInstance(taskDao: TaskDao): TaskRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: TaskRepository(taskDao).also { INSTANCE = it }
            }
        }
    }

}