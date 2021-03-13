package com.elouyi

import com.elouyi.config.YwBotConfiguration
import com.elouyi.config.ywConfig
import com.elouyi.event.SetuEventFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.utils.BotConfiguration

suspend fun main() = coroutineScope<Unit> {
    YwBotConfiguration
    val bot = BotFactory.newBot(ywConfig.qqNumber, ywConfig.qqPassword) {
        fileBasedDeviceInfo("config/device.json")
        protocol = BotConfiguration.MiraiProtocol.ANDROID_PAD
    }

    val botJob = launch(Dispatchers.IO) {
        bot.login()
        YwBotApplication(bot).run()
    }

    botJob.join()
}

class YwBotApplication(private val bot: Bot) {

    suspend fun run() = coroutineScope {
        bot.eventChannel.subscribeAlways<GroupMessageEvent> {
            val func = SetuEventFilter.filterEvent(this)
        }
    }
}