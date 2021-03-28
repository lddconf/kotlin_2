package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.IGithubUsersRepo
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.navigation.IScreens
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.list.IUsersListPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.UsersView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.list.IUserItemView

class UsersPresenter(
    val usersRepo: IGithubUsersRepo,
    val router: Router,
    val screens: IScreens,
    val uiSchelduer: Scheduler
) :
    MvpPresenter<UsersView>() {

    class UsersListPresenter : IUsersListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((IUserItemView) -> Unit)? = null

        override fun bindView(view: IUserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
            view.loadAvatarUrl(user.avatarUrl)
        }

        override fun getCount() = users.size
    }

    val usersListPresenter = UsersListPresenter()
    val compsitDispose = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { view ->
            val user = usersListPresenter.users[view.pos]
            router.navigateTo(screens.userLogin(user))
        }
    }

    fun loadData() {
        //оператор switchMap, в отличии от оператора flatMap,
        //обрабатывает (и передает результат дальше по цепочке) только последний элемент из последовательности, поступившей на вход.
        val disposable = usersRepo.getUsers().observeOn(uiSchelduer)
            .subscribe({ users ->
                usersListPresenter.users.clear()
                usersListPresenter.users.addAll(users)
                viewState.updateList()
            },
                { error ->
                    viewState.showError("Error: ${error.message}")
                    //Handle Error
                })
        compsitDispose.add(disposable)
    }

    fun backClick(): Boolean {
        compsitDispose.dispose()
        router.exit()
        return true
    }
}