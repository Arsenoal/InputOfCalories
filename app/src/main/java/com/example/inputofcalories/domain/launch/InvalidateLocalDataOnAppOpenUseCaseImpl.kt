package com.example.inputofcalories.domain.launch

import com.example.inputofcalories.repo.launch.ClearDbRepo

class InvalidateLocalDataOnAppOpenUseCaseImpl(
    private val clearDbRepo: ClearDbRepo
): InvalidateLocalDataOnAppOpenUseCase {
    override suspend fun invalidate() = clearDbRepo.clear()
}