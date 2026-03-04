package com.ioffeivan.feature.search.data.source.remote.api

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.error.network.NetworkStatusCode
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.network.call_adapter.factory.DataResultCallAdapterFactory
import com.ioffeivan.core.network.model.BookDto
import com.ioffeivan.core.network.model.BookInfo
import com.ioffeivan.core.network.model.BooksDto
import com.ioffeivan.core.network.model.ErrorDetailsDto
import com.ioffeivan.core.network.model.ErrorResponseDto
import com.ioffeivan.core.network.model.ImageLinks
import com.ioffeivan.core.network.utils.NetworkJson
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import retrofit2.Retrofit
import retrofit2.create

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SearchApiServiceTest {
    private val mockWebServer: MockWebServer = MockWebServer()
    private val searchApiService: SearchApiService =
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(NetworkJson.converterFactory)
            .addCallAdapterFactory(DataResultCallAdapterFactory.create())
            .build()
            .create()

    private val booksDto =
        BooksDto(
            listOf(
                BookDto("id", BookInfo("title", emptyList(), ImageLinks("url", "url"))),
            ),
        )

    @AfterAll
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Nested
    inner class Search {
        @Test
        fun whenCalled_shouldSendCorrectRequest() =
            runTest {
                val query = "query"
                val maxResults = 30
                val fields = "items"
                val mockResponse =
                    MockResponse()
                        .setBody("{}")
                mockWebServer.enqueue(mockResponse)

                searchApiService.search(
                    query = query,
                    maxResults = maxResults,
                    fields = fields,
                )
                val request = mockWebServer.takeRequest()

                assertThat(request.method).isEqualTo("GET")
                assertThat(request.path).isEqualTo("/v1/volumes?q=$query&maxResults=$maxResults&fields=$fields")
            }

        @Test
        fun whenApiReturnsSuccess_shouldReturnBooksDto() =
            runTest {
                val expected = DataResult.Success(booksDto)
                val mockResponse =
                    MockResponse()
                        .setBody(NetworkJson.json.encodeToString(booksDto))
                mockWebServer.enqueue(mockResponse)

                val actual = searchApiService.search(query = "title")
                mockWebServer.takeRequest()

                assertThat(actual).isEqualTo(expected)
            }

        @Test
        fun whenApiReturnsBadRequest_shouldReturnsError() =
            runTest {
                val badRequestCode = NetworkStatusCode.BadRequest
                val errorType = "EMPTY_QUERY"
                val expected =
                    DataResult.Error(
                        AppError.NetworkError(badRequestCode, errorType),
                    )
                val errorResponseDto = createErrorResponse(badRequestCode.code, errorType)
                val mockResponse =
                    MockResponse()
                        .setResponseCode(badRequestCode.code)
                        .setBody(NetworkJson.json.encodeToString(errorResponseDto))
                mockWebServer.enqueue(mockResponse)

                val actual = searchApiService.search(query = "")
                mockWebServer.takeRequest()

                assertThat(actual).isEqualTo(expected)
            }
    }

    private fun createErrorResponse(code: Int, message: String): ErrorResponseDto {
        return ErrorResponseDto(
            error =
                ErrorDetailsDto(
                    code = code,
                    message = message,
                ),
        )
    }
}
