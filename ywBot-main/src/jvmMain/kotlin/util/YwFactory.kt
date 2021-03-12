package com.elouyi.util

object YwFactory {

    fun newBot(name: String): YwLogger = ElyLogger(name)
}