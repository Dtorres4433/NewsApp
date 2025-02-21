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
import com.torresdavid.newsapp.Classes.Articles
import com.torresdavid.newsapp.R
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class NewsAdapter(private val dataNews: Array<Articles>): RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    var onItemClick: ((Articles) -> Unit)? = null

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
        holder.tvDate.text = "Date: ${formatedDate(dataNews[position].publishedAt)}"
        if (dataNews[position].author == null){
            holder.tvAuthor.text = "Author: N/A"
        } else {
            holder.tvAuthor.text = "Author: ${convertHtmlEntities(dataNews[position].author!!)}"
        }
        if(dataNews[position].description != null){
            holder.tvDescription.text = convertHtmlEntities(dataNews[position].description!!)
        }else{
            holder.tvDescription.text = "N/A"
        }
        holder.tvSource.text = "Source: ${convertHtmlEntities(dataNews[position].source.name)}"
        if (dataNews[position].urlToImage == null){
            holder.tvImage.setBackgroundResource(R.drawable.img)
        } else {
            holder.tvImage.load(dataNews[position].urlToImage){
                crossfade(true)
                crossfade(1000)
            }
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(dataNews[position])
        }
    }

    override fun getItemCount(): Int {
        return dataNews.size
    }

    private fun convertHtmlEntities(description: String): String {
        return HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatedDate(publishedAt: String): String? {
        val zonedDateTime = ZonedDateTime.parse(publishedAt)
        val localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
        val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
        return localDateTime.format(formatter)
    }
}