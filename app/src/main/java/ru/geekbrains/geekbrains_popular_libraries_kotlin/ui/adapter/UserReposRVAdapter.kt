package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.databinding.ItemUserRepoBinding
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.list.IUserReposListPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.list.IUserRepoItemView

class UserReposRVAdapter(val presenter: IUserReposListPresenter) :
    RecyclerView.Adapter<UserReposRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemUserRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(
            holder.apply { pos = position }
        )

    inner class ViewHolder(val vb: ItemUserRepoBinding) : RecyclerView.ViewHolder(vb.root),
        IUserRepoItemView {
        override var pos = -1

        override fun setReposName(text: String) = with(vb) {
            tvRepoName.text = text
        }

        override fun setLanguage(language: String?): Unit = with(vb) {
            tvRepoLanguage.text = ""
            language?.let {
                tvRepoLanguage.text = it
            }
        }

        override fun setForksCount(forksCount: Long) = with(vb) {
            tvForksCount.text = forksCount.toString()
        }

        override fun setWatchCount(watchCount: Long) = with(vb) {
            tvWatchersCount.text = watchCount.toString()
        }
    }
}