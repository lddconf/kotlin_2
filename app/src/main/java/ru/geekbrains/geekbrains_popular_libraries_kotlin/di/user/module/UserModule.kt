package ru.geekbrains.geekbrains_popular_libraries_kotlin.di.user.module

import dagger.Module
import dagger.Provides
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.user.IUsersScopeContainer
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.user.UserScope
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.api.IDataSource
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.IUsersCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.RoomGithubUsersCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.db.Database
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.network.INetworkStatus
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.IGithubUsersRepo
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.RetrofitUserGithub
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.App
import javax.inject.Singleton

@Module
class UserModule {

    @UserScope
    @Provides
    fun githubUserRepo(api: IDataSource,
                       networkStatus: INetworkStatus,
                       usersCache: IUsersCache ): IGithubUsersRepo = RetrofitUserGithub(
            api,
            networkStatus,
            usersCache
    )

    @UserScope
    @Provides
    fun githubUsersCache(db: Database): IUsersCache = RoomGithubUsersCache(db)

    @UserScope
    @Provides
    fun userScopeContainer(app: App): IUsersScopeContainer = app
}