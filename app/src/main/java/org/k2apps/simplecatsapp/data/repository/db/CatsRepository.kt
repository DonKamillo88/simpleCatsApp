package org.k2apps.simplecatsapp.data.repository.db

import org.k2apps.simplecatsapp.data.model.Cat

class CatsRepository(private val catsDao: CatsDao) {

    suspend fun getAll(): List<Cat> {
        return catsDao.getAll() ?: emptyList()
    }

    suspend fun getCatById(id: String): Cat? {
        return catsDao.getCatById(id)
    }

    suspend fun insert(cat: Cat) {
        catsDao.insert(cat)
    }

    suspend fun update(cat: Cat) {
        catsDao.update(cat)
    }

    suspend fun delete(cat: Cat) {
        catsDao.delete(cat)
    }

    suspend fun deleteAll() {
        catsDao.deleteAll()
    }

    companion object {
        private const val TAG = "CatsRepository"
    }
}