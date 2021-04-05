package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser

interface IUsersCache {
    fun getAll(): Single<List<GithubUser>>
    fun put(users: List<GithubUser>) : Completable
}