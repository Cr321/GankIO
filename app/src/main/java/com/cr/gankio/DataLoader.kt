package com.cr.gankio

import com.google.gson.Gson

/**
 * Created by RUI CAI on 2017/10/27.
 *
 */
class DataLoader {

    fun getGankNewsList(date:String): List<GankNews> {
        val url = Request.BASE_URL+date
        return Gson().fromJson(Request(url).run(),GankNewsList::class.java).results
    }
}