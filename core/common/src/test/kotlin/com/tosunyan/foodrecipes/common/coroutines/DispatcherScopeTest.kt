package com.tosunyan.foodrecipes.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DispatcherScopeTest {

    private val testDispatcherProvider = TestDispatcherProvider

    private val dispatcherScope = object : DispatcherScope {
        override val dispatcherProvider = testDispatcherProvider
    }

    @Test
    fun `withIOScope executes block on IO dispatcher and returns result`() {
        runTest(testDispatcherProvider.Main) {
            var usedDispatcher: CoroutineDispatcher? = null
            val result = dispatcherScope.withIOScope {
                usedDispatcher = currentCoroutineContext()[CoroutineDispatcher]
                "IO Result"
            }

            assertNotNull(result)
            assertEquals("IO Result", result.getOrNull())
            assertEquals(true, result.isSuccess)
            assertEquals(testDispatcherProvider.IO, usedDispatcher)
        }
    }

    @Test
    fun `withIOScope captures exception and returns failure result`() {
        runTest(testDispatcherProvider.Main) {
            val result = dispatcherScope.withIOScope<String> {
                throw Exception("Test Exception")
            }

            assertNotNull(result)
            assertEquals(false, result.isSuccess)
            assertEquals("Test Exception", result.exceptionOrNull()?.message)
        }
    }

    @Test
    fun `withIOScope returns null if block result is null`() {
        runTest(testDispatcherProvider.Main) {
            val result = dispatcherScope.withIOScope<String?> { null }

            assertNotNull(result)
            assertEquals(true, result.isSuccess)
            assertNull(result.getOrNull())
        }
    }

    @Test
    fun `withIOScope does not catch CancellationException`() {
        runTest(testDispatcherProvider.Main) {
            assertFailsWith<CancellationException> {
                dispatcherScope.withIOScope {
                    throw CancellationException("Test cancellation")
                }
            }
        }
    }

    @Test
    fun `withIOScope preserves exception type`() {
        runTest(testDispatcherProvider.Main) {
            class CustomException : Exception("Custom")

            val result = dispatcherScope.withIOScope<String> {
                throw CustomException()
            }

            assertIs<CustomException>(result.exceptionOrNull())
            assertEquals("Custom", result.exceptionOrNull()?.message)
        }
    }

    @Test
    fun `multiple concurrent withIOScope calls execute independently`() {
        runTest(testDispatcherProvider.Main) {
            val results = (1..10).map { index ->
                async {
                    dispatcherScope.withIOScope {
                        delay(10)
                        index
                    }
                }
            }.awaitAll()

            assertEquals((1..10).toList(), results.map { it.getOrNull() })
        }
    }

    @Test
    fun `withIOResultOrNull returns null on exception`() {
        runTest(testDispatcherProvider.Main) {
            val result = dispatcherScope.withIOResultOrNull {
                throw Exception("Test Exception")
            }

            assertNull(result)
        }
    }

    @Test
    fun `withIOResultOrNull returns result when no exception`() {
        runTest(testDispatcherProvider.Main) {
            val result = dispatcherScope.withIOResultOrNull { "Actual Value" }

            assertEquals("Actual Value", result)
        }
    }

    @Test
    fun `withIOResultOrNull returns null if block result is null`() {
        runTest(testDispatcherProvider.Main) {
            val result = dispatcherScope.withIOResultOrNull<String?> { null }

            assertNull(result)
        }
    }

    @Test
    fun `withIOResultOrDefault returns default value on exception`() {
        runTest(testDispatcherProvider.Main) {
            val result = dispatcherScope.withIOResultOrDefault(
                defaultValue = "Default Value",
                block = { throw Exception("Test Exception") },
            )

            assertEquals("Default Value", result)
        }
    }

    @Test
    fun `withIOResultOrDefault returns block result when no exception`() {
        runTest(testDispatcherProvider.Main) {
            val result = dispatcherScope.withIOResultOrDefault(
                defaultValue = "Default Value",
                block = { "Actual Value" },
            )

            assertEquals("Actual Value", result)
        }
    }

    @Test
    fun `withIOResultOrDefault returns null if block result is null`() {
        runTest(testDispatcherProvider.Main) {
            val result = dispatcherScope.withIOResultOrDefault(
                defaultValue = "Default",
                block = { null }
            )

            assertNull(result)
        }
    }
}