package com.husenansari.offlinefirst.model

data class MainResponse(
    val has_more: Boolean,
    val items: ArrayList<Item>,
    val quota_max: Int,
    val quota_remaining: Int
)