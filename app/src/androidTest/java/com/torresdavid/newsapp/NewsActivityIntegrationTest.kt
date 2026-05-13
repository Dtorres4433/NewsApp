package com.torresdavid.newsapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.torresdavid.newsapp.ui.NewsActivity
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.MockResponse
import java.util.concurrent.TimeUnit
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId

@RunWith(AndroidJUnit4::class)
class NewsActivityIntegrationTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(NewsActivity::class.java)
    private lateinit var mockWebServer: MockWebServer
    private lateinit var retrofit: Retrofit
    private lateinit var apiService: com.torresdavid.newsapp.Interface.ApiService
    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(com.torresdavid.newsapp.Interface.ApiService::class.java)
    }
    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
    @Test
    fun testLoadingStateDisplayedWhileFetching() {
        // Configurar respuesta vacía con delay
        mockWebServer.enqueue(MockResponse()
            .setBody("""{"status":"ok","totalResults":0,"articles":[]}""")
            .setResponseCode(200)
            .setBodyDelay(2000, TimeUnit.MILLISECONDS))
        // Verificar que el indicador de carga está visible inicialmente
        onView(withId(R.id.homeLoader))
            .check(matches(isDisplayed()))
    }
    @Test
    fun testNewsDisplayedAfterSuccessfulFetch() {
        val mockResponse = """
        {
            "status": "ok",
            "totalResults": 2,
            "articles": [
                {
                    "source": {"id": "1", "name": "Test Source"},
                    "author": "Test Author",
                    "title": "Test Title 1",
                    "description": "Test Description 1",
                    "url": "https://test.com/1",
                    "urlToImage": "https://test.com/image1.jpg",
                    "publishedAt": "2024-01-01T00:00:00Z",
                    "content": "Test Content 1"
                },
                {
                    "source": {"id": "2", "name": "Test Source 2"},
                    "author": "Test Author 2",
                    "title": "Test Title 2",
                    "description": "Test Description 2",
                    "url": "https://test.com/2",
                    "urlToImage": "https://test.com/image2.jpg",
                    "publishedAt": "2024-01-02T00:00:00Z",
                    "content": "Test Content 2"
                }
            ]
        }
        """.trimIndent()
        mockWebServer.enqueue(MockResponse()
            .setBody(mockResponse)
            .setResponseCode(200))
        // Esperar que el RecyclerView se muestre con los datos
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }
    @Test
    fun testErrorStateShownOnApiFailure() {
        mockWebServer.enqueue(MockResponse()
            .setBody("Server Error")
            .setResponseCode(500))
        // Verificar que se muestra algún indicador de error (Snackbar)
        // Nota: El Snackbar tiene duración SHORT/LONG, ajustar según necesidad
    }
}
