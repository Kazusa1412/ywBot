package com.elouyi

/**
 * @param helpMap 类委托
 */
class Result private constructor(private val helpMap: HashMap<String,Any>): Map<String,Any> by helpMap{

    companion object{

        // 部分返回码
        const val MISS_PARAM = 11
        const val UNKNOWN_ERROR = 101
        const val SUCCESS = 0

        /**
         * 生成 [Result] 实例的根方法
         * @param code 返回码
         * @param message 信息
         * @param data 返回的信息
         */
        fun <T> newResult(code: Int,message: String,data: T): Result {
            val map = HashMap<String,Any>()
            map["code"] = code
            map["message"] = message
            map["data"] = data ?: ""
            return Result(map)
        }

        /**
         * 返回一个成功的结果
         * @param data 返回的数据
         */
        fun<T> ok(data: T) = ok("success",data)

        /**
         * 返回一个成功的结果，类型 [T] 可空
         * @param message 信息
         * @param data 返回的数据
         */
        fun<T> ok(message: String,data: T) = newResult(SUCCESS,message,data)

        /**
         * 返回一个错误
         * @param code 错误码
         * @param message 错误信息
         */
        fun error(code: Int,message: String) = newResult(code,message,null)


        /**
         * 返回一个位置错误
         */
        fun error() = error(UNKNOWN_ERROR,"unknown error")

        /**
         * 缺少参数错误
         * @param paramName 缺少的参数
         */
        fun missParam(vararg paramName: String): Result{
            val message = buildString {
                append("param [")
                for (s in paramName) {
                    append(" $s ")
                }
                append("] required but not found")
            }
            return error(MISS_PARAM,message)
        }
    }

    /**
     * set 运算符的重载，可以使用如下语句
     * ``` kotlin
     * val data = HashMap<String,Any>()
     * data["msg"] = "会长是个 LSP"
     * data["xx"] = "是这样的"
     * val ok = Result.ok(data)
     * ok["zz"] = "sd"  // 这里用到了 set 函数
     * ```
     */
    operator fun set(key: String,value: Any){
        helpMap[key] = value
    }
}