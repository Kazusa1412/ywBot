package com.elouyi.config

import com.elouyi.util.YwFactory
import org.yaml.snakeyaml.Yaml
import java.io.File
import kotlin.system.exitProcess

lateinit var ywConfig: YwConfig

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
            logger.w("未检测到配置文件，已重新生产 ${configFile.path}. 请前往此文件完善登录信息")
            logger.i("回车以结束程序")
            readLine()
            exitProcess(0)
        }
        logger.v("读取配置文件")
        ywConfig = YwConfig(Yaml().load(configFile.inputStream()))
        try {
            ywConfig.qqNumber
            ywConfig.qqPassword
        }catch (e: Exception){
            logger.e("找不到登录的qq信息，请前往 ${configFile.path} 完善登录信息")
            readLine()
            exitProcess(0)
        }
        val imgPath = File(ywConfig.setu["imgPath"].toString())
        if (!imgPath.exists()) {
            imgPath.mkdir()
        }
        logger.v("登录qq为 ${ywConfig.qqNumber} 开始登录")
    }

    /**
     * 重新加载 yml 配置文件
     */
    fun reload() {
        ywConfig = YwConfig(Yaml().load(configFile.inputStream()))
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