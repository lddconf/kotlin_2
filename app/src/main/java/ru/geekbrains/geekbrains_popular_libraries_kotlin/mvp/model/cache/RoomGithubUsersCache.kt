package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache

import io.reactivex.rxjava3.core.Single
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.RoomGithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.db.Database

class RoomGithubUsersCache(val db: Database) : IUsersCache {
    override fun getAll(): Single<List<GithubUser>> = Single.fromCallable {
        db.userDao.getAll().map { roomGithubUser ->
            GithubUser(
                roomGithubUser.id,
                roomGithubUser.login,
                roomGithubUser.avatarUrl,
                roomGithubUser.reposUrl
            )
        }
    }

    override fun put(users: List<GithubUser>) {
        val roomUsers = users.map { user ->
            RoomGithubUser(user.id, user.login, user.avatarUrl, user.reposUrl)
        }
        db.userDao.insert(roomUsers)
    }
}