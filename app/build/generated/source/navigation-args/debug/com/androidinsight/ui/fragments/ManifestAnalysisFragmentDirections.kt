package com.androidinsight.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import com.androidinsight.R
import kotlin.Int
import kotlin.Long
import kotlin.String

public class ManifestAnalysisFragmentDirections private constructor() {
  private data class ActionManifestToComponent(
    public val componentId: Long,
    public val componentType: String,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_manifest_to_component

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putLong("componentId", this.componentId)
        result.putString("componentType", this.componentType)
        return result
      }
  }

  public companion object {
    public fun actionManifestToComponent(componentId: Long, componentType: String): NavDirections =
        ActionManifestToComponent(componentId, componentType)
  }
}
