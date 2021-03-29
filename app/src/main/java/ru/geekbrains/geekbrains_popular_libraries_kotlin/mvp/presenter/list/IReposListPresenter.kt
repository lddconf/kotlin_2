package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.list

import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.adapter.UserReposRVAdapter

interface IReposListPresenter<V> : IListPresenter<V> {
    fun bindView(view: UserReposRVAdapter.ViewHolder)
}