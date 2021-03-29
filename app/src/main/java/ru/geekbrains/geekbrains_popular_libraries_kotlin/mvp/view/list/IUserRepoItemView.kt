package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.list

interface IUserRepoItemView : IItemView {
    fun setReposName(text: String)
    fun setLanguage(language: String?)
    fun setForksCount(forksCount: Long)
    fun setWatchCount(watchCount: Long)
}