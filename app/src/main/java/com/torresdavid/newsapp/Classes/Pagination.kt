package com.torresdavid.newsapp.Classes

data class Pagination(
    var limit: Int,
    var offset: Int,
    var count: Int,
    var total: Int)
