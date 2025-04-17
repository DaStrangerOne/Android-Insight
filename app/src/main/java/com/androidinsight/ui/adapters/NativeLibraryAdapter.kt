package com.androidinsight.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidinsight.core.CodeAnalyzer.NativeLibrary
import com.androidinsight.databinding.ItemNativeLibraryBinding

class NativeLibraryAdapter : ListAdapter<NativeLibrary, NativeLibraryAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<NativeLibrary>() {
        override fun areItemsTheSame(oldItem: NativeLibrary, newItem: NativeLibrary): Boolean {
            return oldItem.name == newItem.name && oldItem.architecture == newItem.architecture
        }

        override fun areContentsTheSame(oldItem: NativeLibrary, newItem: NativeLibrary): Boolean {
            return oldItem == newItem
        }
    }
) {
    class ViewHolder(private val binding: ItemNativeLibraryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(library: NativeLibrary) {
            binding.apply {
                libraryName.text = library.name
                libraryArch.text = "Architecture: ${library.architecture}"
                librarySize.text = "Size: ${formatSize(library.size)}"

                // Set visibility of feature chips
                strippedChip.visibility = if (library.isStripped) View.VISIBLE else View.GONE
                antiDebugChip.visibility = if (library.securityAnalysis.hasAntiDebug) View.VISIBLE else View.GONE
                obfuscatedChip.visibility = if (library.securityAnalysis.hasObfuscation) View.VISIBLE else View.GONE

                // Show exported functions if available
                if (library.exportedFunctions.isNotEmpty()) {
                    exportedFunctionsContainer.visibility = View.VISIBLE
                    library.exportedFunctions.joinToString("\n")
                    // TODO: Implement proper RecyclerView for exported functions
                } else {
                    exportedFunctionsContainer.visibility = View.GONE
                }

                // Show security findings
                if (library.securityAnalysis.securityRisks.isNotEmpty()) {
                    securityAnalysisContainer.visibility = View.VISIBLE
                    securityFindings.text = library.securityAnalysis.securityRisks.joinToString("\n") { "• $it" }
                } else {
                    securityAnalysisContainer.visibility = View.GONE
                }
            }
        }

        private fun formatSize(bytes: Long): String {
            return when {
                bytes < 1024 -> "$bytes B"
                bytes < 1024 * 1024 -> "${bytes / 1024} KB"
                else -> String.format("%.1f MB", bytes / (1024.0 * 1024.0))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNativeLibraryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}