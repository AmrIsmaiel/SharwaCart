package com.som3a.sharwacartsystem.domain

import com.som3a.sharwacartsystem.data.model.MenuCategoryLocal
import io.reactivex.Single
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetLocalItemsUseCaseTest {

    private val repository = mock<Repository>()
    private val useCase = GetLocalItemsUseCase(repository)

    @Test
    fun `repository calls useCase and return correctly`() {
        val expected = listOf(
            MenuCategoryLocal(
                id = DataFactory.randomInt(),
                name = DataFactory.randomString(),
                icon = DataFactory.randomString(),
                decscriptionText = DataFactory.randomString(),
                price = DataFactory.randomDouble(),
                currency = DataFactory.randomString()
            ),
            MenuCategoryLocal(
                id = DataFactory.randomInt(),
                name = DataFactory.randomString(),
                icon = DataFactory.randomString(),
                decscriptionText = DataFactory.randomString(),
                price = DataFactory.randomDouble(),
                currency = DataFactory.randomString()
            )
        )

        stubLocalItemsUseCase(Single.just(expected))
        val testObserver = useCase.execute().test()
        testObserver.assertValue(expected)
    }

    @Test
    fun `repository calls useCase and return exception`() {
        val expected = Throwable()

        stubLocalItemsUseCase(Single.error(expected))
        val testObserver = useCase.execute().test()
        testObserver.assertError(expected)
    }

    private fun stubLocalItemsUseCase(menu: Single<List<MenuCategoryLocal>>) {
        whenever(repository.getItems())
            .thenReturn(menu)
    }

}