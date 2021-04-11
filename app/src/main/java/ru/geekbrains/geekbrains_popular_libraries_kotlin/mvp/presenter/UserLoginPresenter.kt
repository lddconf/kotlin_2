package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.UserLoginView
import javax.inject.Inject

class UserLoginPresenter(val gihubUser: GithubUser?) :
    MvpPresenter<UserLoginView>() {

    @Inject
    lateinit var router: Router

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


