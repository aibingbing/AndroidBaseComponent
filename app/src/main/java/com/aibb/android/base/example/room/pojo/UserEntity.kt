package com.aibb.android.base.example.room.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "t_user", indices = [Index(value = ["name", "age"], unique = true)])
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "u_id")
    val uid: Int,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "age")
    val age: Int?,

    @ColumnInfo(name = "city")
    val city: String?
) {
    override fun toString(): String {
        return "uid=${this.uid}, name=${this.name}, age=${this.age}, city=${this.city}"
    }
}