package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.databinding.FragmentUserLoginBinding
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.GithubUsersRepo
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.UserLoginPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.UserLoginView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.App
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.BackClickListener

class UserLoginFragment : MvpAppCompatFragment(), UserLoginView, BackClickListener {
    private var userLoginBinding : FragmentUserLoginBinding ?= null

    private val presenter by moxyPresenter {
        UserLoginPresenter(getGithubUser(), App.instance.router)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUserLoginBinding.inflate(inflater, container, false).also {
        userLoginBinding = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        userLoginBinding = null
    }

    override fun setupUserLogin(login: String) {
        userLoginBinding?.userLogin?.text = login
    }

    private fun getGithubUser() : GithubUser? {
        return arguments?.getParcelable<GithubUser>(EXTRA_KEY)
    }

    companion object {
        private const val EXTRA_KEY = "UserLoginFragment.GithubUser"

        fun newInstance(githubUser: GithubUser) : UserLoginFragment {
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_KEY, githubUser)
            val userLoginFragment = UserLoginFragment()
            userLoginFragment.arguments = bundle
            return userLoginFragment
        }
    }

    override fun backPressed(): Boolean = presenter.backClick()
}