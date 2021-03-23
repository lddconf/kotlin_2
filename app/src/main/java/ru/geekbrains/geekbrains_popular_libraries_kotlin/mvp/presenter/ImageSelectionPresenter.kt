package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.converter.Simple2PNGConverter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.ImageSelectionView

class ImageSelectionPresenter(private val router: Router, val uiSchelduer: Scheduler) :
    MvpPresenter<ImageSelectionView>() {

    private var inputFilePath: String = ""
    private val compositeDisposable = CompositeDisposable()

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

    fun onNewJPEGFileSelected(path: String) {
        viewState.setPath(path)
        if (path.isNotEmpty()) {
            viewState.disableConvertBtn(false)

        }
        inputFilePath = path
    }

    fun onConvertBtnClicked() {
        if (inputFilePath.isNotEmpty()) {
            //Perform convert
            val outputFilePath = inputFilePath + "_converted.png"

            viewState.disableConvertBtn(true)
            val disposable = Simple2PNGConverter().convert(inputFilePath, outputFilePath)
                .observeOn(uiSchelduer)
                .subscribe(
                    { //onComplete
                        viewState.disableConvertBtn(false)
                    },
                    { error ->
                        //Handle error
                        error.printStackTrace()
                        viewState.disableConvertBtn(false)
                    }
                )
            compositeDisposable.add(disposable)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}