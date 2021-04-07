package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.user.IUsersScopeContainer
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.IGithubUsersRepo
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.navigation.IScreens
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.list.IUsersListPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.UsersView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.list.IUserItemView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.adapter.UsersRVAdapter
import javax.inject.Inject
import javax.inject.Named

class UsersPresenter() :
    MvpPresenter<UsersView>() {

    @Inject
    lateinit var userScopeContainer: IUsersScopeContainer

    @Inject
    @field:Named("UIThread")
    lateinit var uiSchelduer: Scheduler

    @Inject
    lateinit var screens: IScreens

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var usersRepo: IGithubUsersRepo

    class UsersListPresenter : IUsersListPresenter<UsersRVAdapter.ViewHolder> {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((IUserItemView) -> Unit)? = null

        override fun bindView(view: UsersRVAdapter.ViewHolder) {
            val user = users[view.pos]
            view.setLogin(user.login)
            user.avatarUrl?.let { avatarUrl ->
                view.loadAvatarUrl(avatarUrl)
            }

        }

        override fun getCount() = users.size
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { view ->
            val user = usersListPresenter.users[view.pos]
            router.navigateTo(screens.userRepos(user))
        }
    }

    private fun loadData() {
        usersRepo.getUsers().observeOn(uiSchelduer)
            .subscribe({ users ->
                usersListPresenter.users.clear()
                usersListPresenter.users.addAll(users)
                viewState.updateList()
            },
                { error ->
                    viewState.showError("Error: ${error.message}")
                    //Handle Error
                })
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        userScopeContainer.releaseUserScope()
        super.onDestroy()
    }
}