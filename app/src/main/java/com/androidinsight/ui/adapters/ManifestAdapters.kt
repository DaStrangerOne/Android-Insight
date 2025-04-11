package com.androidinsight.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidinsight.databinding.ItemComponentBinding
import com.androidinsight.databinding.ItemPermissionBinding
import com.androidinsight.ui.fragments.ComponentItem

class PermissionListAdapter : ListAdapter<String, PermissionListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }
) {
    class ViewHolder(private val binding: ItemPermissionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(permission: String) {
            binding.apply {
                permissionName.text = permission
                permissionDescription.text = getPermissionDescription(permission)
                permissionIcon.setImageResource(getPermissionIcon(permission))
                dangerousPermissionIndicator.visibility =
                    if (isDangerousPermission(permission)) View.VISIBLE else View.GONE
            }
        }

        private fun getPermissionDescription(permission: String): String {
            return when {
                permission.contains("INTERNET") -> "Allows the app to open network sockets"
                permission.contains("CAMERA") -> "Allows access to the camera device"
                permission.contains("LOCATION") -> "Allows access to precise location"
                permission.contains("STORAGE") -> "Allows reading/writing to external storage"
                permission.contains("CONTACTS") -> "Allows access to contacts data"
                else -> "No description available"
            }
        }

        private fun getPermissionIcon(permission: String): Int {
            // TODO: Return appropriate permission icon based on permission type
            return android.R.drawable.ic_menu_info_details
        }

        private fun isDangerousPermission(permission: String): Boolean {
            return permission.contains("CAMERA") ||
                    permission.contains("LOCATION") ||
                    permission.contains("STORAGE") ||
                    permission.contains("CONTACTS") ||
                    permission.contains("MICROPHONE")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPermissionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ComponentListAdapter(
    private val onComponentClick: (Long, String) -> Unit
) : ListAdapter<ComponentItem, ComponentListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<ComponentItem>() {
        override fun areItemsTheSame(oldItem: ComponentItem, newItem: ComponentItem) =
            oldItem.name == newItem.name && oldItem.type == newItem.type

        override fun areContentsTheSame(oldItem: ComponentItem, newItem: ComponentItem) =
            oldItem == newItem
    }
) {
    class ViewHolder(private val binding: ItemComponentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ComponentItem, onComponentClick: (Long, String) -> Unit) {
            binding.apply {
                componentName.text = item.name
                componentType.text = item.type
                exportedIndicator.visibility = if (item.isExported) View.VISIBLE else View.GONE
                componentIcon.setImageResource(getComponentIcon(item.type))
                
                root.setOnClickListener {
                    // Using position as a temporary ID, in real app use actual component ID
                    onComponentClick(adapterPosition.toLong(), item.type)
                }
            }
        }

        private fun getComponentIcon(type: String): Int {
            return when (type) {
                "Activity" -> android.R.drawable.ic_menu_view
                "Service" -> android.R.drawable.ic_menu_manage
                "Receiver" -> android.R.drawable.ic_menu_share
                "Provider" -> android.R.drawable.ic_menu_save
                else -> android.R.drawable.ic_menu_help
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemComponentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onComponentClick)
    }
}