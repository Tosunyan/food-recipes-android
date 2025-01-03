package com.tosunyan.foodrecipes.network.api

import com.tosunyan.foodrecipes.common.utils.logInfo
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.UTF_8
import kotlin.time.Duration
import kotlin.time.measureTimedValue

object LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        logApiRequest(request)

        val responseTimed = measureTimedValue {
            try {
                chain.proceed(request)
            } catch (e: Exception) {
                logInfo("ApiResponse", "Failed to make request: ${e.message}")
                throw e
            }
        }

        logApiResponse(responseTimed.value, responseTimed.duration)

        return responseTimed.value
    }

    private fun logApiRequest(request: Request) {
        logInfo("ApiRequest", "${request.method} ${request.url}")
    }

    private fun logApiResponse(response: Response, duration: Duration) {
        val responseMessage = " ${response.message}"
            .takeIf(String::isNotBlank)
            .orEmpty()

        logInfo(
            tag = "ApiResponse",
            message = """
                {
                    ${response.code}$responseMessage ${response.request.url} (${duration.inWholeMilliseconds}ms)

                    ${response.bodyAsString()}
                }
            """.trimIndent()
        )
    }

    private fun Response.bodyAsString() : String? {
        val body = body ?: return null
        val contentType = body.contentType()
        val contentLength = body.contentLength()
        val source = body.source()
        val charset: Charset = contentType?.charset(UTF_8) ?: UTF_8

        return source
            .takeIf { contentLength != 0L }
            ?.also { it.request(Long.MAX_VALUE) }
            ?.buffer
            ?.clone()
            ?.readString(charset)
    }
}