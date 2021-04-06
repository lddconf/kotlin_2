package ru.geekbrains.geekbrains_popular_libraries_kotlin.di.module

import dagger.Module
import dagger.Provides
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.api.IDataSource
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.IRepositoriesCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.IUsersCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.network.INetworkStatus
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.IGithubUserRepos
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.IGithubUsersRepo
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.RetrofitGithub
import javax.inject.Singleton

@Module
class GithubModule {

    @Singleton
    @Provides
    fun githubUsersRepos(api: IDataSource,
                         networkStatus: INetworkStatus,
                         usersCache: IUsersCache,
                         reposCache: IRepositoriesCache): IGithubUsersRepo = RetrofitGithub(
            api,
            networkStatus,
            usersCache,
            reposCache
    )

    @Singleton
    @Provides
    fun githubUserRepo(api: IDataSource,
                       networkStatus: INetworkStatus,
                       usersCache: IUsersCache,
                       reposCache: IRepositoriesCache): IGithubUserRepos = RetrofitGithub(
            api,
            networkStatus,
            usersCache,
            reposCache
    )
}