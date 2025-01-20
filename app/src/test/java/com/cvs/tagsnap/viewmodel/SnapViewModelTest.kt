
import com.cvs.tagsnap.domain.SnapUseCase
import com.cvs.tagsnap.model.Media
import com.cvs.tagsnap.model.SnapImage
import com.cvs.tagsnap.model.UiState
import com.cvs.tagsnap.viewmodel.SnapViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class SnapViewModelTest {

    @Mock
    private lateinit var mockSnapUseCase: SnapUseCase
    private lateinit var snapViewModel: SnapViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testTag = "birds"

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        snapViewModel = SnapViewModel(mockSnapUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `searchImages() with success`() = runTest(testDispatcher) {
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
        val expectedUiState = UiState.Success(mockSnapImages)
        `when`(mockSnapUseCase.queryFlickrData(testTag)).thenReturn(mockSnapImages)

        snapViewModel.searchImages(testTag)
        advanceUntilIdle()
        assertEquals(expectedUiState, snapViewModel.uiState.value)
    }

    @Test
    fun `searchImages() with error`() = runTest(testDispatcher) {

        val errorMessage = "Failed to fetch data"
        val expectedUiState = UiState.Error(errorMessage)
        `when`(mockSnapUseCase.queryFlickrData(testTag)).thenThrow(RuntimeException(errorMessage))

        snapViewModel.searchImages(testTag)
        advanceUntilIdle()
        val actualUiState = snapViewModel.uiState.value

        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `searchImages() with empty result`() = runTest(testDispatcher) {
        val emptySnapImages = emptyList<SnapImage>()
        val expectedUiState = UiState.Success(emptySnapImages)
        `when`(mockSnapUseCase.queryFlickrData(testTag)).thenReturn(emptyList())

        snapViewModel.searchImages(testTag)
        advanceUntilIdle()
        val actualUiState = snapViewModel.uiState.value

        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `searchImages() with initial state as none`() = runTest(testDispatcher) {
        assertEquals(UiState.None, snapViewModel.uiState.value)
    }

    @Test
    fun `searchImages() with loading`() = runTest(testDispatcher) {
        val mockSnapImages = listOf(
            SnapImage(
                "id1", "url1",
                Media("url2"), "author", "published", "description", "dateTaken"
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
        val expectedUiState = UiState.Loading
        `when`(mockSnapUseCase.queryFlickrData(testTag)).thenReturn(mockSnapImages)
        snapViewModel.uiState.value = expectedUiState


        snapViewModel.searchImages(testTag)
        val actualUiState = snapViewModel.uiState.value

        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `searchImages() with exception`() = runTest(testDispatcher) {
        val expectedUiState = UiState.Error("test")
        `when`(mockSnapUseCase.queryFlickrData("test")).thenThrow(RuntimeException("test"))
        snapViewModel.uiState.value = expectedUiState
        snapViewModel.searchImages("test")
        assertEquals(expectedUiState, snapViewModel.uiState.value)
    }

    @Test
    fun `searchImages() with null exception`() = runTest(testDispatcher) {
        val expectedUiState = UiState.Error("")
        `when`(mockSnapUseCase.queryFlickrData("test")).thenThrow(NullPointerException())
        snapViewModel.uiState.value = expectedUiState
        snapViewModel.searchImages("test")
        val actualUiState = snapViewModel.uiState.value
        assertEquals(expectedUiState, actualUiState)
    }
}