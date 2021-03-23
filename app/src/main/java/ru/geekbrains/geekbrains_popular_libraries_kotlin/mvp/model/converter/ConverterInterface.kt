package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.converter

import android.media.Image
import io.reactivex.rxjava3.core.Completable

interface ConverterInterface {
    fun convert(from : String, to : String) : Completable
}