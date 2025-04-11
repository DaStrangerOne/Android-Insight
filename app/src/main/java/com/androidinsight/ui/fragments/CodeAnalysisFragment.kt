package com.androidinsight.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidinsight.core.CodeAnalyzer
import com.androidinsight.databinding.FragmentCodeAnalysisBinding
import com.androidinsight.ui.MainViewModel
import com.androidinsight.ui.adapters.MethodAnalysisAdapter
import com.androidinsight.ui.adapters.NativeLibraryAdapter
import com.google.android.material.tabs.TabLayout

class CodeAnalysisFragment : Fragment() {
    private var _binding: FragmentCodeAnalysisBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    
    private lateinit var codeAnalyzer: CodeAnalyzer
    private lateinit var methodAdapter: MethodAnalysisAdapter
    private lateinit var libraryAdapter: NativeLibraryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCodeAnalysisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupCodeAnalyzer()
        setupTabLayout()
        setupRecyclerViews()
        setupChipListeners()
        observeViewModel()
    }

    private fun setupCodeAnalyzer() {
        codeAnalyzer = CodeAnalyzer(requireContext())
    }

    private fun setupTabLayout() {
        binding.codeAnalysisTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> showMethodAnalysis()
                    1 -> showNativeLibraries()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.codeAnalysisTabs.addTab(binding.codeAnalysisTabs.newTab().setText("Methods"))
        binding.codeAnalysisTabs.addTab(binding.codeAnalysisTabs.newTab().setText("Native Libraries"))
    }

    private fun setupRecyclerViews() {
        methodAdapter = MethodAnalysisAdapter()
        libraryAdapter = NativeLibraryAdapter()

        binding.analysisResultsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = methodAdapter
        }
    }

    private fun setupChipListeners() {
        binding.showStringsChip.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setShowStrings(isChecked)
        }

        binding.showApiCallsChip.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setShowApiCalls(isChecked)
        }

        binding.showSignaturesChip.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setShowSignatures(isChecked)
        }
    }

    private fun observeViewModel() {
        viewModel.selectedDexFile.observe(viewLifecycleOwner) { dexFile ->
            dexFile?.let {
                val methodSignatures = codeAnalyzer.analyzeDexFile(it)
                methodAdapter.submitList(methodSignatures)
            }
        }

        viewModel.selectedApkFile.observe(viewLifecycleOwner) { apkFile ->
            apkFile?.let {
                val nativeLibraries = codeAnalyzer.analyzeNativeLibraries(it)
                libraryAdapter.submitList(nativeLibraries)
            }
        }
    }

    private fun showMethodAnalysis() {
        binding.analysisResultsList.adapter = methodAdapter
    }

    private fun showNativeLibraries() {
        binding.analysisResultsList.adapter = libraryAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}