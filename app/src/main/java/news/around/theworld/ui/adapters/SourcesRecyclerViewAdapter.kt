package news.around.theworld.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_sources.view.*
import news.around.theworld.R
import news.around.theworld.model.Source
import news.around.theworld.model.SourceList

class SourcesRecyclerViewAdapter(
     private val sourceList: SourceList
    ,private val seeMoreClickListener: (source: Source) -> Unit)

    : RecyclerView.Adapter<SourcesRecyclerViewAdapter.NewsViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): NewsViewHolder {
        val view = LayoutInflater
             .from(viewGroup.context)
            .inflate(R.layout.item_sources, viewGroup, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int = sourceList.sources.size

    fun getSource(position: Int) = sourceList.sources[position]

    override fun onBindViewHolder(viewHolder: NewsViewHolder, position: Int) {
        viewHolder.bind(getSource(position), seeMoreClickListener)
    }

    inner class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val sourceTitle: TextView by lazy { itemView.title_news }
        private val sourceDescription: TextView by lazy { itemView.description_news }
        private val sourceLink: TextView by lazy { itemView.link_news }

        fun bind(source: Source, seeMoreClickListener: (source: Source) -> Unit){
            sourceTitle.text = source.name
            sourceDescription.text = source.description
            sourceLink.text = source.url
            itemView.setOnClickListener { seeMoreClickListener.invoke(getSource(adapterPosition)) }
        }

    }
}