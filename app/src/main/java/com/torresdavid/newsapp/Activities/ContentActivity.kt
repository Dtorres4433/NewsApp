package com.torresdavid.newsapp.Activities

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import coil3.load
import coil3.request.crossfade
import com.google.android.material.navigation.NavigationView
import com.torresdavid.newsapp.Classes.Articles
import com.torresdavid.newsapp.R
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ContentActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

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
        drawerLayout = findViewById(R.id.main)
        navView = findViewById(R.id.nav_view)
        val toolbar:Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    /*val intent = Intent(this, NewsActivity::class.java)
                    startActivity(intent)*/
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_search -> {
                    /*val intent = Intent(this, SearchActivity::class.java)
                    startActivity(intent)*/
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }
                R.id.bookmark -> {
/*                    val intent = Intent(this, BookMarkActivity::class.java)
                    startActivity(intent)*/
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()

                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        toggle = ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val article = intent.getParcelableExtra<Articles>("article")
        if (article != null) {
            val title: TextView = findViewById(R.id.textView)
            val author: TextView = findViewById(R.id.textView2)
            val date: TextView = findViewById(R.id.textView3)
            val description: TextView = findViewById(R.id.textView4)
            val image: ImageView = findViewById(R.id.imageView2)
            val source: TextView = findViewById(R.id.textView6)

            title.text = article.title
            if (article.author == null){
                author.text = "Unknown Author"
            } else {
                author.text = article.author
            }
            date.text = formatedDate(article.publishedAt)
            if (article.content == null){
                description.text = "N/A"
                description.visibility = TextView.GONE
            } else {
                description.text = article.content
            }
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
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
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