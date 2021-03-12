package com.elouyi.config

import com.elouyi.YwConfigKeyNotFoundException
import com.elouyi.util.utilLogger

class YwConfig(private val helpMap: HashMap<String,Any>): Map<String,Any> by helpMap{

    val basic: Map<String,Any>
        get() = get("basic") as Map<String, Any>

    val setu: Map<String,Any>
        get() = get("setu") as Map<String, Any>

    val qqNumber: Long
        get() = basic["qqNumber"].toString().toLong()

    val qqPassword: String
        get() = basic["qqPassword"].toString()


    operator fun set(key: String,value: Any){
        helpMap[key] = value
    }

    override operator fun get(key: String): Any? {
        val res = helpMap[key]
        if (res == null){
            utilLogger.e("ywConfig get a null value")
        }
        return res
    }
}