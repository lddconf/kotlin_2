package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache

interface IAvatarCache {
    fun getAvatar(url : String) : ByteArray?
    fun putAvatar(url: String, avatar: ByteArray)
}