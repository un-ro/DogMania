package com.unero.dogmania.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.dynamicfeatures.DynamicExtras
import androidx.navigation.dynamicfeatures.DynamicInstallMonitor
import androidx.navigation.fragment.findNavController
import com.google.android.play.core.splitinstall.SplitInstallSessionState
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.unero.dogmania.R
import com.unero.dogmania.adapter.ItemAdapter
import com.unero.dogmania.core.data.Resource
import com.unero.dogmania.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by inject()
    private val itemAdapter: ItemAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        viewModel.dog.observe(viewLifecycleOwner, { dogs ->
            if (dogs != null) {
                when (dogs) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        dogs.data?.let { itemAdapter.setList(it) }
                    }
                    is Resource.Error -> {
                        showLoading(false)
                    }
                }
            }
        })

        binding.toolbar.setOnMenuItemClickListener {
            val installMonitor = DynamicInstallMonitor()
            when (it.itemId) {
                R.id.item_favorite -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToLovedActivity()
                    findNavController().navigate(
                        action,
                        DynamicExtras(installMonitor)
                    )
                    navigateFeature(installMonitor, action)
                    true
                }
                R.id.item_search -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToSearchActivity()
                    findNavController().navigate(
                        action,
                        DynamicExtras(installMonitor)
                    )
                    navigateFeature(installMonitor, action)
                    true
                }
                else -> false
            }
        }
    }

    private fun navigateFeature(installMonitor: DynamicInstallMonitor, action: NavDirections){
        if (installMonitor.isInstallRequired) {
            installMonitor.status.observe(requireActivity(), object : Observer<SplitInstallSessionState> {
                override fun onChanged(sessionState: SplitInstallSessionState) {
                    when (sessionState.status()) {
                        SplitInstallSessionStatus.INSTALLED -> {
                            Toast.makeText(requireContext(), "TERINSTAL", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(action)
                        }
                        SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                            Toast.makeText(requireContext(), "PERLU ACC", Toast.LENGTH_SHORT).show()
                        }

                        // Handle all remaining states:
                        SplitInstallSessionStatus.FAILED -> {
                            Toast.makeText(requireContext(), "GAGAL", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(action)
                        }
                        SplitInstallSessionStatus.CANCELED -> {
                            Toast.makeText(requireContext(), "CANCEL", Toast.LENGTH_SHORT).show()
                        }
                    }

                    if (sessionState.hasTerminalStatus()) {
                        installMonitor.status.removeObserver(this);
                    }
                }
            });
        }
    }

    private fun setupRV() {
        with(binding.rvDog) {
            adapter = itemAdapter
            setHasFixedSize(true)
        }
    }

    private fun showLoading(boolean: Boolean) {
        if (boolean) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}