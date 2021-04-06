package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUserRepo
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.RoomGithubRepository
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.db.Database

class RoomGithubReposCache(val db: Database) : IRepositoriesCache {



    override fun getAll(user: GithubUser): Single<List<GithubUserRepo>> = Single.fromCallable {
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
    }.subscribeOn(Schedulers.io())

    override fun put(user: GithubUser, repos: List<GithubUserRepo>) : Completable = Completable.fromCallable {
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
    }.subscribeOn(Schedulers.io())
}