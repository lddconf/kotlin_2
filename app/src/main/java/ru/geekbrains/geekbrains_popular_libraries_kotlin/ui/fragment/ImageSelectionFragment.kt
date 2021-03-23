package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.databinding.FragmentImageSelectionBinding
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.ImageSelectionPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.ImageSelectionView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.App
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.BackClickListener


class ImageSelectionFragment : MvpAppCompatFragment(), ImageSelectionView, BackClickListener {
    private var imageSelectionBinding: FragmentImageSelectionBinding? = null

    private val presenter by moxyPresenter {
        ImageSelectionPresenter(App.instance.router, AndroidSchedulers.mainThread())
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
        startActivityForResult(Intent.createChooser(intent, "Select a jpeg"), 123)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == RESULT_OK) {
            val selectedfile: Uri? = data?.data //The uri with the location of the file


            selectedfile?.let { file ->
                file.let {
                    presenter.onNewJPEGFileSelected(getPath(file) ?: "")
                }
            }
        }
    }

    private fun getPath(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = requireContext().applicationContext.contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        ) ?: return ""
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val s: String = cursor.getString(column_index)
        cursor.close()
        return s
    }
}