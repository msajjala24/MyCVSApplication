package com.cvs.tagsnap.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvs.tagsnap.domain.SnapUseCase
import com.cvs.tagsnap.model.SnapImage
import com.cvs.tagsnap.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SnapViewModel @Inject constructor(private val snapUseCase: SnapUseCase) : ViewModel() {
    private val _uiState = mutableStateOf<UiState<List<SnapImage>>>(UiState.None)
    var uiState = _uiState

    fun searchImages(queryTag: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = snapUseCase.queryFlickrData(queryTag)
                _uiState.value = UiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.orEmpty())
            }
        }
    }
}