package org.k2apps.simplecatsapp

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.k2apps.simplecatsapp.data.model.Cat
import org.k2apps.simplecatsapp.data.repository.remote.CatsApiService
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class CatsApiServiceTest {
    private val expectedRepos = listOf(
        Cat("1", "", 100, 100, "1"),
        Cat("2", "", 100, 100, "2")
    )

    @Mock
    lateinit var service: CatsApiService

    @BeforeEach
    internal fun setUp() {
        MockitoAnnotations.initMocks(this)
        runBlocking {
            whenever(service.getCats(any(), any(), any())).thenReturn(expectedRepos)
        }
    }

    @Test
    internal fun shouldReturnExpectedRepos() = runBlocking {
        val actualRepos = runBlocking { service.getCats(1, 50, "Desc") }

        actualRepos shouldEqual expectedRepos
    }
}