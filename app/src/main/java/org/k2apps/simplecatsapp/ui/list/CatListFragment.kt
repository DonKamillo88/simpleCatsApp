package org.k2apps.simplecatsapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
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
    private lateinit var callback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.onBackClick()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = CatListFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.photosGrid.adapter = CatGridAdapter(CatGridAdapter.OnClickListener {
            viewModel.displayCatDetails(it)
        })

        viewModel.navigateToSelectedCat.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController().navigate(CatListFragmentDirections.actionHomeToDetails(it))
                viewModel.displayCatDetailsComplete()
            }
        })

        viewModel.isHandleBackButton.observe(viewLifecycleOwner, {
            callback.isEnabled = it
        })


        return binding.root
    }

    companion object {
        private const val TAG = "CatListFragment"
    }
}