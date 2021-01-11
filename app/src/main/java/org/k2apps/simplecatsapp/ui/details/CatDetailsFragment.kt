package org.k2apps.simplecatsapp.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.k2apps.simplecatsapp.databinding.CatDetailsFragmentBinding

@AndroidEntryPoint
class CatDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = CatDetailsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val safeArgs: CatDetailsFragmentArgs by navArgs()
        val cat = safeArgs.cat

        Log.e(TAG, "onCreateView: $cat")

        val viewModelFactory = CatDetailsViewModelFactory(cat, requireNotNull(activity).application)
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(CatDetailsViewModel::class.java)

        return binding.root
    }

    companion object {
        private const val TAG = "DetailsFragment"
    }
}