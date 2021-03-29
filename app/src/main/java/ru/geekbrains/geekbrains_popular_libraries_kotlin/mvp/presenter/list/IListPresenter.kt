package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.list

import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.adapter.UserReposRVAdapter

interface IListPresenter<V> {
    var itemClickListener: ((V) -> Unit)?
    fun getCount(): Int
}