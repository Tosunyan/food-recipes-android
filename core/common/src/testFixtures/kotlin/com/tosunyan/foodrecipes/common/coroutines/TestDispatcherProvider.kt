package com.tosunyan.foodrecipes.common.coroutines

import kotlinx.coroutines.test.StandardTestDispatcher

object TestDispatcherProvider : DispatcherProvider {

    private val testDispatcher = StandardTestDispatcher()

    override val Main = testDispatcher
    override val IO = testDispatcher
    override val Default = testDispatcher
}