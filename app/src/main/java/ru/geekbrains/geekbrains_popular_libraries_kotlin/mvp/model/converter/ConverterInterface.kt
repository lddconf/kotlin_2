package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.converter

import io.reactivex.rxjava3.core.Completable

interface ConverterInterface {
    fun convert(from : Image, oFileName : String) : Completable
}