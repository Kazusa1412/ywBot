package com.elouyi.util

import com.elouyi.config.ywConfig
import com.elouyi.net.request
import com.elouyi.net.requestSer
import com.elouyi.net.requestSetu
import com.elouyi.pojo.SetuResponse

suspend fun getSetu(keyword: String = "",r18: Int = 0) {
    val url = buildString {
        append("https://api.lolicon.app/setu/?keyword=$keyword&r18=$r18")
        val apiKeys = ywConfig.setu["apiKey"] as List<*>
        val apiKey = apiKeys.shuffled()[0].toString()
        append("&apikey=${apiKey}")
    }
    val res = try {
        requestSer<SetuResponse>(url)
    }catch (e: Exception) {
        throw e
    }
    println(res.toString())

}