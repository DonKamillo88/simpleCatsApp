package org.k2apps.simplecatsapp.data.repository.remote


import org.k2apps.simplecatsapp.data.model.Cat
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsApiService {

    @GET("/v1/images/search")
    suspend fun getCats(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("order") order: String
    ): List<Cat>
}

