package org.k2apps.simplecatsapp.ui.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.k2apps.simplecatsapp.data.model.Cat

/**
 * Simple ViewModel factory that provides the Cat and context to the ViewModel.
 */
class CatDetailsViewModelFactory(
    private val cat: Cat,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatDetailsViewModel::class.java)) {
            return CatDetailsViewModel(cat, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}