package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.databinding.FragmentImageSelectionBinding
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.converter.Simple2PNGConverter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.ImageSelectionPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.ImageSelectionView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.App
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.BackClickListener
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.utils.getBitmap
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.utils.getImage
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.utils.getPath


class ImageSelectionFragment : MvpAppCompatFragment(), ImageSelectionView, BackClickListener {
    private var imageSelectionBinding: FragmentImageSelectionBinding? = null

    private val presenter by moxyPresenter {
        ImageSelectionPresenter(App.instance.router, AndroidSchedulers.mainThread(), Simple2PNGConverter(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentImageSelectionBinding.inflate(inflater, container, false).also {
        imageSelectionBinding = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        imageSelectionBinding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageSelectionBinding?.apply {
            selectBtn.setOnClickListener {
                presenter.onFileSelectionBtnClicked()
            }

            cnvBtn.setOnClickListener {
                presenter.onConvertBtnClicked()
            }
        }
    }

    override fun backPressed(): Boolean = presenter.backClick()

    companion object {
        fun newInstance() = ImageSelectionFragment()
        private const val PICK_IMAGE_REQUEST_CODE = 123.toInt()
    }

    override fun disableConvertBtn(disable: Boolean) {
        imageSelectionBinding?.cnvBtn?.isEnabled = !disable
    }

    override fun setPath(path: String) {
        imageSelectionBinding?.pathView?.text = path
    }

    override fun performJPEGFileSearch() {
        val intent = Intent()
            .setType("image/jpeg")
            .setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(
            Intent.createChooser(intent, "Select a jpeg"),
            PICK_IMAGE_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {

            data?.data?.let { selectedImage ->
                val image = getImage(requireContext(), selectedImage )
                presenter.onNewJPEGFileSelected(image)
            }

        }
    }

    override fun showError(message : String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

}