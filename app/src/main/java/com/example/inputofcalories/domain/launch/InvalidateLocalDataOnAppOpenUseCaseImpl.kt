package com.example.inputofcalories.domain.launch

import com.example.inputofcalories.repo.launch.ClearDbRepo

class InvalidateLocalDataOnAppOpenUseCaseImpl(
    private val clearDbRepo: ClearDbRepo
): InvalidateLocalDataOnAppOpenUseCase {
    override fun invalidate() = clearDbRepo.clear()
}