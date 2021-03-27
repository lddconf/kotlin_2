package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.converter.ConverterInterface
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.converter.Image
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.ImageSelectionView

class ImageSelectionPresenter(private val router: Router, val uiSchelduer: Scheduler, val converter : ConverterInterface) :
    MvpPresenter<ImageSelectionView>() {

    private var image: Image? = null
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

    fun onNewJPEGFileSelected(image: Image?) {
        //viewState.setPath(path)
        image?.let { image ->
            if (image.data.isNotEmpty()) {
                viewState.disableConvertBtn(false)
            }
        }
        this.image = image
    }

    fun onConvertBtnClicked() {
        image?.let { image ->
            if (image.data.isNotEmpty()) {
                //Perform convert
                val outputFile = "converted.png"
                viewState.disableConvertBtn(true)
                val disposable = converter.convert(image, outputFile)
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
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}