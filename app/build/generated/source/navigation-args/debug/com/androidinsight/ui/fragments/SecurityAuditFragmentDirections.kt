package com.androidinsight.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import com.androidinsight.R
import kotlin.Int
import kotlin.Long

public class SecurityAuditFragmentDirections private constructor() {
  private data class ActionAuditToVulnerability(
    public val vulnerabilityId: Long,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_audit_to_vulnerability

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putLong("vulnerabilityId", this.vulnerabilityId)
        return result
      }
  }

  public companion object {
    public fun actionAuditToVulnerability(vulnerabilityId: Long): NavDirections =
        ActionAuditToVulnerability(vulnerabilityId)
  }
}
