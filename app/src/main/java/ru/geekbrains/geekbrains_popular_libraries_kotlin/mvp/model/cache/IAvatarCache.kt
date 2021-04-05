package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IAvatarCache {
    fun getAvatar(url : String) : Single<ByteArray>
    fun putAvatar(url: String, avatar: ByteArray) : Completable
}