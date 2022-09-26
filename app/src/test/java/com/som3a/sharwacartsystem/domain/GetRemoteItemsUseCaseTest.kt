package com.som3a.sharwacartsystem.domain

import com.som3a.sharwacartsystem.data.model.Menu
import com.som3a.sharwacartsystem.data.model.RemoteList
import io.reactivex.Single
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class GetRemoteItemsUseCaseTest {

    private val repository = mock<Repository>()
    private val useCase = GetRemoteItemsUseCase(repository)

    @Test
    fun `repository calls useCase correctly and return value`() {
        val actual = RemoteList.menuList
        stubRemoteItemsUseCase(Single.just(actual))
        val testObserver = useCase.execute().test()
        testObserver.assertValue(actual)
    }

    @Test
    fun `repository calls useCase correctly and return exception`() {
        val exception = Throwable()
        stubRemoteItemsUseCase(Single.error(exception))
        val testObserver = useCase.execute().test()
        testObserver.assertError(exception)
    }

    private fun stubRemoteItemsUseCase(menu: Single<Menu>) {
        whenever(repository.getRemoteItems())
            .thenReturn(menu)
    }
}