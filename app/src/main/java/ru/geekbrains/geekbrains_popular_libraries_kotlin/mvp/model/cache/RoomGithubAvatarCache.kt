package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.RoomGithubAvatar
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.db.Database
import java.lang.RuntimeException

class RoomGithubAvatarCache(val db: Database) : IAvatarCache {
    override fun getAvatar(url: String): Single<ByteArray> = Single.fromCallable {
        db.avatarDao.getAvatar(url)?.avatar ?: throw RuntimeException("No avatar for user")
    }.subscribeOn(Schedulers.io())

    override fun putAvatar(url: String, avatar: ByteArray): Completable = Completable.fromCallable {
        val roomAvatar = RoomGithubAvatar(url, avatar)
        db.avatarDao.insert(roomAvatar)
    }.subscribeOn(Schedulers.io())
}