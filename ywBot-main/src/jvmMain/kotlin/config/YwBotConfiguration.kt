package com.elouyi.config

import com.elouyi.util.YwFactory
import java.io.File
import kotlin.system.exitProcess

object YwBotConfiguration {
    val configDir = "config"
    val configName = "botConfig.yml"
    
    private val configFile = File("$configDir/$configName")

    private val logger = YwFactory.newBot("YwConfig")
    
    init {
        val dir = File(configDir)
        if (!dir.exists()) dir.mkdir()
        if (!configFile.exists()){
            createNewYml()
        }
    }

    /**
     * 重新加载 yml 配置文件
     */
    fun reload() {

    }

    private fun createNewYml() {
        try {
            if (configFile.exists()) configFile.delete()
            configFile.createNewFile()
            configFile.outputStream().apply {
                write(defaultConfig.toByteArray())
                close()
            }
        } catch (e: Exception) {
            logger.e("创建配置文件 ${configFile.path} 失败: ${e.message}")
            exitProcess(0)
        }
    }

    private const val defaultConfig = ""
}