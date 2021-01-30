package org.k2apps.simplecatsapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.k2apps.simplecatsapp.data.model.Breed
import org.k2apps.simplecatsapp.data.model.Cat
import org.k2apps.simplecatsapp.data.repository.db.*
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class DbTest {
    private lateinit var catsDao: CatsDao
    private lateinit var breedsDao: BreedsDao
    private lateinit var catsRepository: CatsRepository
    private lateinit var breedsRepository: BreedsRepository
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        catsDao = db.catsDao()
        breedsDao = db.breedsDao()

        catsRepository = CatsRepository(catsDao)
        breedsRepository = BreedsRepository(breedsDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun catsDaoTest() = runBlocking {

        val cat1 = Cat("1", "", 100, 100, "1")
        val cat2 = Cat("2", "", 100, 100, "2")

        assert(catsDao.getAll().isNullOrEmpty())
        catsDao.insert(cat1)
        assert(catsDao.getAll()?.size == 1)
        catsDao.insert(cat2)
        assert(catsDao.getCatById("2")?.id == cat2.id)
        assert(catsDao.getCatById("1")?.breeds == cat1.breeds)
        catsDao.deleteAll()
        assert(catsDao.getAll().isNullOrEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun catsRepositoryTest() = runBlocking {

        val cat1 = Cat("1", "", 100, 100, "1")
        val cat2 = Cat("2", "", 100, 100, "2")

        assert(catsRepository.getAll().isNullOrEmpty())
        catsRepository.insert(cat1)
        assert(catsRepository.getAll().size == 1)
        catsRepository.insert(cat2)
        assert(catsRepository.getCatById("2")?.id == cat2.id)
        assert(catsRepository.getCatById("1")?.breeds == cat1.breeds)
        catsRepository.deleteAll()
        assert(catsRepository.getAll().isNullOrEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun breedsDaoTest() = runBlocking {

        val breed1 = Breed("1", "Abyssinian", "tem1", "org1", "desc1")
        val breed2 = Breed("2", "Bengal", "temp2", "org2", "desc2")

        assert(breedsDao.getAll().isNullOrEmpty())
        breedsDao.insert(breed1)
        assert(breedsDao.getAll()?.size == 1)
        breedsDao.insert(breed2)
        assert(breedsDao.getBreedById("2")?.id == breed2.id)
        assert(breedsDao.getBreedById("1")?.description == breed1.description)
        breedsDao.deleteAll()
        assert(breedsDao.getAll().isNullOrEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun breedsRepositoryTest() = runBlocking {

        val breed1 = Breed("1", "Abyssinian", "tem1", "org1", "desc1")
        val breed2 = Breed("2", "Bengal", "temp2", "org2", "desc2")

        assert(breedsRepository.getAll().isNullOrEmpty())
        breedsRepository.insert(breed1)
        assert(breedsRepository.getAll().size == 1)
        breedsRepository.insert(breed2)
        assert(breedsRepository.getBreedById("2")?.id == breed2.id)
        assert(breedsRepository.getBreedById("1")?.description == breed1.description)
        breedsRepository.deleteAll()
        assert(breedsRepository.getAll().isNullOrEmpty())
    }

}