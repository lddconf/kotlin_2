package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.utils

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.icu.util.UniversalTimeScale
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.converter.Image
import java.io.ByteArrayOutputStream
import java.io.IOException

/**
 * Get a file path from a Uri. This will get the the path for Storage Access
 * Framework Documents, as well as the _data field for the MediaStore and
 * other file-based ContentProviders.
 *
 * @param context The context.
 * @param uri The Uri to query.
 * @author paulburke
 */
fun getPath(context: Context, uri: Uri): String? {
    val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    // DocumentProvider
    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
        // ExternalStorageProvider
        if (isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri);
            val split = docId.split(":")
            val type = split[0]

            if ("primary".equals(type, true)) {
                return Environment.getExternalStorageDirectory().absolutePath + "/" + split[1];
            }

            // TODO handle non-primary volumes
        }
        // DownloadsProvider
        else if (isDownloadsDocument(uri)) {
            val id = DocumentsContract.getDocumentId(uri);
            val contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), id.toLong()
            )

            return getDataColumn(context, contentUri, null, null);
        }
        // MediaProvider
        else if (isMediaDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri);
            val split = docId.split(":")
            val type = split[0]

            var contentUri: Uri? = null;
            when (type) {
                "image" -> {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }
                "video" -> {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                }
                "audio" -> {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
            }

            val selection = "_id=?";
            val selectionArgs = arrayOf(split[1])


            return contentUri?.let { getDataColumn(context, it, selection, selectionArgs); }
        }
    }
    // MediaStore (and general)
    else if ("content".equals(uri.scheme, true)) {
        return getDataColumn(context, uri, null, null);
    }
    // File
    else if ("file".equals(uri.scheme, true)) {
        return uri.path;
    }

    return null;
}

/**
 * Get the value of the data column for this Uri. This is useful for
 * MediaStore Uris, and other file-based ContentProviders.
 *
 * @param context The context.
 * @param uri The Uri to query.
 * @param selection (Optional) Filter used in the query.
 * @param selectionArgs (Optional) Selection arguments used in the query.
 * @return The value of the _data column, which is typically a file path.
 */
fun getDataColumn(
    context: Context,
    uri: Uri,
    selection: String?,
    selectionArgs: Array<String>?
): String? {

    var cursor: Cursor? = null;
    val column = "_data";
    val projection = arrayOf(column)

    try {
        cursor = context.contentResolver.query(
            uri, projection, selection, selectionArgs,
            null
        );
        cursor?.apply {
            if (moveToFirst()) {
                val columnIndex = getColumnIndexOrThrow(column);
                return getString(columnIndex);
            }
        }
    } finally {
        cursor?.close()
    }
    return null;
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is ExternalStorageProvider.
 */
fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is DownloadsProvider.
 */
fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is MediaProvider.
 */
fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

fun getBitmap(context: Context, uri: Uri): Bitmap? {
    try {
        uri.let {
            return if (Build.VERSION.SDK_INT < 28) {
                val bitmap = MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    uri
                )
                bitmap
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                val bitmap = ImageDecoder.decodeBitmap(source)
                bitmap
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun getImage(context: Context, uri: Uri): Image {
    var baos : ByteArrayOutputStream? = null
    try {
        baos = ByteArrayOutputStream()
        val bitmap = getBitmap(context, uri)
        bitmap?.apply {
            compress(Bitmap.CompressFormat.PNG, 100, baos);
            return Image(baos.toByteArray())
        }
        return Image(ByteArray(0))
    } finally {
        baos?.apply {
            try {
                close();
            } catch (e : IOException) {
                e.printStackTrace()
            }
        }
    }
}