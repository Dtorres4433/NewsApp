package com.torresdavid.newsapp.Adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.torresdavid.newsapp.Classes.News
import com.torresdavid.newsapp.R
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class NewsAdapter(private val dataNews: Array<News>): RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvTitle: TextView = view.findViewById(R.id.title)
        val tvDate: TextView = view.findViewById(R.id.published_at)
        val tvAuthor: TextView = view.findViewById(R.id.author)
        val tvDescription: TextView = view.findViewById(R.id.description)
        val tvImage: ImageView = view.findViewById(R.id.imageView)
        val tvSource: TextView = view.findViewById(R.id.source)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_card, parent, false)
        return MyViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvTitle.text = convertHtmlEntities(dataNews[position].title)
        holder.tvDate.text = formatedDate(dataNews[position].published_at)
        if (dataNews[position].author == null){
            holder.tvAuthor.text = "Author: N/A"
        } else {
            holder.tvAuthor.text = "Author: ${convertHtmlEntities(dataNews[position].author)}"
        }
        holder.tvDescription.text = convertHtmlEntities(dataNews[position].description)
        holder.tvSource.text = convertHtmlEntities(dataNews[position].source)
        if (dataNews[position].image == null){
            holder.tvImage.visibility = View.GONE
        } else {
            holder.tvImage.load(dataNews[position].image){
                crossfade(true)
                crossfade(1000)
            }
        }
    }

    private fun convertHtmlEntities(description: String): String {
        return HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatedDate(publishedAt: String): String? {
        val zonedDateTime = ZonedDateTime.parse(publishedAt)
        val localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return localDateTime.format(formatter)
    }

    override fun getItemCount(): Int {
        return dataNews.size
    }
}