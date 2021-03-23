package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.converter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream


class Simple2PNGConverter : ConverterInterface {
    override fun convert(from: String, to: String) : Completable = Completable.fromCallable {
        val bmp = BitmapFactory.decodeFile(from) // Create Bitmap object for the original image
        // Crate new converted image file object
        val convertedImage = File(to)
        // Create FileOutputStream object to write data to the converted image file
        // Create FileOutputStream object to write data to the converted image file
        val outStream = FileOutputStream(convertedImage)
        // Keep 100 quality of the original image when converting
        val success = bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream)
        outStream.flush()
        outStream.close()
    }.subscribeOn(Schedulers.io())
}