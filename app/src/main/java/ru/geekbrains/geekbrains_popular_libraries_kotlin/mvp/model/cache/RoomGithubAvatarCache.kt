package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache

import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.RoomGithubAvatar
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.db.Database

class RoomGithubAvatarCache(val db: Database) : IAvatarCache {
    override fun getAvatar(url: String): ByteArray? {
        return db.avatarDao.getAvatar(url)?.avatar
    }

    override fun putAvatar(url: String, avatar: ByteArray) {
        val roomAvatar = RoomGithubAvatar(url, avatar)
        db.avatarDao.insert(roomAvatar)
    }
}