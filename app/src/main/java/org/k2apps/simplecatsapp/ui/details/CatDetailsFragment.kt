package org.k2apps.simplecatsapp.ui.details

import android.os.Bundle
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

    private val viewModel: CatDetailsViewModel by lazy {
        ViewModelProvider(this).get(CatDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = CatDetailsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val safeArgs: CatDetailsFragmentArgs by navArgs()
        val cat = safeArgs.cat

        viewModel.initCat(cat)
        binding.viewModel = viewModel


        return binding.root
    }

    companion object {
        private const val TAG = "DetailsFragment"
    }
}