package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.dao

import androidx.room.*
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.RoomGithubRepository

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repo: RoomGithubRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg repo: RoomGithubRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repo: List<RoomGithubRepository>)

    @Update
    fun update(repo: RoomGithubRepository)

    @Update
    fun update(vararg repo: RoomGithubRepository)

    @Update
    fun update(repo: List<RoomGithubRepository>)

    @Delete
    fun delete(repo: RoomGithubRepository)

    @Delete
    fun delete(vararg repo: RoomGithubRepository)

    @Delete
    fun delete(user: List<RoomGithubRepository>)

    @Query("SELECT * from RoomGithubRepository")
    fun getAll(): List<RoomGithubRepository>

    @Query("SELECT * FROM RoomGithubRepository WHERE userId = :userId")
    fun findForUser(userId: String): List<RoomGithubRepository>
}