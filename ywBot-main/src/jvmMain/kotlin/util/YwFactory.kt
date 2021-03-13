package com.elouyi.util

object YwFactory {

    fun newLogger(name: String): YwLogger = ElyLogger(name)
}