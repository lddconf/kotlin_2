package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.RoomGithubAvatar


@Dao
interface UsersAvatarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(avatar: RoomGithubAvatar)

    @Query("SELECT * FROM RoomGithubAvatar WHERE avatarUrl=:avatarUrl LIMIT 1")
    fun getAvatar(avatarUrl: String): RoomGithubAvatar?
}