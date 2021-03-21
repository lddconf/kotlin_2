package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser

class GithubUsersRepo {
    private val users = listOf(
        GithubUser("login1"),
        GithubUser("login2"),
        GithubUser("login3"),
        GithubUser("login4"),
        GithubUser("login5")
    )

    fun getUsers() = Observable.create<List<GithubUser>> { emitter->
        emitter.onNext(users)
        emitter.onComplete()
    }.subscribeOn(Schedulers.io())
}