package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUserRepo

interface IGithubUserRepos {
    fun getUserReposByName(username : String): Single<List<GithubUserRepo>>
    fun getUserReposByURL(url : String): Single<List<GithubUserRepo>>
}