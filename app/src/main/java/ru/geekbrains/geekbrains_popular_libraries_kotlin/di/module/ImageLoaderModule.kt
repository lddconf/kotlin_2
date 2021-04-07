package ru.geekbrains.geekbrains_popular_libraries_kotlin.di.module

import android.widget.ImageView
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Scheduler
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.IAvatarCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.RoomGithubAvatarCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.db.Database
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.IImageLoader
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.image.GlideImageLoader
import javax.inject.Named
import javax.inject.Singleton

@Module
class ImageLoaderModule {

    @Singleton
    @Provides
    fun avatarCache(db: Database): IAvatarCache = RoomGithubAvatarCache(db)

    @Singleton
    @Provides
    fun imageLoader(cache: IAvatarCache, @Named("UIThread") uiSchelduer: Scheduler): IImageLoader<ImageView> =
            GlideImageLoader(cache, uiSchelduer)
}