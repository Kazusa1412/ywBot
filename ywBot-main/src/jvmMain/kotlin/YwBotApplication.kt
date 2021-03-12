package com.elouyi

import com.elouyi.config.YwBotConfiguration
import com.elouyi.config.ywConfig
import kotlinx.coroutines.coroutineScope
import net.mamoe.mirai.BotFactory

suspend fun main() = coroutineScope {
    YwBotConfiguration
    val bot = BotFactory.newBot(ywConfig.qqNumber, ywConfig.qqPassword) {
        fileBasedDeviceInfo("config/device.json")
    }

    bot.login()
}