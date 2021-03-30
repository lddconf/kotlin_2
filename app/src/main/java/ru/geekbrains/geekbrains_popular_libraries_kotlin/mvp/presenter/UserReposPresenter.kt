package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUserRepo
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.IGithubUserRepos
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.navigation.IScreens
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.list.IUserReposListPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.UserReposView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.UsersView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.list.IUserRepoItemView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.adapter.UserReposRVAdapter

class UserReposPresenter(
    val githubUser: GithubUser?,
    val userRepos: IGithubUserRepos,
    val router: Router,
    val screens: IScreens,
    val uiSchelduer: Scheduler
) :
    MvpPresenter<UserReposView>() {

    class UserReposListPresenter : IUserReposListPresenter<UserReposRVAdapter.ViewHolder> {
        val repos = mutableListOf<GithubUserRepo>()
        override var itemClickListener: ((IUserRepoItemView) -> Unit)? = null

        override fun bindView(view: UserReposRVAdapter.ViewHolder) {
            val repo = repos[view.pos]

            view.setReposName(repo.name)
            view.setLanguage(repo.language)
            view.setForksCount(repo.forksCount)
            view.setWatchCount(repo.watchersCount)
        }

        override fun getCount() = repos.size
    }

    val userReposListPresenter = UserReposListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
    }

    fun loadData() {
        githubUser?.let { user ->
            userRepos.getUserRepos(user.login).observeOn(uiSchelduer)
                .subscribe({ users ->
                    userReposListPresenter.repos.clear()
                    userReposListPresenter.repos.addAll(users)
                    viewState.updateList()
                },
                    { error ->
                        viewState.showError("Error: ${error.message}")
                        //Handle Error
                    })
        }
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}