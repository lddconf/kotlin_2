package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomGithubAvatar (
    @PrimaryKey
    val avatarUrl : String,
    val avatar : ByteArray
)