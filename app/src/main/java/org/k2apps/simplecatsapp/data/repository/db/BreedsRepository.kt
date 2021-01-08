package org.k2apps.simplecatsapp.data.repository.db

import org.k2apps.simplecatsapp.data.model.Breed

class BreedsRepository(private val breedsDao: BreedsDao) {

    suspend fun getAll(): List<Breed> {
        return breedsDao.getAll() ?: emptyList()
    }

    suspend fun getBreedById(id: String): Breed? {
        return breedsDao.getBreedById(id)
    }

    suspend fun insert(breed: Breed) {
        breedsDao.insert(breed)
    }

    suspend fun update(breed: Breed) {
        breedsDao.update(breed)
    }

    suspend fun delete(breed: Breed) {
        breedsDao.delete(breed)
    }

    suspend fun deleteAll() {
        breedsDao.deleteAll()
    }

    companion object {
        private const val TAG = "BreedsRepository"
    }
}