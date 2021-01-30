package org.k2apps.simplecatsapp.data.repository.remote

import org.k2apps.simplecatsapp.data.model.Cat

interface RemoteCatsRepository {

    suspend fun getCats(page: Int, limit: Int, order: String): List<Cat>

    class RemoteCatsRepositoryImpl(private val catsApiService: CatsApiService) : RemoteCatsRepository {
        override suspend fun getCats(page: Int, limit: Int, order: String) =
            catsApiService.getCats(page, limit, order)
    }
}