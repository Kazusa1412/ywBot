package com.elouyi.net

import com.elouyi.pojo.SetuResponse
import com.elouyi.util.utilLogger
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream


suspend fun request(url: String): HttpResponse {
    val client = HttpClient()

    val res: HttpResponse = client.get(url) {
        //header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
        userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
    }
    client.close()
    return res
}

suspend inline fun <reified R> requestSer(url: String): R {
    val client = HttpClient {
        BrowserUserAgent()
//        install(UserAgent) {
//            agent = "User-Agent\",\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36"
//        }
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }
    val res: R = client.get(url)
    client.close()
    return res
}

suspend fun download(url: String,path: String): File {
    utilLogger.i("开始下载 $url")
    val fileName = url.slice(url.lastIndexOf("/") until url.length)
    val file = File(if (path.endsWith("/")) path + fileName else "$path/$fileName")
    if (file.exists()) return file
    val inputStream = request(url).receive<InputStream>()
    val out = file.outputStream()
    withContext(Dispatchers.IO) {
        out.write(inputStream.readBytes())
        out.close()
        inputStream.close()
    }
    return file
}