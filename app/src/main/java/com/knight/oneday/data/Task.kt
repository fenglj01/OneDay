package com.knight.oneday.data

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.*
import com.knight.oneday.utilities.TaskType
import com.knight.oneday.utilities.TABLE_NAME_TASK
import com.knight.oneday.utilities.currentTimeMills
import java.util.*

/**
 * @author knight
 * create at 20-2-29 下午2:46
 * 事件表
 */
@Keep
@Entity(tableName = TABLE_NAME_TASK)
data class Task(
    val content: String,
    @ColumnInfo(name = "create_time") val createTime: Calendar = Calendar.getInstance(),
    @ColumnInfo(name = "completion_time") var completionTime: Calendar = createTime,
    @ColumnInfo(name = "due_date_time") var dueDateTime: Calendar = createTime,
    @ColumnInfo(name = "is_done") var isDone: Boolean = false,
    @ColumnInfo(name = "type") var taskType: TaskType = TaskType.NO_CATEGORY
) : Parcelable {
    /**
     * 主键的优化写法
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var taskId: Long = 0

    override fun toString(): String {
        return "$taskId - $content -$isDone"
    }

    fun isExpired(): Boolean = this.dueDateTime.timeInMillis < currentTimeMills() && !isDone

    constructor(source: Parcel) : this(
        source.readString() ?: "",
        source.readSerializable() as Calendar,
        source.readSerializable() as Calendar,
        source.readSerializable() as Calendar,
        1 == source.readInt(),
        TaskType.values()[source.readInt()]
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(content)
        writeSerializable(createTime)
        writeSerializable(completionTime)
        writeSerializable(dueDateTime)
        writeInt((if (isDone) 1 else 0))
        writeInt(taskType.ordinal)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Task> = object : Parcelable.Creator<Task> {
            override fun createFromParcel(source: Parcel): Task = Task(source)
            override fun newArray(size: Int): Array<Task?> = arrayOfNulls(size)
        }
    }
}
