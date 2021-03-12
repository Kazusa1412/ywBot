package com.elouyi

import com.elouyi.config.YwBotConfiguration
import com.elouyi.config.ywConfig
import com.elouyi.net.request
import com.elouyi.util.getSetu

suspend fun main() {

    YwBotConfiguration
    println(ywConfig.setu["apiKey"] as List<*>)
    getSetu()
    //println(request("https://api.lolicon.app/setu/?keyword=&r18=0&apikey=371688885fd355e21b1715"))
    //println(request("https://www.baidu.com"))
}