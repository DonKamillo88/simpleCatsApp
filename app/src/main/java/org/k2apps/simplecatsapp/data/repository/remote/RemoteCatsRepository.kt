package org.k2apps.simplecatsapp.data.repository.remote

import javax.inject.Inject

class RemoteCatsRepository @Inject constructor(private val catsApiService: CatsApiService) {

    suspend fun getCats(page: Int, limit: Int, order: String) =
        catsApiService.getCats(page, limit, order)
}