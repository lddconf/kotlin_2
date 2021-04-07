package ru.geekbrains.geekbrains_popular_libraries_kotlin.di

import com.github.terrakok.cicerone.NavigatorHolder
import dagger.Component
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.module.*
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.user.UserSubcomponent
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.RoomGithubAvatarCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.MainPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.UserLoginPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.UserReposPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.UsersPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.App
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.activity.MainActivity
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.adapter.UsersRVAdapter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.image.GlideImageLoader
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AppModule::class,
            NetworkStatus::class,
            CiceroneModule::class,
            JavaRxSchelduesModule::class,
            ApiModule::class,
            DatabaseModule::class,
            GithubModule::class,
            ImageLoaderModule::class
        ]
)
interface AppComponent {
    fun userSubcomponent() : UserSubcomponent
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(usersRVAdapter: UsersRVAdapter)
    fun inject(userLoginPresenter: UserLoginPresenter)

    /*
    fun inject(usersPresenter: UsersPresenter)


    fun inject(userReposPresenter: UserReposPresenter)
     */
}