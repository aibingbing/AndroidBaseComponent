package com.aibb.android.base.example.workmanager.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "t_work")
data class WorkEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "content")
    val content: String?
) {
    override fun toString(): String {
        return "id=${this.id}, name=${this.name}, content=${this.content}"
    }
}
