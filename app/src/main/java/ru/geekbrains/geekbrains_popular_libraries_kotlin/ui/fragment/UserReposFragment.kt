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
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.RetrofitGithubUsersRepo
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.UserReposPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.UserReposView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.App
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.BackClickListener
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.adapter.UserReposRVAdapter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.navigation.AndroidScreens

class UserReposFragment : MvpAppCompatFragment(), UserReposView, BackClickListener {

    companion object {
        private const val EXTRA_KEY = "UserReposFragment.GithubUser"

        fun newInstance(githubUser: GithubUser) : UserReposFragment {
            val bundle = Bundle()
            bundle.putParcelable(UserReposFragment.EXTRA_KEY, githubUser)
            val userReposFragment = UserReposFragment()
            userReposFragment.arguments = bundle
            return userReposFragment
        }
    }

    private val presenter by moxyPresenter {
        val githubUser = arguments?.getParcelable<GithubUser>(UserReposFragment.EXTRA_KEY) as GithubUser

        UserReposPresenter(
            githubUser,
            RetrofitGithubUsersRepo(ApiHolder.api),
            App.instance.router,
            AndroidScreens(),
            AndroidSchedulers.mainThread()
        )
    }

    private var vb: FragmentUsersBinding? = null
    private var adapter: UserReposRVAdapter? = null

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
        adapter = UserReposRVAdapter(presenter.userReposListPresenter)
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