package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui

import android.app.Application
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.AppComponent
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.DaggerAppComponent
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.module.AppModule
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.repository.IRepositoryScopeContainer
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.repository.RepositorySubcomponent
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.user.IUsersScopeContainer
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.user.UserSubcomponent
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.db.Database

class App : Application(), IRepositoryScopeContainer, IUsersScopeContainer {

    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent

    var userSubcomponent: UserSubcomponent? = null
        private set

    var repositorySubcomponent: RepositorySubcomponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this


        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        //appComponent.inject()
    }

    fun initUserSubcomponent() = appComponent.userSubcomponent().also {
        userSubcomponent = it
    }

    fun initRepositorySubcomponent() = userSubcomponent?.repositorySubcomponent().also {
        repositorySubcomponent = it
    }

    override fun releaseRepositoryScope() {
        repositorySubcomponent = null
    }

    override fun releaseUserScope() {
        userSubcomponent = null
    }
}