package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.GithubUsersRepo
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.UserLoginView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.fragment.UserLoginFragment

class UserLoginPresenter(val gihubUser: GithubUser?, val router: Router) :
    MvpPresenter<UserLoginView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        gihubUser?.let {
            viewState.setupUserLogin(it.login)
        }
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}


