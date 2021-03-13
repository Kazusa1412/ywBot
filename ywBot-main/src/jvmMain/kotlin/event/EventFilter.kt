package com.elouyi.event

import net.mamoe.mirai.event.events.GroupMessageEvent


interface GroupEventFilter {
    suspend fun filterEvent(groupMessageEvent: GroupMessageEvent)
}