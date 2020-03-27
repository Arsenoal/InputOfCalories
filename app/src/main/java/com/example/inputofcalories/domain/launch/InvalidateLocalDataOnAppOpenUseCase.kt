package com.example.inputofcalories.domain.launch

//TODO remove caching functionality or delete this logically redundant s***t
interface InvalidateLocalDataOnAppOpenUseCase {
    suspend fun invalidate()
}