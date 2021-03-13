package com.elouyi

import com.elouyi.config.YwBotConfiguration
import com.elouyi.config.ywConfig
import com.elouyi.net.download
import com.elouyi.util.downloadSetu
import com.elouyi.util.getSetu


suspend fun main() {

    YwBotConfiguration
    //println(ywConfig.setu["apiKey"] as List<*>)

    val setu = getSetu()
    val file = downloadSetu(setu)
    println(file.path)
}