package ru.geekbrains.geekbrains_popular_libraries_kotlin.di.repository

import dagger.Subcomponent
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.repository.module.RepositoryModule
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.UserReposPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.UsersPresenter

@RepositoryScope
@Subcomponent(
        modules = [
            RepositoryModule::class
        ]
)
interface RepositorySubcomponent {
    fun inject(usersPresenter: UsersPresenter)
    fun inject(reposPresenter: UserReposPresenter)
}