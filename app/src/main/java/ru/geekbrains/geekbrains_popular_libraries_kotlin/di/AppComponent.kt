package ru.geekbrains.geekbrains_popular_libraries_kotlin.di

import com.github.terrakok.cicerone.NavigatorHolder
import dagger.Component
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.module.CiceroneModule
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.MainPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.UserLoginPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.UserReposPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.UsersPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.activity.MainActivity

@Component(
    modules = [
        CiceroneModule::class
    ]
)
interface AppComponent {
    fun inject(usersPresenter: UsersPresenter)
    fun inject(mainPresenter: MainPresenter)
    fun inject(userLoginPresenter: UserLoginPresenter)
    fun inject(mainActivity: MainActivity)
    fun inject(userReposPresenter: UserReposPresenter)
}