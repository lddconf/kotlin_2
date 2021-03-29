package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.api.IDataSource
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUserRepo

class RetrofitGithubUsersRepo(val api: IDataSource) : IGithubUsersRepo, IGithubUserRepos {
    override fun getUsers(): Single<List<GithubUser>> = api.getUsers().subscribeOn(Schedulers.io())

    override fun getUserRepos(username: String): Single<List<GithubUserRepo>> =
        api.getUserRepos(username).subscribeOn(Schedulers.io())
}