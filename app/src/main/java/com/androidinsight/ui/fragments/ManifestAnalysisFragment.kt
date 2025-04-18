package com.androidinsight.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidinsight.R
import com.androidinsight.databinding.FragmentManifestAnalysisBinding
import com.androidinsight.ui.MainViewModel
import com.androidinsight.ui.ProcessingState
import com.androidinsight.ui.adapters.ComponentListAdapter
import com.androidinsight.ui.adapters.PermissionListAdapter
import com.google.android.material.tabs.TabLayout

class ManifestAnalysisFragment : Fragment() {
    private var _binding: FragmentManifestAnalysisBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var permissionAdapter: PermissionListAdapter
    private lateinit var componentAdapter: ComponentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManifestAnalysisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        setupTabLayout()
        observeViewModel()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        permissionAdapter.clear()
        componentAdapter.clear()
    }
    }

    private fun setupRecyclerViews() {
        permissionAdapter = PermissionListAdapter()
        componentAdapter = ComponentListAdapter { componentId, componentType ->
            navigateToComponentDetails(componentId, componentType)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = permissionAdapter
        }
    }

    private fun setupTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> showPermissions()
                    1 -> showActivities()
                    2 -> showServices()
                    3 -> showReceivers()
                    4 -> showProviders()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun observeViewModel() {
        viewModel.processingState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ProcessingState.Processing -> showLoadingState()
                is ProcessingState.Success -> showContent()
                is ProcessingState.Error -> showError(state.message)
                else -> {}
            }
        }

        viewModel.currentAnalysis.observe(viewLifecycleOwner) { analysis ->
            analysis?.let {
                when (binding.tabLayout.selectedTabPosition) {
                    0 -> permissionAdapter.submitList(it.permissions)
                    1 -> componentAdapter.submitList(it.activities.map { entry ->
                        ComponentItem(entry.key, "Activity", entry.value == "true")
                    })
                    2 -> componentAdapter.submitList(it.services.map { entry ->
                        ComponentItem(entry.key, "Service", entry.value == "true")
                    })
                    3 -> componentAdapter.submitList(it.receivers.map { entry ->
                        ComponentItem(entry.key, "Receiver", entry.value == "true")
                    })
                    4 -> componentAdapter.submitList(it.providers.map { entry ->
                        ComponentItem(entry.key, "Provider", true)
                    })
                }
            }
        }
    }

    private fun showPermissions() {
        binding.recyclerView.adapter = permissionAdapter
        viewModel.currentAnalysis.value?.let {
            permissionAdapter.submitList(it.permissions)
        }
    }

    private fun showActivities() {
        binding.recyclerView.adapter = componentAdapter
        viewModel.currentAnalysis.value?.let {
            componentAdapter.submitList(it.activities.map { entry ->
                ComponentItem(entry.key, "Activity", entry.value == "true")
            })
        }
    }

    private fun showServices() {
        binding.recyclerView.adapter = componentAdapter
        viewModel.currentAnalysis.value?.let {
            componentAdapter.submitList(it.services.map { entry ->
                ComponentItem(entry.key, "Service", entry.value == "true")
            })
        }
    }

    private fun showReceivers() {
        binding.recyclerView.adapter = componentAdapter
        viewModel.currentAnalysis.value?.let {
            componentAdapter.submitList(it.receivers.map { entry ->
                ComponentItem(entry.key, "Receiver", entry.value == "true")
            })
        }
    }

    private fun showProviders() {
        binding.recyclerView.adapter = componentAdapter
        viewModel.currentAnalysis.value?.let {
            componentAdapter.submitList(it.providers.map { entry ->
                ComponentItem(entry.key, "Provider", true)
            })
        }
    }

    private fun showLoadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.errorView.visibility = View.GONE
    }

    private fun showContent() {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        binding.errorView.visibility = View.GONE
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.errorView.visibility = View.VISIBLE
        binding.errorText.text = message
    }

    private fun navigateToComponentDetails(componentId: Long, componentType: String) {
        findNavController().navigate(
            ManifestAnalysisFragmentDirections.actionManifestToComponent(
                componentId,
                componentType
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class ComponentItem(
    val name: String,
    val type: String,
    val isExported: Boolean
)