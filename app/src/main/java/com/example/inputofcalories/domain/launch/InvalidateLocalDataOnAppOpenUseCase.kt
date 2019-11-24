package com.example.inputofcalories.domain.launch

interface InvalidateLocalDataOnAppOpenUseCase {
    suspend fun invalidate()
}