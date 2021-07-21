package com.hokagelab.gamecatalogapp.core.utils

import androidx.annotation.VisibleForTesting
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors @VisibleForTesting constructor(private val executor: Executor) {

    constructor() : this(
        Executors.newSingleThreadExecutor(),
    )
    fun executor(): Executor = executor
}