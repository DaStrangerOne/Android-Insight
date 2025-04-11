package com.androidinsight.ui

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.androidinsight.R
import com.androidinsight.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { handleApkSelection(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupNavigation()
        setupFab()
        observeViewModel()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            getContent.launch("application/vnd.android.package-archive")
        }
    }

    private fun observeViewModel() {
        viewModel.processingState.observe(this) { state ->
            when (state) {
                is ProcessingState.Processing -> showProcessingUI()
                is ProcessingState.Success -> showSuccessUI(state.message)
                is ProcessingState.Error -> showErrorUI(state.message)
                else -> {}
            }
        }
    }

    private fun handleApkSelection(uri: Uri) {
        viewModel.processApk(uri)
    }

    private fun showProcessingUI() {
        binding.fab.isEnabled = false
        Snackbar.make(binding.root, "Processing APK...", Snackbar.LENGTH_INDEFINITE)
            .show()
    }

    private fun showSuccessUI(message: String) {
        binding.fab.isEnabled = true
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun showErrorUI(message: String) {
        binding.fab.isEnabled = true
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction("Retry") {
                // Handle retry logic
            }.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Handle settings
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

sealed class ProcessingState {
    object Idle : ProcessingState()
    object Processing : ProcessingState()
    data class Success(val message: String) : ProcessingState()
    data class Error(val message: String) : ProcessingState()
}