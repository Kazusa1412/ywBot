package com.elouyi.util

import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class ElyLogger(
    override val name: String,
    config: ElyLogger.() -> Unit = {}
) : YwLoggerAbstract(){

    init {
        config()
    }

    /**
     * logger 配置,可在主构造中配置 [filePath] 和 [fileEnable]
     */
    private val logConfig: LogConfig = LogConfig()

    /**
     * 日志写入的文件，如果 [LogConfig.fileEnable] 为 false 则不会启用
     */
    private val file by lazy {
        val f = File(logConfig.filePath)
        if (!f.exists()){
            try {
                f.createNewFile()
                this.i("日志文件不存在，已重新生产 ${f.path}")
            }catch (e: Exception){
                this.e("生成日志文件失败，关闭文件开关")
                logConfig.fileEnable = false
            }
        }
        f
    }

    /**
     * 日志写入的输出流
     */
    private lateinit var fout: FileOutputStream


    private data class LogConfig(var fileEnable: Boolean = false,var filePath: String = "yw.log")

    fun filePath(path: String){
        logConfig.filePath = path
    }

    fun fileEnable(enable: Boolean = true){
        logConfig.fileEnable = enable
    }

    private fun yout(message: String?,level: String,color: YwColor = YwColor.ANSI_WHITE){
        val content = "${now()} $level [$name] : $message"
        out(color.toString() + content + YwColor.ANSI_RESET)
        if (logConfig.fileEnable){
            writeToFile(content)
        }
    }

    override fun vv(message: String?) {
        yout(message,"Verbose",YwColor.ANSI_WHITE)
    }

    override fun dd(message: String?) {
        yout(message,"Debug",YwColor.ANSI_GREEN)
    }

    override fun ii(message: String?) {
        yout(message,"Info",YwColor.ANSI_BLUE)
    }

    override fun ww(message: String?) {
        yout(message,"Warn",YwColor.ANSI_YELLOW)
    }

    override fun ee(message: String?) {
        yout(message,"Error",YwColor.ANSI_RED)
    }

    private fun now(): String{
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return LocalDateTime.now().format(format)
    }

    private enum class YwColor(val color: String){
        ANSI_RESET("\u001B[0m"),
        ANSI_RED("\u001B[31m"),
        ANSI_GREEN("\u001B[32m"),
        ANSI_BLUE("\u001B[34m"),
        ANSI_YELLOW("\u001B[33m"),
        ANSI_PURPLE("\u001B[35m"),
        ANSI_WHITE("\u001B[37m");

        override fun toString() = color
    }

    private fun writeToFile(content: String){
        if (file.canWrite()){
            try {
                fout = FileOutputStream(file,true)
                fout.write((content + "\n").toByteArray())
                fout.close()
            } catch (e: Exception) {
                this.e("$name logger 写入文件发生错误")
                e.printStackTrace()
            }
        }else{
            this.e("日志文件不可写: ${file.path}")
        }
    }
}