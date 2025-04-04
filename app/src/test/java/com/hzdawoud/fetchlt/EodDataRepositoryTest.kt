package com.hzdawoud.fetchlt

import com.hzdawoud.fetchlt.data.model.EodEntryDto
import com.hzdawoud.fetchlt.data.model.EodResponseDto
import com.hzdawoud.fetchlt.data.remote.APIService
import com.hzdawoud.fetchlt.data.repository.EodDataRepository
import com.hzdawoud.fetchlt.data.repository.EodDataRepositoryImpl
import com.hzdawoud.fetchlt.domain.model.EodEntry
import com.hzdawoud.fetchlt.utils.network.Either
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class EodDataRepositoryTest {

    private val apiService : APIService = mockk()
    private val dispatcher = StandardTestDispatcher()
    private lateinit var repository: EodDataRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        repository = EodDataRepositoryImpl(apiService)
    }

    @Test
    fun `getEndOfDayData returns success resource when API returns valid data`() = runTest {
        // Arrange
        val dummyEntryDto = EodEntryDto(
            open = 100.0,
            high = 110.0,
            low = 95.0,
            close = 105.0,
            volume = 1000000.0,
            adjHigh = 110.0,
            adjLow = 95.0,
            adjClose = 105.0,
            adjOpen = 100.0,
            adjVolume = 1000000.0,
            splitFactor = 1.0,
            dividend = 0.0,
            symbol = "AAPL",
            exchange = "NASDAQ",
            date = "2021-04-09T00:00:00+0000"
        )
        val dummyResponseDto = EodResponseDto(data = listOf(dummyEntryDto))
        val retrofitResponse: Response<EodResponseDto> = Response.success(dummyResponseDto)

        coEvery { apiService.getEndOfDayData(any()) } returns retrofitResponse

        // Act
        val resource = repository.getEndOfDayData("AAPL")
        dispatcher.scheduler.advanceUntilIdle()

        // Assert
        when (resource) {
            is Either.Success<List<EodEntry>> -> {
                assertEquals(1, resource.data.size)
                assertEquals("AAPL", resource.data[0].symbol)
            }
            is Either.Error -> fail("Expected success, got error: ${resource.error}")
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}