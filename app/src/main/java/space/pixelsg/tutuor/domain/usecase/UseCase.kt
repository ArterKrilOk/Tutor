package space.pixelsg.tutuor.domain.usecase

import kotlinx.coroutines.flow.Flow

interface UseCase<I, O> {
    fun execute(input: I): Flow<O>
}