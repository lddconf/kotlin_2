package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUserRepo(
    @Expose val id: String,
    @Expose val name: String,
    @Expose val fullName: String,
    @Expose val language: String?,
    @Expose val forksCount: Long,
    @Expose val watchersCount: Long,
    @Expose val defaultBranch: String
) : Parcelable