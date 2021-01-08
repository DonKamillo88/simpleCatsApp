package org.k2apps.simplecatsapp.data.repository.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import org.k2apps.simplecatsapp.data.model.Cat

@Dao
interface CatsDao {

    @Query("SELECT * FROM $TABLE")
    suspend fun getAll(): List<Cat>?

    @Query("SELECT * FROM $TABLE WHERE id = :id")
    suspend fun getCatById(id: String): Cat?

    @Insert(onConflict = REPLACE)
    suspend fun insert(cat: Cat)

    @Update
    suspend fun update(cat: Cat)

    @Query("DELETE FROM $TABLE")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(cat: Cat)

    companion object {
        private const val TABLE = "cats"
    }
}