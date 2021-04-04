package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.api.IDataSource
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUserRepo
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.RoomGithubRepository
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.RoomGithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.db.Database
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.network.INetworkStatus
import java.lang.RuntimeException

class RetrofitGithub(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val db: Database
) : IGithubUsersRepo, IGithubUserRepos {

    override fun getUsers(): Single<List<GithubUser>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                api.getUsers().flatMap { users ->
                    Single.fromCallable {
                        val roomUsers = users.map { user ->
                            RoomGithubUser(user.id, user.login, user.avatarUrl, user.reposUrl)
                        }
                        db.userDao.insert(roomUsers)
                        users
                    }
                }
            } else {
                Single.fromCallable {
                    db.userDao.getAll().map { roomGithubUser ->
                        GithubUser(
                            roomGithubUser.id,
                            roomGithubUser.login,
                            roomGithubUser.avatarUrl,
                            roomGithubUser.reposUrl
                        )
                    }
                }
            }
        }.subscribeOn(Schedulers.io())

    /*
    override fun getUserReposByName(username: String): Single<List<GithubUserRepo>> =
        api.getUserReposByName(username).subscribeOn(Schedulers.io())

    override fun getUserReposByURL(url: String): Single<List<GithubUserRepo>> =
        api.getUserReposByUrl(url).subscribeOn(Schedulers.io())
*/
    override fun getUserRepos(user: GithubUser): Single<List<GithubUserRepo>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                user.reposUrl?.let { url ->
                    api.getUserReposByUrl(url).flatMap { repos ->
                        Single.fromCallable {
                            val roomUser =
                                db.userDao.findByLogin(user.login)
                            val roomRepos = repos.map { repo ->
                                RoomGithubRepository(
                                    id = repo.id,
                                    name = repo.name,
                                    fullName = repo.fullName,
                                    language = repo.language,
                                    forksCount = repo.forksCount,
                                    watchersCount = repo.watchersCount,
                                    defaultBranch = repo.defaultBranch,
                                    userId = roomUser.id
                                )
                            }
                            db.repositoryDao.insert(roomRepos)
                            repos
                        }
                    }
                } ?: Single.error<List<GithubUserRepo>>(RuntimeException("No user repos"))
            } else {
                Single.fromCallable {
                    val roomUser = db.userDao.findByLogin(user.login)
                    db.repositoryDao.findForUser(roomUser.id).map { roomRepo ->
                        GithubUserRepo(
                            id = roomRepo.id,
                            fullName = roomRepo.fullName,
                            name = roomRepo.name,
                            language = roomRepo.language,
                            forksCount = roomRepo.forksCount,
                            watchersCount = roomRepo.watchersCount,
                            defaultBranch = roomRepo.defaultBranch
                        )
                    }
                }
            }
        }.subscribeOn(Schedulers.io())
}