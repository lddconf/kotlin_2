package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.ImageSelectionView

class ImageSelectionPresenter(private val router: Router) : MvpPresenter<ImageSelectionView>() {

    private var filePath : String? = null

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.disableConvertBtn(true)
        viewState.setPath("")
    }

    fun onFileSelectionBtnClicked() {
        viewState.performJPEGFileSearch()
    }

    fun onNewJPEGFileSelected(path : String) {
        viewState.setPath(path)
        if ( path.isNotEmpty() ) {
            viewState.disableConvertBtn(false)

        }
        filePath = path
    }

    fun onConvertBtnClicked() {
        if ( !filePath.isNullOrEmpty() ) {
            //Perform convert

        }
    }
}