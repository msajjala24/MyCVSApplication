package com.cvs.tagsnap.domain

import com.cvs.tagsnap.data.repository.SnapRepository
import javax.inject.Inject

class SnapUseCase @Inject constructor(private val repository: SnapRepository) {

    suspend fun queryFlickrData(inputData: String) = repository.fetchImages(inputData)

}