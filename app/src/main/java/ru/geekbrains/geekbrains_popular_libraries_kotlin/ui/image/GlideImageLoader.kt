package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.IAvatarCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.IImageLoader
import java.nio.ByteBuffer

class GlideImageLoader(val cache: IAvatarCache) : IImageLoader<ImageView> {
    override fun load(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    GlobalScope.launch(Dispatchers.IO) {
                        val cachedAvatar = cache.getAvatar(url)
                        if (cachedAvatar.isNotEmpty()) {
                            //Have data
                            val bitmap = BitmapFactory.decodeByteArray(cachedAvatar, 0, cachedAvatar.size)
                        }
                    }
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.let { drawable ->
                        drawable as BitmapDrawable
                        val bitmap = drawable.bitmap
                        val buffer = ByteBuffer.allocate(bitmap.rowBytes * bitmap.height)
                        bitmap.copyPixelsToBuffer(buffer)
                        GlobalScope.launch(Dispatchers.IO) {
                            cache.putAvatar(url, buffer.array())
                        }
                    }
                    return false
                }
            })
            .into(container)

    }
}




