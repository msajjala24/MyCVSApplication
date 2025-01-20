package com.cvs.tagsnap.domain

import com.cvs.tagsnap.data.repository.SnapRepository
import com.cvs.tagsnap.model.SnapImage
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class SnapUseCaseTest {

    @Mock
    private lateinit var mockRepository: SnapRepository

    private lateinit var useCase: SnapUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = SnapUseCase(mockRepository)
    }

    @Test
    fun `queryFlickrData should return data from repository`() = runBlocking {
        val inputData = "test"
        val expectedData = listOf<SnapImage>()
        whenever(mockRepository.fetchImages(inputData)).thenReturn(expectedData)
        val result = useCase.queryFlickrData(inputData)
        assertEquals(expectedData, result)
    }

    @Test
    fun `queryFlickrData should handle repository errors`() = runBlocking {
        val inputData = "error"
        whenever(mockRepository.fetchImages(inputData)).thenThrow(RuntimeException("Repository error"))
        try {
            useCase.queryFlickrData(inputData)
            assert(false) { "Expected exception was not thrown" }
        } catch (e: RuntimeException) {
            assert(e.message == "Repository error")
        }
    }
}