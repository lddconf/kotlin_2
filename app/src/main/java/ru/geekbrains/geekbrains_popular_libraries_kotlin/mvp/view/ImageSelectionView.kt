package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ImageSelectionView : MvpView {
    fun disableConvertBtn(status: Boolean)
    fun setPath(path : String)

    fun performJPEGFileSearch()

}