package com.amnah.recog_txt.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val uid: String?,
    val text: String?
)
