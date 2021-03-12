package com.elouyi.net

import com.elouyi.pojo.SetuResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*


suspend fun request(url: String): HttpResponse {
    val client = HttpClient {
        install(UserAgent) {
            agent = "User-Agent\",\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36"
        }
        //BrowserUserAgent()
    }

    return client.get(url) {
        header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
        //userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
    }

}

suspend fun requestSetu(url: String): SetuResponse {
    val client = HttpClient {
        //BrowserUserAgent()
        install(UserAgent) {
            agent = "User-Agent\",\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36"
        }
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }
    return client.get(url)
}

suspend inline fun <reified R> requestSer(url: String): R {
    val client = HttpClient {
        //BrowserUserAgent()
        install(UserAgent) {
            agent = "User-Agent\",\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36"
        }
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }
    return client.get(url)
}