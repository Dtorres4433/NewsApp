package com.torresdavid.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade

class NewsAdapter(private val dataNews: Array<News>): RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvTitle: TextView = view.findViewById(R.id.title)
        val tvDate: TextView = view.findViewById(R.id.published_at)
        val tvAuthor: TextView = view.findViewById(R.id.author)
        val tvDescription: TextView = view.findViewById(R.id.description)
        val tvImage: ImageView = view.findViewById(R.id.imageView)
        val tvSource: TextView = view.findViewById(R.id.source)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_card, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsAdapter.MyViewHolder, position: Int) {
        holder.tvTitle.text = dataNews[position].title
        holder.tvDate.text = dataNews[position].publishedAt
        holder.tvAuthor.text = dataNews[position].author
        holder.tvDescription.text = dataNews[position].description
        holder.tvSource.text = dataNews[position].source
        holder.tvImage.load(dataNews[position].image){
            crossfade(true)
            crossfade(1000)
        }
    }

    override fun getItemCount(): Int {
        return dataNews.size
    }
}