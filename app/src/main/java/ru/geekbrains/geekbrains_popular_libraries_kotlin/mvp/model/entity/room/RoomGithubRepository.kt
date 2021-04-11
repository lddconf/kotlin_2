package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RoomGithubUser::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class RoomGithubRepository(
    @PrimaryKey val id: String,
    val name : String,
    val fullName : String,
    val language : String?,
    val forksCount : Long,
    val watchersCount : Long,
    val defaultBranch : String,
    var userId: String
)