package com.androidinsight.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.androidinsight.data.database.entities.ApkInfo
import com.androidinsight.databinding.FragmentApkAnalysisBinding
import com.androidinsight.ui.viewmodels.ApkInfoViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ApkInfoFragment : Fragment() {
    private var _binding: FragmentApkAnalysisBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ApkInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApkAnalysisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewModel.apkInfo.observe(viewLifecycleOwner) { apkInfo ->
            updateApkInfo(apkInfo)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupClickListeners() {
        binding.selectApkButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.selectAndAnalyzeApk(requireContext())
            }
        }
    }

    private fun updateApkInfo(apkInfo: ApkInfo?) {
        apkInfo?.let {
            binding.apply {
                apkVersion.text = "Version: ${it.versionName} (${it.versionCode})"
                apkSdk.text = "Min SDK: ${it.minSdkVersion}, Target SDK: ${it.targetSdkVersion}"
                packageName.text = "Package: ${it.packageName}"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}