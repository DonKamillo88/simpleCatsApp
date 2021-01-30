package org.k2apps.simplecatsapp.ui.details

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.k2apps.simplecatsapp.data.model.Breed
import org.k2apps.simplecatsapp.data.model.Cat
import org.k2apps.simplecatsapp.data.repository.db.BreedsRepository
import org.k2apps.simplecatsapp.data.repository.db.CatsRepository

class CatDetailsViewModel @ViewModelInject constructor(
    app: Application,
    private val catsRepository: CatsRepository,
    private val breedsRepository: BreedsRepository
) : AndroidViewModel(app) {

    private val _isCatSaved = MutableLiveData(false)
    val isCatSaved: LiveData<Boolean> = _isCatSaved

    private val _selectedCat = MutableLiveData<Cat>()
    val selectedCat: LiveData<Cat>
        get() = _selectedCat
    private val _selectedCatBreed = MutableLiveData<Breed>()
    val selectedCatBreed: LiveData<Breed>
        get() = _selectedCatBreed


    fun initCat(cat: Cat) {
        viewModelScope.launch {
            _selectedCat.postValue(cat)
            if (cat.breeds.isNotEmpty()) _selectedCatBreed.value = cat.breeds[0]
            else {
                val localBreed = cat.bredId?.let { breedsRepository.getBreedById(it) }
                if (localBreed != null)
                    _selectedCatBreed.value = localBreed
            }

            _isCatSaved.postValue(catsRepository.getCatById(cat.id) != null)
        }
    }

    fun onSaveCat() {
        viewModelScope.launch {
            onSaveCatSuspend()
        }
    }

    suspend fun onSaveCatSuspend() {
        val cat = _selectedCat.value!!
        if (_isCatSaved.value == true) {
            catsRepository.delete(cat)
        } else {
            if (cat.breeds.isNotEmpty()) cat.breeds[0]?.let {
                cat.bredId = it.id
                breedsRepository.insert(it)
            }
            catsRepository.insert(cat)
        }
        _isCatSaved.postValue(!_isCatSaved.value!!)
    }


    companion object {
        private const val TAG = "CatDetailsViewModel"
    }
}