package news.around.theworld.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_articles.view.*
import news.around.theworld.R
import news.around.theworld.extension.loadImageFromUrl
import news.around.theworld.model.Article
import news.around.theworld.model.ArticleList

class ArticlesRecyclerViewAdapter()
    : RecyclerView.Adapter<ArticlesRecyclerViewAdapter.NewsViewHolder>() {

    private val list: MutableList<Article> = mutableListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): NewsViewHolder {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.item_articles, viewGroup, false)
        return NewsViewHolder(view)
    }

    fun initContent(newList: ArticleList){
        this.list.clear()
        this.list.addAll(newList.articleList)
        notifyDataSetChanged()
    }

    fun update(newList: ArticleList){
        this.list.addAll(newList.articleList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    private fun getArticle(position: Int) = list[position]

    override fun onBindViewHolder(viewHolder: NewsViewHolder, position: Int) {
        viewHolder.bind(getArticle(position))
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val articleTitle: TextView by lazy { itemView.article_title }
        private val articleDescription: TextView by lazy { itemView.article_description }
        private val articleAuthor: TextView by lazy { itemView.article_author }
        private val articleThumbnail: ImageView by lazy { itemView.article_thumbnail }
        private val articlePublishedDay: TextView by lazy { itemView.article_published_day }

        fun bind(article: Article) {
            articleTitle.text = article.title
            articleDescription.text = article.description
            articleAuthor.text = article.author
            articleThumbnail.loadImageFromUrl(article.urlToImage)
            articlePublishedDay.text = article.getPublishedDay()
        }
    }
}