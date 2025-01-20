package com.cvs.tagsnap.data.network

import com.cvs.tagsnap.model.NetworkSnapResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SnapApi {
    @GET("services/feeds/photos_public.gne?format=json&nojsoncallback=1")
    suspend fun fetchImages(@Query("tags") tags: String): NetworkSnapResponse
}