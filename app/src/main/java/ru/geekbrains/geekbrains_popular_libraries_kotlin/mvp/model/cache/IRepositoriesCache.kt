package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUserRepo

interface IRepositoriesCache {
    fun getAll(user : GithubUser): Single<List<GithubUserRepo>>
    fun put(user: GithubUser, repos: List<GithubUserRepo>) : Completable
}