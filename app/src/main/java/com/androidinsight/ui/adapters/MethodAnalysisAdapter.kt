package com.androidinsight.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidinsight.core.CodeAnalyzer.MethodSignature
import com.androidinsight.databinding.ItemMethodAnalysisBinding
import org.jf.dexlib2.AccessFlags

class MethodAnalysisAdapter : ListAdapter<MethodSignature, MethodAnalysisAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<MethodSignature>() {
        override fun areItemsTheSame(oldItem: MethodSignature, newItem: MethodSignature): Boolean {
            return oldItem.className == newItem.className && 
                   oldItem.methodName == newItem.methodName &&
                   oldItem.parameters == newItem.parameters
        }

        override fun areContentsTheSame(oldItem: MethodSignature, newItem: MethodSignature): Boolean {
            return oldItem == newItem
        }
    }
) {
    class ViewHolder(private val binding: ItemMethodAnalysisBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(signature: MethodSignature) {
            binding.apply {
                methodSignature.text = buildMethodSignature(signature)
                methodClass.text = signature.className.substring(1).replace('/', '.')
                
                // Set access level chip
                accessLevelChip.text = getAccessLevelString(signature.accessFlags)
                
                // Show native chip if applicable
                nativeChip.visibility = if (signature.isNative) View.VISIBLE else View.GONE
                
                // Show security risk chip if needed
                securityRiskChip.visibility = if (hasSecurityRisk(signature)) {
                    securityRiskChip.text = "Security Risk"
                    View.VISIBLE
                } else View.GONE
                
                // Show security warning if needed
                securityWarning.visibility = if (hasSecurityRisk(signature)) {
                    securityWarning.text = getSecurityWarning(signature)
                    View.VISIBLE
                } else View.GONE
            }
        }

        private fun buildMethodSignature(signature: MethodSignature): String {
            return buildString {
                append(signature.methodName)
                append('(')
                append(signature.parameters.joinToString(", ") { 
                    it.substring(1).replace('/', '.') 
                })
                append(") : ")
                append(signature.returnType.substring(1).replace('/', '.'))
            }
        }

        private fun getAccessLevelString(accessFlags: Int): String {
            return when {
                accessFlags and AccessFlags.PUBLIC.value != 0 -> "public"
                accessFlags and AccessFlags.PROTECTED.value != 0 -> "protected"
                accessFlags and AccessFlags.PRIVATE.value != 0 -> "private"
                else -> "package-private"
            }
        }

        private fun hasSecurityRisk(signature: MethodSignature): Boolean {
            // Check for potential security risks based on method characteristics
            return signature.className.contains("crypto") ||
                   signature.className.contains("security") ||
                   signature.methodName.contains("encrypt") ||
                   signature.methodName.contains("decrypt") ||
                   signature.isNative
        }

        private fun getSecurityWarning(signature: MethodSignature): String {
            return when {
                signature.isNative -> "Native method may contain security-sensitive code"
                signature.className.contains("crypto") -> "Contains cryptographic operations"
                signature.methodName.contains("encrypt") || 
                signature.methodName.contains("decrypt") -> "Handles sensitive data encryption"
                else -> "Potential security risk detected"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMethodAnalysisBinding.inflate(
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