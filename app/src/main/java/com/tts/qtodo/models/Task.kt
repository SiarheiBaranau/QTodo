package com.tts.qtodo.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String,
    var description: String,
    var priority: Priority = Priority.MIDDLE,
    var checked: Boolean = false
) : Serializable