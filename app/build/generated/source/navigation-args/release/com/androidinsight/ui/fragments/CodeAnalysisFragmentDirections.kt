package com.androidinsight.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import com.androidinsight.R
import kotlin.Int
import kotlin.Long

public class CodeAnalysisFragmentDirections private constructor() {
  private data class ActionCodeToMethod(
    public val methodId: Long,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_code_to_method

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putLong("methodId", this.methodId)
        return result
      }
  }

  public companion object {
    public fun actionCodeToMethod(methodId: Long): NavDirections = ActionCodeToMethod(methodId)
  }
}
