package org.k2apps.simplecatsapp.ui.details

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.k2apps.simplecatsapp.data.model.Cat
import org.k2apps.simplecatsapp.data.repository.db.*
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CatDetailsViewModelTest {
    private lateinit var catsDao: CatsDao
    private lateinit var breedsDao: BreedsDao
    private lateinit var catsRepository: CatsRepository
    private lateinit var breedsRepository: BreedsRepository
    private lateinit var db: AppDatabase
    private lateinit var viewModel: CatDetailsViewModel

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        catsDao = db.catsDao()
        breedsDao = db.breedsDao()

        catsRepository = CatsRepository(catsDao)
        breedsRepository = BreedsRepository(breedsDao)

        viewModel = CatDetailsViewModel(
            ApplicationProvider.getApplicationContext(),
            catsRepository,
            breedsRepository
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun initCatTest() = runBlocking {
        val cat1 = Cat("1", "", 100, 100, "1")
        val cat2 = Cat("2", "", 100, 100, "2")

        assert(viewModel.isCatSaved.value == false)
        viewModel.initCat(cat1)
        assert(viewModel.selectedCat.value?.id == cat1.id)
        viewModel.initCat(cat2)
        assert(viewModel.selectedCat.value?.id == cat2.id)
    }

    @Test
    @Throws(Exception::class)
    fun onSaveCatTest() = runBlocking {
        val cat = Cat("0", "", 100, 100, "1")

        assert(viewModel.isCatSaved.value == false)
        viewModel.initCat(cat)
        assert(viewModel.selectedCat.value?.id == cat.id)

        viewModel.onSaveCatSuspend()
        assert(viewModel.isCatSaved.value == true)
        viewModel.onSaveCatSuspend()
        assert(viewModel.isCatSaved.value == false)

    }

}