package com.elouyi.event

import com.elouyi.config.ywConfig
import com.elouyi.endl
import com.elouyi.githubUrl
import com.elouyi.pojo.SetuResponse
import com.elouyi.util.*
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage

val logger: YwLogger = YwFactory.newLogger("SetuEvent")

suspend fun GroupMessageEvent.sendSetu() {
    logger.v("开始请求涩图 $senderName -> ${group.id}")
    val setu = getSetu()
    val imgFile = downloadSetu(setu).uploadAsImage(subject)
    val msg = formatSetu(setu,imgFile)
    try {
        subject.sendMessage(msg).recallIn(ywConfig.setu["recallTime"].toString().toLong() * 1000L)
    } catch (e: Exception) {
        subject.sendMessage(e.message.toString())
    }
}

object SetuEventFilter : GroupEventFilter {

    override suspend fun filterEvent(groupMessageEvent: GroupMessageEvent) {
        val content = groupMessageEvent.message.content
        content.startsWith("") {

        }
        content.equals(ywConfig.setu["keyword"].toString()) {
            logger.i("进入equals")
            groupMessageEvent.sendSetu()
        }
        content.equals(ywConfig.setu["r18Keyword"].toString()) {
            groupMessageEvent.subject.sendMessage("该功能重构中，现在暂时可用 [会长来]$endl 想要更多功能？ 欢迎来提交pr$endl$githubUrl")
        }
    }
}

fun GroupMessageEvent.formatSetu(setu: SetuResponse, file: Image) = buildMessageChain {
    + At(sender)
    + endl
    + setu.data[0].title
    + endl
    + file
    + "tags: $endl"
    setu.data[0].tags.forEach {
        + it
    }
    + "\n"
    + setu.data[0].fileName
}