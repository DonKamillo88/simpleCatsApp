package org.k2apps.simplecatsapp.ui.list

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.k2apps.simplecatsapp.data.model.Cat
import org.k2apps.simplecatsapp.data.repository.db.CatsRepository
import org.k2apps.simplecatsapp.data.repository.remote.RemoteCatsRepository

class CatListViewModel @ViewModelInject constructor(
    private val localCatsRepository: CatsRepository,
    private val remoteCatsRepository: RemoteCatsRepository
) :
    ViewModel() {

    private val _cats = MutableLiveData<List<Cat>>()
    private val _navigateToSelectedCat = MutableLiveData<Cat>()
    private val _isShowingSavedCats = MutableLiveData(false)
    private var page = 1
    private var limit = 50
    private var order = "Desc"

    val cats: LiveData<List<Cat>>
        get() = _cats
    val navigateToSelectedCat: LiveData<Cat>
        get() = _navigateToSelectedCat
    val isShowingSavedCats: LiveData<Boolean>
        get() = _isShowingSavedCats

    var isHandleBackButton = MutableLiveData(false)

    init {
        getCats()
    }

    fun onRefreshList() {
        getCats()
    }

    fun onBackClick() {
        if (_isShowingSavedCats.value == true) {
            getCats()
            isHandleBackButton.value = false
            _isShowingSavedCats.value = false
        }
    }

    fun onShowSavedCats() {
        viewModelScope.launch {
            _cats.value = localCatsRepository.getAll()
            _isShowingSavedCats.value = true
            isHandleBackButton.value = true
        }
    }

    private fun getCats() {
        viewModelScope.launch {
            try {
                val allCats = remoteCatsRepository.getCats(page, limit, order)
                val catsWithBreeds = allCats.filter { it.breeds.isNotEmpty() }

                val catsToShow = if (catsWithBreeds.size < MINIMUM_CATS_NUMBER_TO_SHOW) {
                    allCats
                } else {
                    catsWithBreeds
                }

                addSavedMarkToCats(catsToShow)
                _cats.value = catsToShow
            } catch (e: Exception) {
                _cats.value = ArrayList()
                Log.e(TAG, "getCats: error: $e")
            }
        }
    }

    fun displayCatDetails(cat: Cat) {
        _navigateToSelectedCat.value = cat
    }

    fun displayCatDetailsComplete() {
        _navigateToSelectedCat.value = null
    }

    private suspend fun addSavedMarkToCats(cats: List<Cat>) {
        val savedCats = localCatsRepository.getAll()
        for (cat in cats) {
            for (savedCat in savedCats) {
                if (savedCat.id == cat.id) {
                    cat.isSaved = true
                    break
                }
            }
        }
    }


    companion object {
        private const val TAG = "CatListViewModel"
        private const val MINIMUM_CATS_NUMBER_TO_SHOW = 5
    }
}