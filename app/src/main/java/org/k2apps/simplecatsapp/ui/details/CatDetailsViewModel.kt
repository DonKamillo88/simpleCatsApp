package org.k2apps.simplecatsapp.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.k2apps.simplecatsapp.data.model.Breed
import org.k2apps.simplecatsapp.data.model.Cat
import org.k2apps.simplecatsapp.data.repository.db.AppDatabase
import org.k2apps.simplecatsapp.data.repository.db.BreedsRepository
import org.k2apps.simplecatsapp.data.repository.db.CatsRepository

class CatDetailsViewModel(val cat: Cat, app: Application) : AndroidViewModel(app) {

    private val catsRepository: CatsRepository =
        CatsRepository(AppDatabase.getDatabase(getApplication()).catsDao())
    private val breedsRepository: BreedsRepository =
        BreedsRepository(AppDatabase.getDatabase(getApplication()).breedsDao())

    private val _isLike = MutableLiveData("")
    val isLike: LiveData<String> = _isLike

    private val _selectedCat = MutableLiveData<Cat>()
    val selectedCat: LiveData<Cat>
        get() = _selectedCat
    private val _selectedCatBreed = MutableLiveData<Breed>()
    val selectedCatBreed: LiveData<Breed>
        get() = _selectedCatBreed


    private var isCatSaved = false

    init {
        viewModelScope.launch {
            _selectedCat.value = cat
            if (cat.breeds.isNotEmpty()) _selectedCatBreed.value = cat.breeds[0]
            else {
                val localBreed = cat.bredId?.let { breedsRepository.getBreedById(it) }
                if (localBreed != null)
                    _selectedCatBreed.value = localBreed
            }

            isCatSaved = catsRepository.getCatById(cat.id) != null
            _isLike.value = if (isCatSaved) "saved" else "not saved"
        }
    }

    fun onSaveCat() {
        viewModelScope.launch {
            if (isCatSaved) {
                catsRepository.delete(cat)
            } else {
                if (cat.breeds.isNotEmpty()) cat.breeds[0]?.let {
                    cat.bredId = it.id
                    breedsRepository.insert(it)
                }
                catsRepository.insert(cat)
            }
            isCatSaved = !isCatSaved
            _isLike.value = if (isCatSaved) "saved" else "not saved"
        }
    }


    companion object {
        private const val TAG = "DetailsViewModel"
    }
}