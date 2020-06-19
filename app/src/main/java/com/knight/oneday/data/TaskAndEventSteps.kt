package com.knight.oneday.data

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Create by FLJ in 2020/3/2 15:18
 * 事件及事件对应的步骤
 * 这不是一个表只是为了更好的架构
 */
data class TaskAndEventSteps(
    /* @Embedded 嵌入*/
    @Embedded
    val task: Task,
    /* @Relation 关系*/
    @Relation(parentColumn = "id", entityColumn = "event_id")
    val eventSteps: List<Step> = emptyList()
) {
    override fun toString(): String {
        return "event:${task.taskId}-${task.content} \n steps :$eventSteps"
    }

    fun haveSteps(): Boolean = eventSteps.isNotEmpty()
}