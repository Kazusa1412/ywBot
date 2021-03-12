package com.elouyi.util


/**
 * yw 日志，通过 [] 函数构建, 目前的实现类是 []
 *
 * 有 5 种日志级别：verbose、debug、info、warn 和 error，也可以关闭日志
 *
 * 用法：
 * ```
 * logger.v(message)
 * logger.d(message)
 * logger.i(message)
 * logger.w(message)
 * logger.e(message)
 * ```
 */
interface YwLogger {

    /**
     * 日志的控制级别
     */
    var level: LogLevel

    /**
     * logger 所有者的 name
     */
    val name: String

    /**
     * 输出方式，默认 println
     */
    var out: (Any?) -> Unit

    /**
     * verbose 级别的日志
     * @param message 日志的内容
     */
    fun v(message: String?)

    /**
     * debug 级别的日志
     */
    fun d(message: String?)

    /**
     * info 级别的日志
     */
    fun i(message: String?)

    /**
     * warning 级别的日志
     */
    fun w(message: String?)

    /**
     * error 级别的日志
     */
    fun e(message: String?)
}


/**
 * 日志级别
 */
sealed class LogLevel(val lv: Int) : Comparable<LogLevel>{

    object VERBOSE : LogLevel(1)
    object DEBUG : LogLevel(2)
    object INFO : LogLevel(3)
    object WARN : LogLevel(4)
    object ERROR : LogLevel(5)
    object DISABLE : LogLevel(100)

    // TODO: 2021/3/12 增加 trace 级别 

    companion object{
        fun getLevel(l: Int) = when{
            l < 2 -> VERBOSE
            l < 3 -> DEBUG
            l < 4 -> INFO
            l < 5 -> WARN
            l < 6 -> ERROR
            else -> DISABLE
        }
    }

    override operator fun compareTo(other: LogLevel) = this.lv.compareTo(other.lv)

    override fun equals(other: Any?) = when(other){
        is LogLevel -> this.lv == other.lv
        is Int -> this.lv == other
        else -> false  //super.equals(other)
    }

    override fun hashCode() = lv
}

abstract class YwLoggerAbstract : YwLogger {

    override var level: LogLevel = LogLevel.VERBOSE

    override var out: (Any?) -> Unit = {
        println(it.toString())
    }

    fun setLevel(l: Int){
        level = LogLevel.getLevel(l)
    }

    final override fun v(message: String?) {
        if (level > LogLevel.VERBOSE) return
        vv(message)
    }

    final override fun d(message: String?) {
        if (level > LogLevel.DEBUG) return
        dd(message)
    }

    final override fun i(message: String?) {
        if (level > LogLevel.INFO) return
        ii(message)
    }

    final override fun w(message: String?) {
        if (level > LogLevel.WARN) return
        ww(message)
    }

    final override fun e(message: String?) {
        if (level > LogLevel.ERROR) return
        ee(message)
    }

    protected abstract fun vv(message: String?)
    protected abstract fun dd(message: String?)
    protected abstract fun ii(message: String?)
    protected abstract fun ww(message: String?)
    protected abstract fun ee(message: String?)
}

class SimpleLogger(override val name: String) : YwLoggerAbstract() {
    override fun vv(message: String?) {
        out("Verbose [$name] $message")
    }

    override fun dd(message: String?) {
        out("Debug [$name] $message")
    }

    override fun ii(message: String?) {
        out("Info [$name] $message")
    }

    override fun ww(message: String?) {
        out("Warn [$name] $message")
    }

    override fun ee(message: String?) {
        out("Error [$name] $message")
    }
}
