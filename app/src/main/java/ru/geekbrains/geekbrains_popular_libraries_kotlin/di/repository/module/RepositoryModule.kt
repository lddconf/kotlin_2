package ru.geekbrains.geekbrains_popular_libraries_kotlin.di.repository.module

import dagger.Module
import dagger.Provides
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.repository.IRepositoryScopeContainer
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.repository.RepositoryScope
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.api.IDataSource
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.IRepositoriesCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.RoomGithubReposCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.db.Database
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.network.INetworkStatus
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.IGithubUserRepos
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.RetrofitGithubUserRepos
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.App
import javax.inject.Singleton

@Module
class RepositoryModule {
    @RepositoryScope
    @Provides
    fun reposCache(db: Database): IRepositoriesCache = RoomGithubReposCache(db)

    @RepositoryScope
     @Provides
    fun githubUsersRepos(api: IDataSource,
                         networkStatus: INetworkStatus,
                         reposCache: IRepositoriesCache): IGithubUserRepos = RetrofitGithubUserRepos(
            api,
            networkStatus,
            reposCache
    )

    @RepositoryScope
    @Provides
    fun repositoryScopeContainer(app: App): IRepositoryScopeContainer = app

}