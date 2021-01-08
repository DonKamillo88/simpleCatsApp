package org.k2apps.simplecatsapp.data.repository.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import org.k2apps.simplecatsapp.data.model.Breed

@Dao
interface BreedsDao {

    @Query("SELECT * FROM $TABLE")
    suspend fun getAll(): List<Breed>?

    @Query("SELECT * FROM $TABLE WHERE id = :id")
    suspend fun getBreedById(id: String): Breed?

    @Insert(onConflict = REPLACE)
    suspend fun insert(breed: Breed)

    @Update
    suspend fun update(breed: Breed)

    @Query("DELETE FROM $TABLE")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(breed: Breed)

    companion object {
        private const val TABLE = "breeds"
    }
}