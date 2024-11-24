package com.torresdavid.newsapp.Classes

import androidx.compose.ui.geometry.Offset

data class Pagination(
    var limit: Int,
    var offset: Int,
    var count: Int,
    var total: Int)
