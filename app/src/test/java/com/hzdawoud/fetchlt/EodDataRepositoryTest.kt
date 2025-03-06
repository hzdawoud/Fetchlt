package com.hzdawoud.fetchlt

import com.hzdawoud.fetchlt.data.model.EodEntryDto
import com.hzdawoud.fetchlt.data.model.EodResponseDto
import com.hzdawoud.fetchlt.data.remote.APIService
import com.hzdawoud.fetchlt.data.repository.EodDataRepository
import com.hzdawoud.fetchlt.data.repository.EodDataRepositoryImpl
import com.hzdawoud.fetchlt.domain.model.EodResponse
import com.hzdawoud.fetchlt.utils.network.Resource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
class EodDataRepositoryTest {

    private val apiService = mockk<APIService>()
    private val repository: EodDataRepository = EodDataRepositoryImpl(apiService)

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

        // Assert
        when (resource) {
            is Resource.Success<EodResponse> -> {
                assertEquals(1, resource.data.data.size)
                assertEquals("AAPL", resource.data.data[0].symbol)
            }
            is Resource.Error -> fail("Expected success, got error: ${resource.exception.message}")
            else -> fail("Unexpected resource type")
        }
    }
}