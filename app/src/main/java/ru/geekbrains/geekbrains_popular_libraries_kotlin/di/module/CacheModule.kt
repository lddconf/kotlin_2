package ru.geekbrains.geekbrains_popular_libraries_kotlin.di.module

import dagger.Module
import dagger.Provides
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.*
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.db.Database
import javax.inject.Singleton

@Module
class CacheModule {
    @Singleton
    @Provides
    fun githubUsersCache(db: Database): IUsersCache = RoomGithubUsersCache(db)

    @Singleton
    @Provides
    fun reposCache(db: Database): IRepositoriesCache = RoomGithubReposCache(db)

    @Singleton
    @Provides
    fun avatarCache(db: Database): IAvatarCache = RoomGithubAvatarCache(db)
}