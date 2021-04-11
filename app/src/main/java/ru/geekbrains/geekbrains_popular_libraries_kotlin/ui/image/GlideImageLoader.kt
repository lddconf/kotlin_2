package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import kotlinx.coroutines.*
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.IAvatarCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.IImageLoader
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

class GlideImageLoader(val cache: IAvatarCache, val uiSchelduer: Scheduler) : IImageLoader<ImageView> {
    override fun load(url: String, container: ImageView) {
        Glide.with(container.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                    ): Boolean {
                        cache.getAvatar(url).observeOn(uiSchelduer).subscribe({ avatar ->
                            avatar.apply {
                                if (isNotEmpty()) {
                                    val bitmap = BitmapFactory.decodeByteArray(this, 0, this.size)
                                    target?.let {
                                        container.setImageBitmap(bitmap)
                                    }
                                }
                            }
                        }, { error ->

                        })
                        return true
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
                            val baos = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                            cache.putAvatar(url, baos.toByteArray()).subscribe()

                        }
                        return false
                    }
                })
                .into(container)
    }
}




