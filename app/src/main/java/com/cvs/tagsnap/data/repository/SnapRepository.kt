package com.cvs.tagsnap.data.repository

import com.cvs.tagsnap.model.SnapImage

interface SnapRepository {
    suspend fun fetchImages(tags: String): List<SnapImage>
}