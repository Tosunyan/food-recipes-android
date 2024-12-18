package com.tosunyan.foodrecipes.common.coroutines

import org.koin.dsl.module

val CommonModule = module {
    single<DispatcherProvider> { DispatcherProvider.default }
}