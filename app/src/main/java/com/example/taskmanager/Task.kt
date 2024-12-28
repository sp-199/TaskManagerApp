package com.example.taskmanager

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["email"],
            childColumns = ["email"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Task(
    @PrimaryKey(autoGenerate = true) val taskId: Int? = null,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "description") val description: String? = "",
    @ColumnInfo(name = "dueDate") val dueDate: String? = "",
    @ColumnInfo(name = "status") val status: Boolean? = false
)  {
    constructor() : this(taskId = null, email = "", description = "", dueDate = "", status = false)
}