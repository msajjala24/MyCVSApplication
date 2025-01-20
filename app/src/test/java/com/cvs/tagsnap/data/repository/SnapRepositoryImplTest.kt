package com.cvs.tagsnap.data.repository

import com.cvs.tagsnap.data.network.SnapApi
import com.cvs.tagsnap.model.Media
import com.cvs.tagsnap.model.NetworkSnapResponse
import com.cvs.tagsnap.model.SnapImage
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class SnapRepositoryImplTest {
    @Mock
    private lateinit var mockApi: SnapApi
    private lateinit var snapRepositoryImpl: SnapRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        snapRepositoryImpl = SnapRepositoryImpl(mockApi)
    }

    @Test
    fun `fetchImages returns list of SnapImages when API call is successful`() = runBlockingTest {
        // Arrange
        val mockSnapImages = listOf(
            SnapImage(
                "id1", "url1",
                Media("url2"),
                "author",
                "published",
                "description",
                "dateTaken"
            ),
            SnapImage(
                "id2",
                "url2",
                Media("url3"),
                "author2",
                "published2",
                "description2",
                "dateTaken2"
            )
        )
        whenever(mockApi.fetchImages(any())).thenReturn(NetworkSnapResponse(mockSnapImages))
        val result = snapRepositoryImpl.fetchImages("testTag")
        assertEquals(mockSnapImages, result)
    }
}