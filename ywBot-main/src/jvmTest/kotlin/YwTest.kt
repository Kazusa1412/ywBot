package com.elouyi

import com.elouyi.config.YwBotConfiguration
import com.elouyi.config.ywConfig
import com.elouyi.util.ElyLogger
import org.junit.Test

class YwTest {

    init {
        YwBotConfiguration
    }

    val logger = ElyLogger("test")
    @Test
    fun zz(){

        logger.e("zzz")

    }

    @Test
    fun config(){

        ywConfig.forEach { t, u ->
            println("t is $t and u is $u")
            println(u.javaClass)
        }
        logger.i(ywConfig.qqNumber.toString())
        logger.i("password is ${ywConfig.qqPassword}")
        try {
            //logger.e("zz is ${ywConfig["zz"]}")
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }
}