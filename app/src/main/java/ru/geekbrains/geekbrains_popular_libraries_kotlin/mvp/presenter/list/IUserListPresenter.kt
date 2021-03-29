package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.list

import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.adapter.UsersRVAdapter

interface IUserListPresenter<V> : IListPresenter<V> {
    fun bindView(view: UsersRVAdapter.ViewHolder)
}