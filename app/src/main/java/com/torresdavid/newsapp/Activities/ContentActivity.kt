package com.torresdavid.newsapp.Activities

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil3.load
import coil3.request.crossfade
import com.torresdavid.newsapp.Classes.Articles
import com.torresdavid.newsapp.R
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ContentActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_content)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val article = intent.getParcelableExtra<Articles>("article")
        if (article != null) {
            val title: TextView = findViewById(R.id.textView)
            val author: TextView = findViewById(R.id.textView2)
            val date: TextView = findViewById(R.id.textView3)
            val description: TextView = findViewById(R.id.textView4)
            val image: ImageView = findViewById(R.id.imageView2)
            val source: TextView = findViewById(R.id.textView6)

            title.text = article.title
            author.text = article.author
            date.text = formatedDate(article.publishedAt)
            description.text = article.description
            image.load(article.urlToImage){
                crossfade(true)
                crossfade(1000)
            }
            source.text = article.source.name
            source.setTextColor(Color.BLUE)
            source.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(intent)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatedDate(publishedAt: String): CharSequence? {
        val zonedDateTime = ZonedDateTime.parse(publishedAt)
        val localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
        val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
        return localDateTime.format(formatter)
    }
}