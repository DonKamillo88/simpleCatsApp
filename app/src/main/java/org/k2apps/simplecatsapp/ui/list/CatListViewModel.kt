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
import org.k2apps.simplecatsapp.data.repository.remote.CatsApi

class CatListViewModel @ViewModelInject constructor(val catsRepository: CatsRepository) : ViewModel() {

    private val _cats = MutableLiveData<List<Cat>>()
    private val _navigateToSelectedCat = MutableLiveData<Cat>()

    val cats: LiveData<List<Cat>>
        get() = _cats
    val navigateToSelectedCat: LiveData<Cat>
        get() = _navigateToSelectedCat

    private var isShowingSavedCats = false

    init {
        getCats()
    }

    fun onShowSavedCats() {
        viewModelScope.launch {
            isShowingSavedCats = !isShowingSavedCats
            if (isShowingSavedCats) {
                _cats.value = catsRepository.getAll()
            } else {
                getCats()
            }

//            Log.e(TAG, "onShowSavedCats: ${allSavedCats.size}")

        }
    }

    fun onRefreshList() {
        getCats()
    }

    private fun getCats() {
        Log.e(TAG, "getCats: ")
        viewModelScope.launch {
            try {
                _cats.value = CatsApi.retrofitService.getCats()
                Log.e(TAG, "getCats: success")
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

    companion object {
        private const val TAG = "CatListViewModel"
    }
}