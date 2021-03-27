package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip


interface ImageSelectionView : MvpView {
    @AddToEndSingle
    fun disableConvertBtn(status: Boolean)

    @AddToEndSingle
    fun setPath(path : String)

    @Skip
    fun performJPEGFileSearch()

}