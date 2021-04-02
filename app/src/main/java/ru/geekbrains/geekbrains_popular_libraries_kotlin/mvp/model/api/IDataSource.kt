package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUserRepo

interface IDataSource {
    @GET("users")
    fun getUsers() : Single<List<GithubUser>>

    @GET("users/{username}/repos")
    fun getUserReposByName(@Path("username") username: String) : Single<List<GithubUserRepo>>

    @GET
    fun getUserReposByUrl(@Url url: String) : Single<List<GithubUserRepo>>
}