package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.api.IDataSource
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.IRepositoriesCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.IUsersCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.RoomGithubUsersCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUserRepo
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.RoomGithubRepository
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.RoomGithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.db.Database
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.network.INetworkStatus
import java.lang.RuntimeException

class RetrofitGithubUserRepos(
        val api: IDataSource,
        val networkStatus: INetworkStatus,
        val reposCache: IRepositoriesCache
) : IGithubUserRepos {
    override fun getUserRepos(user: GithubUser): Single<List<GithubUserRepo>> =
            networkStatus.isOnlineSingle().flatMap { isOnline ->
                if (isOnline) {
                    user.reposUrl?.let { url ->
                        api.getUserReposByUrl(url).flatMap { repos ->
                            reposCache.put(user, repos).toSingleDefault(repos)
                        }
                    } ?: Single.error(RuntimeException("No user repos"))
                } else {
                    reposCache.getAll(user)
                }
            }.subscribeOn(Schedulers.io())
}