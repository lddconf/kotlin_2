package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter

import ru.geekbrains.geekbrains_popular_libraries_kotlin.R
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.CountersModel
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.MainView

class MainPresenter(val mainView: MainView) {
    val model = CountersModel()

    fun counterClick(id: Int) {
        val nextValue = model.next(id)
        mainView.setButtonText(id, nextValue.toString())
    }
}