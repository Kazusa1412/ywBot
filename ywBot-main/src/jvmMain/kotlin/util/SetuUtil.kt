package com.elouyi.util

import com.elouyi.config.ywConfig
import com.elouyi.net.download
import com.elouyi.net.requestSer
import com.elouyi.pojo.SetuResponse
import java.io.File

suspend fun getSetu(keyword: String = "", r18: Int = 0): SetuResponse {
    val url = buildString {
        append("https://api.lolicon.app/setu/?keyword=$keyword&r18=$r18")
        val apiKeys = ywConfig.setu["apiKey"] as List<*>
        val apiKey = apiKeys.shuffled()[0].toString()
        append("&apikey=${apiKey}")
    }
    utilLogger.i("开始请求 $url")
    return try {
        requestSer(url)
    } catch (e: Exception) {
        utilLogger.e("请求涩图失败")
        throw e
    }
}

suspend fun downloadSetu(setu: SetuResponse): File {
    val path = ywConfig.setu["imgPath"].toString()
    return download(setu.data[0].url,path)
}