package org.k2apps.simplecatsapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.k2apps.simplecatsapp.databinding.CatListFragmentBinding

@AndroidEntryPoint
class CatListFragment : Fragment() {

    private val viewModel: CatListViewModel by lazy {
        ViewModelProvider(this).get(CatListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = CatListFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayCatDetails(it)
        })

        viewModel.navigateToSelectedCat.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController()
                    .navigate(CatListFragmentDirections.actionCatListFragmentToCatDetailsFragment(it))
                viewModel.displayCatDetailsComplete()
            }
        })

        return binding.root
    }

    companion object {
        private const val TAG = "CatListFragment"
    }
}