package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.databinding.FragmentUsersBinding
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.api.ApiHolder
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.RoomGithubAvatarCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.RoomGithubUsersCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.RoomGithubReposCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.db.Database
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.RetrofitGithub
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.UsersPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.UsersView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.App
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.BackClickListener
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.adapter.UsersRVAdapter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.image.GlideImageLoader
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.navigation.AndroidScreens
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.network.AndroidNetworkStatus

class UsersFragment : MvpAppCompatFragment(), UsersView, BackClickListener {

    companion object {
        fun newInstance() = UsersFragment()
    }

    private val presenter by moxyPresenter {
        UsersPresenter(
                RetrofitGithub(
                        ApiHolder.api,
                        AndroidNetworkStatus(App.instance),
                        RoomGithubUsersCache(Database.getInstance()),
                        RoomGithubReposCache(Database.getInstance())
                ),
                AndroidSchedulers.mainThread()
        ).apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var vb: FragmentUsersBinding? = null
    private var adapter: UsersRVAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) = FragmentUsersBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun init() {
        vb?.rvUsers?.layoutManager = LinearLayoutManager(requireContext())
        adapter = UsersRVAdapter(
                presenter.usersListPresenter,
                GlideImageLoader(
                        RoomGithubAvatarCache(
                                Database.getInstance()
                        ),
                        AndroidSchedulers.mainThread()
                )
        )
        vb?.rvUsers?.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backClick()

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}