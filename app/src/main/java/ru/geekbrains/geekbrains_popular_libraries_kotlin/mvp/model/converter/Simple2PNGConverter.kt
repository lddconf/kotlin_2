package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.converter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


class Simple2PNGConverter(private val context: Context) : ConverterInterface {
    override fun convert(from: Image, oFileName: String) : Completable = Completable.fromCallable {
        val bmp = BitmapFactory.decodeByteArray(from.data, 0, from.data.size)

        // Crate new converted image file object
        val convertedImage = File(context.getExternalFilesDir(null), oFileName)
        // Create FileOutputStream object to write data to the converted image file
        val outStream = FileOutputStream(convertedImage)
        // Keep 100 quality of the original image when converting
        val success = bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream)

        if ( !success ) {
            throw Exception("Could't convert image")
        }

        outStream.flush()
        outStream.close()
    }.subscribeOn(Schedulers.io())
}