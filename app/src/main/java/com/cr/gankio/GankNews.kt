package com.cr.gankio

/**
 * Created by RUI CAI on 2017/10/27.
 *
 */
data class GankNews(val _id: String,
                    val createdAt: String,
                    val desc: String,
                    val publishedAt: String,
                    val type: String,
                    val url: String,
                    val used: Boolean,
                    val who: String)
