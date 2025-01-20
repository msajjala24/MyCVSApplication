package com.cvs.tagsnap.data.repository

import com.cvs.tagsnap.data.network.SnapApi
import com.cvs.tagsnap.model.SnapImage
import javax.inject.Inject

class SnapRepositoryImpl @Inject constructor(private val api: SnapApi) : SnapRepository {
    override suspend fun fetchImages(tags: String): List<SnapImage> =
        api.fetchImages(tags).items
}