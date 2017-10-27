package com.cr.gankio

import java.net.URL

/**
 * Created by RUI CAI on 2017/10/27.
 *
 */
class Request(val url: String) {

    companion object {
        val BASE_URL = "http://gank.io/api/"
    }

    fun run(): String {
        val resultStr = URL(url).readText()
        return resultStr
    }
}