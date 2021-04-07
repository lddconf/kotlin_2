package ru.geekbrains.geekbrains_popular_libraries_kotlin.di.user

import dagger.Subcomponent
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.repository.RepositorySubcomponent
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.user.module.UserModule
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.UsersPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.adapter.UsersRVAdapter

@UserScope
@Subcomponent(
        modules = [
            UserModule::class
        ]
)
interface UserSubcomponent {

    fun repositorySubcomponent() : RepositorySubcomponent

    fun inject(usersPresenter: UsersPresenter)
    fun inject(usersRVAdapter: UsersRVAdapter)
}